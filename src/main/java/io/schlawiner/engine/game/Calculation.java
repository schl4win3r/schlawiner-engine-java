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
package io.schlawiner.engine.game;

import io.schlawiner.engine.algorithm.Solution;
import io.schlawiner.engine.term.Term;

import static java.lang.Math.abs;

/**
 * Result of a human player's calculation attempt, including their term, the target number, and the algorithm's best solution
 * for comparison.
 */
public record Calculation(Term term, int target, Solution bestSolution) {

    /** Returns the absolute difference between the player's result and the target. */
    public int difference() {
        return abs(term.eval() - target);
    }

    /** Returns {@code true} if the player's solution is as good as the algorithm's best. */
    public boolean best() {
        return difference() == 0 || difference() == bestDifference();
    }

    /** Returns the absolute difference between the algorithm's best solution and the target. */
    public int bestDifference() {
        return abs(bestSolution.result() - target);
    }
}
