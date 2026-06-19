/*
 *  Copyright 2023 Harald Pehl
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.schlawiner.engine.score;

/**
 * A single score entry: the expression used and the difference from the target number. Uses {@code String} for the term (not
 * {@link io.schlawiner.engine.term.Term}) because it may also be "Skipped" or "Timeout".
 */
public record Score(String term, int difference) {

    /** Sentinel value for an unscored slot (no attempt yet). */
    static final Score EMPTY = new Score("", -1);

    @Override
    public String toString() {
        return term + " Δ " + difference;
    }
}
