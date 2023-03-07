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

public class Calculation {

    private final int difference;
    private final int bestDifference;
    private final Solution bestSolution;

    Calculation(int difference, int currentNumber, Solution bestSolution) {
        this.difference = difference;
        this.bestSolution = bestSolution;
        this.bestDifference = bestSolution == null ? difference : Math.abs(bestSolution.value() - currentNumber);
    }

    public boolean isBest() {
        return difference == 0 || difference == bestDifference;
    }

    public int getDifference() {
        return difference;
    }

    public int getBestDifference() {
        return bestDifference;
    }

    public Solution getBestSolution() {
        return bestSolution;
    }
}
