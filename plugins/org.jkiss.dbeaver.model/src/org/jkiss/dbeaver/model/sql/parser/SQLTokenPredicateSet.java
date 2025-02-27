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

import java.util.Deque;
import java.util.Set;

/**
 * A set of connection-specific dialect features which require special handling during SQL parsing
 */
public interface SQLTokenPredicateSet {
    /**
     * Returns maximum statement prefix length handled by predicates
     */
    int getMaxPrefixLength();

    /**
     * Returns maximum statement suffix length handled by predicates
     */
    int getMaxSuffixLength();

    /**
     * Returns true if we need to try capturing text parts by the key of the predicate condition
     */
    boolean hasCaptures();

    /**
     * Checks for a presence of predicates matching given prefix and suffix
     *
     * @return true if there are any corresponding conditions
     */
    boolean anyMatches(Deque<TokenEntry> prefix, Deque<TokenEntry> suffix);

    /**
     * Returns root node of the tree containing all prefixes of all predicates
     */
    @NotNull
    TrieNode<TokenEntry, SQLTokenPredicate> getPrefixTreeRoot();

    /**
     * Searches for all the predicates matching given suffix in the text
     *
     * @return set of successfully matched predicates
     */
    @NotNull
    Set<SQLTokenPredicate> matchSuffix(Deque<TokenEntry> suffix);
}
