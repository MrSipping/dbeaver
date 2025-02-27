/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2024 DBeaver Corp and others
 * Copyright (C) 2017-2018 Alexander Fedorov (alexander.fedorov@jkiss.org)
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

package org.jkiss.dbeaver.debug.internal.core;

import org.jkiss.dbeaver.utils.NLS;

public class DebugCoreMessages extends NLS {

    private static final String BUNDLE_NAME = "org.jkiss.dbeaver.debug.internal.core.DebugCoreMessages"; //$NON-NLS-1$

    public static String DebugCore_e_read_attribute_generic;
    public static String DebugCore_e_read_attribute_null;
    public static String DebugCore_e_unable_to_retrieve_modes;

    public static String DatabaseThread_name;

    public static String DatabaseDebugTarget_name_default;

    public static String DatasourceSourceContainer_e_extract_node;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, DebugCoreMessages.class);
    }

    private DebugCoreMessages() {
    }
}
