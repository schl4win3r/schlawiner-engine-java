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

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import io.schlawiner.engine.game.Level;

import static java.lang.Math.abs;

public class Solutions {

    private final int target;
    private final int allowedDifference;
    private final SortedMap<Integer, SortedSet<Solution>> solutions;

    public Solutions(final int target, final int allowedDifference) {
        this.target = target;
        this.allowedDifference = allowedDifference;
        this.solutions = new TreeMap<>();
    }

    public void add(final Solution solution) {
        if (solution.value() >= target - allowedDifference && solution.value() <= target + allowedDifference) {
            int key = abs(solution.value() - target);
            solutions.computeIfAbsent(key, k -> new TreeSet<>()).add(solution);
        }
    }

    public Solution bestSolution() {
        if (solutions.isEmpty()) {
            return null;
        }
        return solutions.values().iterator().next().first();
    }

    public Solution bestSolution(final Level level) {
        // TODO Choose bestSolution based on level
        return bestSolution();
    }

    public int size() {
        return solutions.size();
    }
}
