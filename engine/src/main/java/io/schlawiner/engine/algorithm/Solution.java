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
package io.schlawiner.engine.algorithm;

public record Solution(String term, int value) implements Comparable<Solution> {

    static final Solution INVALID = new Solution("Invalid term", Integer.MAX_VALUE);

    @Override
    public int compareTo(final Solution other) {
        int result = Integer.compare(value, other.value);
        if (result == 0) {
            result = term.compareTo(other.term);
        }
        return result;
    }

    @Override
    public String toString() {
        return term + " = " + value;
    }
}
