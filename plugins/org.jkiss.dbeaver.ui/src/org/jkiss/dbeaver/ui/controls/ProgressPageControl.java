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
package org.jkiss.dbeaver.ui.controls;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;
import org.jkiss.dbeaver.Log;
import org.jkiss.dbeaver.model.runtime.DBRProgressMonitor;
import org.jkiss.dbeaver.model.runtime.ProxyProgressMonitor;
import org.jkiss.dbeaver.model.runtime.load.ILoadVisualizer;
import org.jkiss.dbeaver.ui.*;
import org.jkiss.dbeaver.ui.internal.UIMessages;
import org.jkiss.utils.CommonUtils;

import java.util.ArrayList;

/**
 * ProgressPageControl
 */
public class ProgressPageControl extends Composite implements ISearchContextProvider, ICustomActionsProvider
{
    private static final Log log = Log.getLog(ProgressPageControl.class);

    private final static int PROGRESS_MIN = 0;
    private final static int PROGRESS_MAX = 20;

    private boolean showDivider;

    private CLabel listInfoLabel;

    private ProgressBar progressBar;
    private Text searchText;

    private int loadCount = 0;
    private ProgressPageControl ownerPageControl = null;
    private ProgressPageControl childPageControl = null;
    private Composite searchControlsComposite;

    private String curInfo;
    private String curSearchText;
    private volatile Job curSearchJob;

    private Color searchNotFoundColor;
    private ToolBarManager defaultToolbarManager;
    private ToolBarManager searchToolbarManager;
    private ToolBarManager customToolbarManager;
    private Composite customControlsComposite;
    private Color defaultBackgroundColor;

    public ProgressPageControl(
        Composite parent,
        int style)
    {
        super(parent, style);
        GridLayout layout = new GridLayout(1, false);
        if ((style & SWT.SHEET) != 0) {
            layout.marginHeight = 0;
            layout.marginWidth = 0;
            layout.verticalSpacing = 0;
            layout.horizontalSpacing = 0;
        }
        //layout.horizontalSpacing = 0;
        //layout.verticalSpacing = 0;
        this.setLayout(layout);
        addDisposeListener(e -> disposeControl());
        searchNotFoundColor = UIStyles.getDefaultWidgetBackground();
    }

    @Override
    public GridLayout getLayout()
    {
        return (GridLayout)super.getLayout();
    }

    public void setShowDivider(boolean showDivider)
    {
        this.showDivider = showDivider;
    }

    public void setInfo(String info)
    {
        if (!CommonUtils.isEmpty(info)) {
            this.curInfo = info;
        }
        if (ownerPageControl != null) {
            ownerPageControl.setInfo(info);
        } else if (listInfoLabel != null && !listInfoLabel.isDisposed()) {
            listInfoLabel.setVisible(!CommonUtils.isEmptyTrimmed(info));
            listInfoLabel.setText(info);
            listInfoLabel.getParent().layout(true, true);
        }
    }

    public final Composite createProgressPanel()
    {
        return createProgressPanel(this);
    }

    public final void substituteProgressPanel(ProgressPageControl externalPageControl)
    {
        this.ownerPageControl = externalPageControl;
        if (this.ownerPageControl != null) {
            this.ownerPageControl.setChildControl(this);
        }
    }

    public void createOrSubstituteProgressPanel(IWorkbenchPartSite site) {
        ProgressPageControl progressControl = findOwnerPageControl(site);
        if (progressControl != null) {
            substituteProgressPanel(progressControl);
        } else {
            createProgressPanel();
        }

    }

    private ProgressPageControl findOwnerPageControl(IWorkbenchPartSite site) {
        if (site instanceof INestedEditorSite && ((INestedEditorSite) site).getFolderEditor() instanceof IProgressControlProvider) {
            return ((IProgressControlProvider)((INestedEditorSite) site).getFolderEditor()).getProgressControl();
        } else if (site instanceof MultiPageEditorSite && ((MultiPageEditorSite) site).getMultiPageEditor() instanceof IProgressControlProvider) {
            return ((IProgressControlProvider)((MultiPageEditorSite) site).getMultiPageEditor()).getProgressControl();
        } else {
            return null;
        }
    }

    private void setChildControl(ProgressPageControl progressPageControl)
    {
        if (progressPageControl == this.childPageControl) {
            return;
        }
//        if (this.childPageControl != null && progressPageControl != null) {
//            log.warn("Overwrite of child page control '" + this.childPageControl); //$NON-NLS-1$
//        }
        this.childPageControl = progressPageControl;
        if (getProgressControl().progressBar == null) {
            hideControls(true);
        }
    }

    private ProgressPageControl getProgressControl()
    {
        return ownerPageControl != null ? ownerPageControl : this;
    }

    public Text getSearchTextControl() {
        return searchText;
    }

    public Composite createContentContainer()
    {
        Composite container = new Composite(this, (getStyle() & SWT.SHEET) == SWT.SHEET ? SWT.NONE :  SWT.BORDER);
        container.setLayout(new FillLayout());
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalIndent = 0;
        gd.verticalIndent = 0;
        container.setLayoutData(gd);

        return container;
    }

    public Composite createProgressPanel(Composite container)
    {
        if (this.ownerPageControl != null) {
            throw new IllegalStateException("Can't create page control while substitution control already set"); //$NON-NLS-1$
        }
        if (showDivider) {
            Label separator = new Label(container, SWT.NONE);
            GridData gd = new GridData(GridData.FILL_HORIZONTAL);
            gd.heightHint = 1;
            separator.setLayoutData(gd);
            separator.addPaintListener(e ->  {
                e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
                e.gc.drawLine(e.x, e.y, e.x + e.width, e.y);
            });
        }

        Composite infoGroup = new Composite(container, SWT.NONE);
        infoGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout gl = new GridLayout(3, false);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        infoGroup.setLayout(gl);

        customControlsComposite = new Composite(infoGroup, SWT.NONE);
        customControlsComposite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        gl = new GridLayout(1, false);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        customControlsComposite.setLayout(gl);

        listInfoLabel = new CLabel(infoGroup, SWT.NONE);
        listInfoLabel.setImage(DBeaverIcons.getImage(UIIcon.SEPARATOR_V));
        listInfoLabel.setLayoutData(GridDataFactory.swtDefaults().minSize(100, SWT.DEFAULT).create());

        searchControlsComposite = UIUtils.createPlaceholder(infoGroup, 1);
        searchControlsComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        // Placeholder toolbar (need to set initial height of search composite)
        new ToolBar(searchControlsComposite, SWT.NONE);

        defaultToolbarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL | SWT.RIGHT);
        customToolbarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL | SWT.RIGHT);

        hideControls(true);

        return infoGroup;
    }

    @Override
    public void fillCustomActions(IContributionManager contributionManager) {
        if (childPageControl != null) {
            childPageControl.fillCustomActions(contributionManager);
        }
    }

    protected void populateCustomActions(ContributionManager contributionManager) {

    }

    public void updateActions() {
        UIUtils.updateContributionItems(defaultToolbarManager);
        UIUtils.updateContributionItems(customToolbarManager);
    }

    private void hideControls(boolean showDefaultControls)
    {
        if (searchControlsComposite == null || searchControlsComposite.isDisposed()) {
            return;
        }
        searchControlsComposite.getParent().setRedraw(false);
        try {
            // Delete all controls created in searchControlsComposite
            UIUtils.disposeChildControls(searchControlsComposite);

            // Nullify all controls
            progressBar = null;
            searchText = null;

            // Create default controls toolbar
            if (showDefaultControls) {
                ((GridLayout)searchControlsComposite.getLayout()).numColumns = 2;
                ((GridLayout)searchControlsComposite.getLayout()).marginTop = 2;
                defaultToolbarManager.removeAll();
                if (isSearchPossible() && isSearchEnabled()) {
                    addSearchAction(defaultToolbarManager);
                }
                Label phLabel = new Label(searchControlsComposite, SWT.NONE);
                phLabel.setText(""); //$NON-NLS-1$
                phLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
                ToolBar defaultToolbar = defaultToolbarManager.createControl(searchControlsComposite);
                defaultToolbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_END));

                // Recreate custom controls
                UIUtils.disposeChildControls(customControlsComposite);

                customToolbarManager.removeAll();
                fillCustomActions(customToolbarManager);
                if (!customToolbarManager.isEmpty()) {
                    ToolBar toolbar = customToolbarManager.createControl(customControlsComposite);
                    toolbar.setFont(BaseThemeSettings.instance.baseFont);
                    toolbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_END));
                    populateCustomActions(customToolbarManager);
                    UIUtils.populateToolItemCommandIds(customToolbarManager);
                }
            }

            searchControlsComposite.getParent().layout();
            //customControlsComposite.layout();
        } finally {
            searchControlsComposite.getParent().setRedraw(true);
        }
    }

    /**
     * Default search action (standard Eclipse EDIT_FIND_AND_REPLACE command)
     */
    protected void addSearchAction(IContributionManager contributionManager) {
        contributionManager.add(ActionUtils.makeCommandContribution(
            PlatformUI.getWorkbench(),
            IWorkbenchCommandConstants.EDIT_FIND_AND_REPLACE,
            UIMessages.controls_progress_page_toolbar_title,
            UIIcon.SEARCH));
    }

    private void createProgressControls()
    {
        if (progressBar != null || customControlsComposite == null) {
            return;
        }
        hideControls(false);
        ((GridLayout)searchControlsComposite.getLayout()).numColumns = 2;
        progressBar = new ProgressBar(searchControlsComposite, SWT.SMOOTH | SWT.HORIZONTAL);
        progressBar.setSize(300, 16);
        progressBar.setState(SWT.NORMAL);
        progressBar.setMinimum(PROGRESS_MIN);
        progressBar.setMaximum(PROGRESS_MAX);
        progressBar.setToolTipText(UIMessages.controls_progress_page_progress_bar_loading_tooltip);
        progressBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        ToolBar progressTools = new ToolBar(searchControlsComposite, SWT.HORIZONTAL);
        final ToolItem stopButton = new ToolItem(progressTools, SWT.PUSH);
        stopButton.setImage(UIUtils.getShardImage(ISharedImages.IMG_ELCL_STOP));
        stopButton.setToolTipText(UIMessages.controls_progress_page_progress_bar_cancel_tooltip);
        stopButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                // Cancel current job
                if (cancelProgress()) {
                    if (!stopButton.isDisposed()) {
                        stopButton.setEnabled(false);
                        stopButton.setImage(UIUtils.getShardImage(ISharedImages.IMG_ELCL_STOP_DISABLED));
                    }
                }
            }
        });
        searchControlsComposite.getParent().layout();
    }

    protected void createSearchControls()
    {
        if (searchText != null) {
            return;
        }
        hideControls(false);
        ((GridLayout)searchControlsComposite.getLayout()).numColumns = 2;

        searchControlsComposite.getParent().setRedraw(false);
        try {

            searchText = new Text(searchControlsComposite, SWT.BORDER);
            UIUtils.addDefaultEditActionsSupport(UIUtils.getActiveWorkbenchWindow(), this.searchText);
            if (curSearchText != null) {
                searchText.setText(curSearchText);
                searchText.setSelection(curSearchText.length());
            }
            //searchText.setBackground(searchNotFoundColor);
            searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            searchText.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.keyCode) {
                        case SWT.ESC:
                            cancelSearch(true);
                            break;
                        case SWT.CR:
                            performSearch(SearchType.NEXT);
                            /* fall-through */
                        case SWT.ARROW_UP:
                        case SWT.ARROW_DOWN:
                            if (childPageControl != null) {
                                childPageControl.setFocus();
                            }
                            e.doit = false;
                            //performSearch(SearchType.NEXT);
                            break;
                    }
                }
            });
            searchText.addModifyListener(e -> {
                curSearchText = searchText.getText();
                if (curSearchJob == null) {
                    curSearchJob = new UIJob(UIMessages.controls_progress_page_job_search) {
                        @Override
                        public IStatus runInUIThread(IProgressMonitor monitor) {
                            if (monitor.isCanceled()) {
                                return Status.CANCEL_STATUS;
                            }
                            performSearch(SearchType.NEXT);
                            curSearchJob = null;
                            return Status.OK_STATUS;
                        }
                    };
                    curSearchJob.schedule(200);
                }
            });

            //ToolBar searchTools = new ToolBar(searchControlsComposite, SWT.HORIZONTAL);
            if (searchToolbarManager == null) {
                searchToolbarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
                searchToolbarManager.add(ActionUtils.makeCommandContribution(
                        PlatformUI.getWorkbench(),
                        IWorkbenchActionDefinitionIds.FIND_PREVIOUS,
                        null,
                        UIIcon.ARROW_UP));
                searchToolbarManager.add(ActionUtils.makeCommandContribution(
                        PlatformUI.getWorkbench(),
                        IWorkbenchActionDefinitionIds.FIND_NEXT,
                        null,
                        UIIcon.ARROW_DOWN));
                searchToolbarManager.add(new Action(UIMessages.controls_progress_page_action_close, UIUtils.getShardImageDescriptor(ISharedImages.IMG_ELCL_REMOVE)) {
                    @Override
                    public void run() {
                        cancelSearch(true);
                    }
                });
            }
            searchToolbarManager.createControl(searchControlsComposite);
            defaultBackgroundColor = searchText.getBackground();
            searchControlsComposite.getParent().layout();
        } finally {
            searchControlsComposite.getParent().setRedraw(true);
        }
    }

    public void disposeControl()
    {
        if (searchToolbarManager != null) {
            searchToolbarManager.dispose();
            searchToolbarManager = null;
        }
        if (defaultToolbarManager != null) {
            defaultToolbarManager.dispose();
            defaultToolbarManager = null;
        }
        if (customToolbarManager != null) {
            customToolbarManager.dispose();
            customToolbarManager = null;
        }
    }

    protected boolean cancelProgress()
    {
        return false;
    }

    protected ISearchExecutor getSearchRunner()
    {
        if (childPageControl != null) {
            return childPageControl.getSearchRunner();
        }
        return null;
    }

    @Override
    public boolean isSearchPossible()
    {
        return getSearchRunner() != null;
    }

    @Override
    public boolean isSearchEnabled()
    {
        return getProgressControl().progressBar == null;
    }

    @Override
    public boolean performSearch(SearchType searchType)
    {
        return performSearch(searchType, true);
    }

    /**
     * Create search controls and perform search according to the searchType
     * @param searchType is a type of search
     * @param isSetFocusToSearchText defines if focus should be set to the search text area if searchType is {@link SearchType#NONE}
     * @return operation success indicator
     */
    public boolean performSearch(SearchType searchType, boolean isSetFocusToSearchText)
    {
        getProgressControl().createSearchControls();
        if (searchType == SearchType.NONE && isSetFocusToSearchText) {
            getProgressControl().searchText.setFocus();
        }
        if (!CommonUtils.isEmpty(getProgressControl().curSearchText)) {
            int options = 0;
            if (searchType == SearchType.PREVIOUS) {
                options |= ISearchExecutor.SEARCH_PREVIOUS;
            } else {
                options |= ISearchExecutor.SEARCH_NEXT;
            }
            boolean success = getSearchRunner().performSearch(getProgressControl().curSearchText, options);
            getProgressControl().searchText.setBackground(success ? getProgressControl().defaultBackgroundColor : searchNotFoundColor);
            return success;
        } else {
            cancelSearch(false);
            return true;
        }
    }

    
    private void cancelSearch(boolean hide)
    {
        if (curSearchJob != null) {
            curSearchJob.cancel();
            curSearchJob = null;
        }
        ISearchExecutor searchRunner = getSearchRunner();
        if (searchRunner != null) {
            searchRunner.cancelSearch();
        }

        if (hide) {
            hideControls(true);
        } else {
            getProgressControl().searchText.setBackground(getProgressControl().defaultBackgroundColor);
        }
    }

    public void refreshActions() {
        getProgressControl().hideControls(true);
    }

    public void activate(boolean active)
    {
        if (active) {
            if (curInfo != null) {
                setInfo(curInfo);
            } else {
                setInfo("");
            }
            if (this.ownerPageControl != null) {
                this.ownerPageControl.setChildControl(this);
            }
        }
    }

/*
    public void run(boolean fork, boolean cancelable, final IRunnableWithProgress runnable) throws InvocationTargetException, InterruptedException
    {
        Job job = new Job("Progress") {
            @Override
            protected IStatus run(IProgressMonitor monitor)
            {
                try {
                    runnable.run(monitor);
                } catch (InvocationTargetException e) {
                    return RuntimeUtils.makeExceptionStatus(e.getTargetException());
                } catch (InterruptedException e) {
                    // do nothing
                }
                return Status.OK_STATUS;
            }
        };
        job.schedule();
        job.join();
    }
*/

    private static class TaskInfo {
        final String name;
        final int totalWork;
        int progress;

        private TaskInfo(String name, int totalWork)
        {
            this.name = name;
            this.totalWork = totalWork;
        }
    }

    public class ProgressVisualizer<RESULT> implements ILoadVisualizer<RESULT> {

        private boolean completed = false;
        private String curStatus;
        private final java.util.List<TaskInfo> tasksRunning = new ArrayList<>();

        @Override
        public DBRProgressMonitor overwriteMonitor(final DBRProgressMonitor monitor)
        {
            return new ProxyProgressMonitor(monitor) {
                @Override
                public void beginTask(final String name, int totalWork)
                {
                    super.beginTask(name, totalWork);
                    curStatus = name;
                    synchronized (tasksRunning) {
                        tasksRunning.add(new TaskInfo(name, totalWork));
                    }
                }

                @Override
                public void done()
                {
                    super.done();
                    curStatus = ""; //$NON-NLS-1$
                    synchronized (tasksRunning) {
                        if (tasksRunning.isEmpty()) {
                            log.warn("Task end when no tasks are running"); //$NON-NLS-1$
                        } else {
                            tasksRunning.remove(tasksRunning.size() - 1);
                        }
                    }
                }

                @Override
                public void subTask(String name)
                {
                    super.subTask(name);
                    curStatus = name;
                }

                @Override
                public void worked(int work)
                {
                    super.worked(work);
                    synchronized (tasksRunning) {
                        if (!tasksRunning.isEmpty()) {
                            tasksRunning.get(tasksRunning.size() - 1).progress += work;
                        }
                    }
                }
            };
        }

        private TaskInfo getCurTaskInfo()
        {
            for (int i = tasksRunning.size() - 1; i >= 0; i--) {
                if (tasksRunning.get(i).totalWork > 1) {
                    return tasksRunning.get(i);
                }
            }
            return null;
        }

        @Override
        public boolean isCompleted()
        {
            return completed;
        }

        @Override
        public void visualizeLoading()
        {
            if (!getProgressControl().isDisposed()) {
                getProgressControl().createProgressControls();
                synchronized (tasksRunning) {
                    TaskInfo taskInfo = getCurTaskInfo();
                    ProgressBar progressBar = getProgressControl().progressBar;
                    if (progressBar != null) {
                        if (taskInfo != null) {
                            progressBar.setMaximum(taskInfo.totalWork);
                            progressBar.setSelection(taskInfo.progress);
                        } else {
                            progressBar.setMaximum(PROGRESS_MAX);
                            progressBar.setSelection(loadCount);
                        }
                    }
                }
                if (curStatus != null) {
                    setInfo(curStatus);
                }
                loadCount++;
                if (loadCount > PROGRESS_MAX) {
                    loadCount = PROGRESS_MIN;
                }
            }
        }

        @Override
        public void completeLoading(RESULT result)
        {
            completed = true;

            if (ProgressPageControl.this.isDisposed()) {
                return;
            }
            visualizeLoading();
            loadCount = 0;
            ProgressBar progressBar = getProgressControl().progressBar;
            if (progressBar != null && !progressBar.isDisposed()) {
                progressBar.setState(SWT.PAUSED);
                getProgressControl().hideControls(true);
            }
        }

    }

}