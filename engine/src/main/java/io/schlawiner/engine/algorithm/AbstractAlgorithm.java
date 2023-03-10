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

abstract class AbstractAlgorithm implements Algorithm {

    // Calculated by io.schlawiner.engine.algorithm.FindDifference
    private static final int DEFAULT_DIFFERENCE = 15;

    // @formatter:off
    private static final int[][] MULTIPLIERS = new int[][] {
            { 1, 1, 1 },
            { 1, 1, 10 }, { 1, 10, 1 }, { 10, 1, 1 },
            { 1, 1, 100 }, { 1, 100, 1 }, { 100, 1, 1 },
            { 1, 10, 10 }, { 10, 1, 10 }, { 10, 10, 1 },
            { 10, 10, 10 },
            { 10, 10, 100 }, { 10, 100, 10 }, { 100, 10, 10 },
            { 1, 100, 100 }, { 100, 1, 100 }, { 100, 100, 1 },
            { 10, 100, 100 }, { 100, 10, 100 }, { 100, 100, 10 },
            { 100, 100, 100 },
            { 1, 10, 100 }, { 1, 100, 10 }, { 10, 1, 100 },
            { 10, 100, 1 }, { 100, 1, 10 }, { 100, 10, 1 },
    };
    // @formatter:on

    private final String name;
    private final int allowedDifference;

    AbstractAlgorithm(final String name) {
        this(name, DEFAULT_DIFFERENCE);
    }

    // WARNING: if allowedDifference < DEFAULT_DIFFERENCE, compute() might return empty solutions!
    AbstractAlgorithm(final String name, final int allowedDifference) {
        this.name = name;
        this.allowedDifference = allowedDifference;
    }

    @Override
    public Solutions compute(final int a, final int b, final int c, final int target) {
        Solutions solutions = new Solutions(target, allowedDifference);
        for (int[] multiplier : MULTIPLIERS) {
            int am = a * multiplier[0];
            int bm = b * multiplier[1];
            int cm = c * multiplier[2];
            computePermutation(am, bm, cm, target, solutions);
        }
        return solutions;
    }

    protected abstract void computePermutation(int a, int b, int c, int target, Solutions solutions);

    boolean differentDiceNumbers(final int a, final int b, final int c) {
        return a != b || a != c;
    }

    @Override
    public String name() {
        return name;
    }
}
