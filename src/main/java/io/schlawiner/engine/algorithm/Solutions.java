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

import io.schlawiner.engine.game.Level;

import static java.lang.Math.abs;

/** Collects solutions from an algorithm run and tracks the best one (closest to the target number). */
public class Solutions {

    private final int target;
    private final int allowedDifference;
    private Solution bestSolution;

    public Solutions(final int target, final int allowedDifference) {
        this.target = target;
        this.allowedDifference = allowedDifference;
        this.bestSolution = null;
    }

    /**
     * Adds a solution if its result is within the allowed difference from the target. Updates the best solution if this one is
     * closer.
     */
    public void add(final Solution solution) {
        if (solution.result() >= target - allowedDifference && solution.result() <= target + allowedDifference) {
            if (bestSolution == null) {
                bestSolution = solution;
            } else {
                if (abs(solution.result() - target) < abs(bestSolution.result() - target)) {
                    bestSolution = solution;
                }
            }
        }
    }

    /** Returns the best solution found, or {@code null} if no valid solution exists. */
    public Solution bestSolution() {
        return bestSolution;
    }

    /**
     * Returns a solution appropriate for the given difficulty level. Currently delegates to {@link #bestSolution()} (not yet
     * level-aware).
     */
    public Solution bestSolution(final Level level) {
        // TODO Choose bestSolution based on level
        return bestSolution();
    }
}
