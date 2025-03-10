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
package org.jkiss.dbeaver.ui.views.session.internal;

import org.jkiss.dbeaver.utils.NLS;

public class SessionEditorMessages extends NLS {

    static final String BUNDLE_NAME = "org.jkiss.dbeaver.ui.views.session.internal.SessionEditorMessages"; //$NON-NLS-1$

    public static String viewer_details_item_details;
    public static String viewer_details_item_session_details;
    public static String viewer_sql_plan_item_execution_plan;
    public static String viewer_view_item_sql;
    public static String viewer_unsupported_details_type_label;
    public static String viewer_plan_details_text;
    public static String viewer_open_sql_editor_text;
    public static String viewer_open_sql_editor_tip;
    public static String viewer_session_manager_sql_title;
    public static String viewer_session_manager_error_opening_editor_title;
    public static String viewer_session_manager_error_opening_editor_message;
    public static String viewer_session_manager_refresh_sessions_action;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, SessionEditorMessages.class);
    }

    private SessionEditorMessages() {
    }
}
