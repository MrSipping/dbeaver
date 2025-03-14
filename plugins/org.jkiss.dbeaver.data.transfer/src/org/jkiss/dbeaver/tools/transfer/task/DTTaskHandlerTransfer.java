/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2025 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.tools.transfer.task;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.JobGroup;
import org.jkiss.code.NotNull;
import org.jkiss.code.Nullable;
import org.jkiss.dbeaver.DBException;
import org.jkiss.dbeaver.Log;
import org.jkiss.dbeaver.model.exec.DBCStatistics;
import org.jkiss.dbeaver.model.runtime.DBRProgressMonitor;
import org.jkiss.dbeaver.model.runtime.DBRRunnableContext;
import org.jkiss.dbeaver.model.runtime.DBRRunnableWithProgress;
import org.jkiss.dbeaver.model.runtime.ProxyProgressMonitor;
import org.jkiss.dbeaver.model.task.*;
import org.jkiss.dbeaver.runtime.DBWorkbench;
import org.jkiss.dbeaver.tools.transfer.*;
import org.jkiss.dbeaver.tools.transfer.database.DatabaseConsumerSettings;
import org.jkiss.dbeaver.tools.transfer.database.DatabaseTransferConsumer;
import org.jkiss.dbeaver.tools.transfer.internal.DTMessages;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * DTTaskHandlerTransfer
 */
public class DTTaskHandlerTransfer implements DBTTaskHandler, DBTTaskInfoCollector {
    private static final Log log = Log.getLog(DTTaskHandlerTransfer.class);

    private final DBCStatistics totalStatistics = new DBCStatistics();

    @Override
    @NotNull
    public DBTTaskRunStatus executeTask(
        @NotNull DBRRunnableContext runnableContext,
        @NotNull DBTTask task,
        @NotNull Locale locale,
        @NotNull Log log,
        @NotNull PrintStream logStream,
        @NotNull DBTTaskExecutionListener listener) throws DBException
    {
        DataTransferSettings[] settings = new DataTransferSettings[1];
        try {
            runnableContext.run(true, true, monitor -> {
                settings[0] = new DataTransferSettings(
                    monitor,
                    task,
                    log,
                    Collections.emptyMap(),
                    new DataTransferState(),
                    true);
                settings[0].loadNodeSettings(monitor);
            });
        } catch (InvocationTargetException e) {
            throw new DBException("Error loading task settings", e.getTargetException());
        } catch (InterruptedException | OperationCanceledException e) {
            return new DBTTaskRunStatus();
        }
        executeWithSettings(runnableContext, task, locale, log, logStream, listener, settings[0]);

        return DBTTaskRunStatus.makeStatisticsStatus(totalStatistics);
    }

    public void executeWithSettings(
        @NotNull DBRRunnableContext runnableContext,
        @Nullable DBTTask task,
        @NotNull Locale locale,
        @NotNull Log log,
        @Nullable PrintStream logStream,
        @NotNull DBTTaskExecutionListener listener,
        DataTransferSettings settings
    ) throws DBException {
        listener.taskStarted(task);
        int indexOfLastPipeWithDisabledReferentialIntegrity = -1;
        try {
            indexOfLastPipeWithDisabledReferentialIntegrity = initializePipes(runnableContext, settings, task);
            Throwable error = runDataTransferJobs(runnableContext, task, locale, log, logStream, listener, settings);
            listener.taskFinished(task, null, error, settings);
        } catch (InvocationTargetException e) {
            DBWorkbench.getPlatformUI().showError(
                DTMessages.data_transfer_task_handler_unexpected_error_title,
                DTMessages.data_transfer_task_handler_unexpected_error_message,
                e.getCause()
            );
            throw new DBException("Error starting data transfer", e);
        } catch (InterruptedException | OperationCanceledException e) {
            //ignore
        } finally {
            restoreReferentialIntegrity(
                runnableContext,
                settings.getDataPipes().subList(0, indexOfLastPipeWithDisabledReferentialIntegrity + 1)
            );
        }
    }

    private int initializePipes(
        @NotNull DBRRunnableContext runnableContext,
        @NotNull DataTransferSettings settings,
        @Nullable DBTTask task
    ) throws InvocationTargetException, InterruptedException, DBException {
        int[] indexOfLastPipeWithDisabledReferentialIntegrity = new int[]{-1};
        DBException[] dbException = {null};
        List<DataTransferPipe> dataPipes = settings.getDataPipes();

        runnableContext.run(true, false, monitor -> {
            monitor.beginTask("Initialize pipes", dataPipes.size());
            try {
                Object consumerRuntimeParameters = settings.getNodeSettings(settings.getConsumer()).prepareRuntimeParameters();
                for (int i = 0; i < dataPipes.size(); i++) {
                    DataTransferPipe pipe = dataPipes.get(i);
                    pipe.initPipe(settings, i, dataPipes.size());
                    IDataTransferConsumer<?, ?> consumer = pipe.getConsumer();
                    consumer.setRuntimeParameters(consumerRuntimeParameters);
                    try {
                        consumer.startTransfer(monitor);
                    } catch (DBException e) {
                        consumer.finishTransfer(monitor, e, task, true);
                        throw e;
                    }
                    if (enableReferentialIntegrity(consumer, monitor, false)) {
                        indexOfLastPipeWithDisabledReferentialIntegrity[0] = i;
                    }
                    monitor.worked(1);
                }
            } catch (DBException e) {
                dbException[0] = e;
            } finally {
                monitor.done();
            }
        });
        if (dbException[0] != null) {
            throw dbException[0];
        }

        return indexOfLastPipeWithDisabledReferentialIntegrity[0];
    }

    @Nullable
    private Throwable runDataTransferJobs(
        @NotNull DBRRunnableContext runnableContext,
        DBTTask task,
        @NotNull Locale locale,
        @NotNull Log log,
        @Nullable PrintStream logStream,
        @NotNull DBTTaskExecutionListener listener,
        @NotNull DataTransferSettings settings
    ) {
        final List<DataTransferPipe> dataPipes = settings.getDataPipes();
        final int totalJobs = Math.min(dataPipes.size(), settings.getMaxJobCount());
        if (totalJobs == 0) {
            return null;
        }
        TaskExecutor taskExecutor = new TaskExecutor(
            settings, task, listener, log, logStream, dataPipes, totalJobs);
        try {
            runnableContext.run(true, true, taskExecutor);
        } catch (InvocationTargetException e) {
            Throwable error = taskExecutor.error;
            if (error == null) {
                error = e.getTargetException();
            } else {
                error.addSuppressed(e.getTargetException());
            }
            return error;
        } catch (InterruptedException | OperationCanceledException e) {
            log.debug("Data transfer was interrupted", e);
        }
        return taskExecutor.error;
    }

    private void restoreReferentialIntegrity(@NotNull DBRRunnableContext runnableContext,
                                             @NotNull List<DataTransferPipe> pipes) throws DBException {
        DBException[] firstDBException = {null};
        try {
            runnableContext.run(true, false, monitor -> {
                try {
                    monitor.beginTask("Post transfer work", pipes.size());
                    for (DataTransferPipe pipe: pipes) {
                        try {
                            enableReferentialIntegrity(pipe.getConsumer(), monitor, true);
                        } catch (DBException e) {
                            log.debug("enabling referential integrity unexpectedly failed", e);
                            if (firstDBException[0] == null) {
                                firstDBException[0] = e;
                            }
                        }
                        monitor.worked(1);
                    }
                } finally {
                    monitor.done();
                }
            });
        } catch (InterruptedException | OperationCanceledException e) {
            //ignore
        } catch (InvocationTargetException e) {
            DBWorkbench.getPlatformUI().showError(
                DTMessages.data_transfer_task_handler_resoring_referential_integrity_unexpected_error_title,
                DTMessages.data_transfer_task_handler_resoring_referential_integrity_unexpected_error_message,
                e.getCause()
            );
        }
        if (firstDBException[0] != null) {
            throw new DBException("Unable to restore referential integrity properly", firstDBException[0]);
        }
    }

    private static boolean enableReferentialIntegrity(
        @NotNull IDataTransferConsumer<?, ?> consumer,
        @NotNull DBRProgressMonitor monitor,
        boolean enable
    ) throws DBException {
        if (!(consumer instanceof DatabaseTransferConsumer)) {
            return false;
        }
        DatabaseTransferConsumer databaseTransferConsumer = (DatabaseTransferConsumer) consumer;
        DatabaseConsumerSettings settings = databaseTransferConsumer.getSettings();
        if (settings.isDisableReferentialIntegrity() && databaseTransferConsumer.supportsChangingReferentialIntegrity(monitor)) {
            databaseTransferConsumer.enableReferentialIntegrity(monitor, enable);
            return true;
        }
        return false;
    }

    @Override
    public void collectTaskInfo(@NotNull DBTTask task, @NotNull TaskInformation information) {
        DataTransferSettings.collectTaskInfo(task, information);
    }

    private class TaskExecutor implements DBRRunnableWithProgress {
        private final DataTransferSettings settings;
        private final DBTTask task;
        private final DBTTaskExecutionListener listener;
        private final Log log;
        private final PrintStream logStream;
        private final List<DataTransferPipe> dataPipes;
        private final int totalJobs;
        private Throwable error;

        public TaskExecutor(
            DataTransferSettings settings,
            DBTTask task,
            DBTTaskExecutionListener listener,
            Log log,
            PrintStream logStream,
            List<DataTransferPipe> dataPipes,
            int totalJobs
        ) {
            this.totalJobs = totalJobs;
            this.settings = settings;
            this.task = task;
            this.listener = listener;
            this.log = log;
            this.logStream = logStream;
            this.dataPipes = dataPipes;
        }

        @Override
        public void run(DBRProgressMonitor monitor) {
            final JobGroup group;
            if (totalJobs > 1) {
                group = new JobGroup("Data transfer", totalJobs, totalJobs);
            } else {
                group = null;
            }

            final DataTransferJob[] jobs = new DataTransferJob[totalJobs];
            for (int i = 0; i < totalJobs; i++) {
                DataTransferJob job = new DataTransferJob(settings, task, log, logStream, totalJobs == 1 ? monitor : null, i);
                job.setJobGroup(group);
                job.schedule();
                jobs[i] = job;
            }

            monitor.beginTask("Performing data transfer in parallel", settings.getDataPipes().size());

            if (group != null) {
                try {
                    group.join(0, new ProxyProgressMonitor(monitor));
                } catch (InterruptedException | OperationCanceledException e) {
                    group.cancel();
                    return;
                }
            }

            try {
                for (DataTransferJob job : jobs) {
                    if (group == null) {
                        try {
                            while (true) {
                                // Try to join with monitor checks
                                if (!job.join(1000, monitor.getNestedMonitor())) {
                                    if (monitor.isCanceled()) {
                                        break;
                                    }
                                    continue;
                                }
                                break;
                            }
                        } catch (InterruptedException | OperationCanceledException e) {
                            break;
                        }
                    }
                    if (monitor.isCanceled()) {
                        break;
                    }
                    final IStatus result = job.getResult();
                    if (result.getException() != null) {
                        if (error == null) {
                            error = result.getException();
                        } else {
                            error.addSuppressed(result.getException());
                        }
                    }
                    totalStatistics.accumulate(job.getTotalStatistics());
                }
                if (monitor.isCanceled()) {
                    monitor.subTask("Canceling ");
                    // Cancel all nested jobs
                    for (DataTransferJob job : jobs) {
                        job.cancel();
                    }
                }

                monitor.done();
                monitor.beginTask("Finalizing data transfer", 1);

                // End of transfer - signal last pipe about it
                dataPipes.get(dataPipes.size() - 1).getConsumer().finishTransfer(monitor, error, task, true);
            } finally {
                monitor.done();
            }
        }
    }
}
