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
package org.jkiss.dbeaver.model.sql.parser;

import org.jkiss.code.NotNull;
import org.jkiss.code.Nullable;

/**
 * Represents information about the predicate describing one dialect feature which requires special handling during SQL parsing
 */
public interface SQLTokenPredicate {

    /**
     * Action to perform during parse on condition match
     */
    @NotNull
    SQLParserActionKind getActionKind();

    /**
     * Maximum lengths of corresponding suffixes under condition. Zero when prefix is enough to trigger the action.
     */
    int getMaxSuffixLength();

    /**
     * The parameter of the predicate describing the special way of handling during SQL parsing
     */
    @Nullable
    String getParameter();
}
