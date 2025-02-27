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
package org.jkiss.dbeaver.tasks.ui.nativetool;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.jkiss.code.Nullable;
import org.jkiss.dbeaver.model.struct.DBSObject;
import org.jkiss.dbeaver.model.task.DBTTask;
import org.jkiss.dbeaver.tasks.nativetool.AbstractScriptExecuteSettings;
import org.jkiss.dbeaver.tasks.ui.nativetool.internal.TaskNativeUIMessages;
import org.jkiss.dbeaver.ui.UIUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public abstract class AbstractNativeScriptExecuteWizard<SETTINGS extends AbstractScriptExecuteSettings<BASE_OBJECT>, BASE_OBJECT extends DBSObject, PROCESS_ARG>
        extends AbstractNativeToolWizard<SETTINGS, BASE_OBJECT, PROCESS_ARG> implements IImportWizard
{
    protected AbstractNativeScriptExecuteWizard(Collection<BASE_OBJECT> dbObject, String task) {
        super(dbObject, task);
    }

    protected AbstractNativeScriptExecuteWizard(Collection<BASE_OBJECT> dbObject, String task, @Nullable Path file) {
        super(dbObject, task);
        updateFileSettings(file);
    }

    protected AbstractNativeScriptExecuteWizard(DBTTask task) {
        super(task);
    }

    @Override
    protected boolean isSingleTimeWizard() {
        return false;
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        setWindowTitle(taskTitle);
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        // Do not add base wizard pages. They can be added explicitly thru addTaskConfigPages
        //super.addPages();
        addPage(logPage);
    }

    @Override
    public void onSuccess(long workTime) {
        UIUtils.showMessageBox(getShell(),
            taskTitle,
                NLS.bind(TaskNativeUIMessages.tools_script_execute_wizard_task_completed, taskTitle, getObjectsName()) , //$NON-NLS-1$
                        SWT.ICON_INFORMATION);
    }

    protected void updateFileSettings(@Nullable Path file) {
        getSettings().setInputFile(file != null && Files.exists(file) ? file.toAbsolutePath().toString() : null);
    }

}
