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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AlgorithmComparisonTest {

    private final int[][] diceNumbers = new int[][] { { 2, 3, 5 }, { 4, 4, 4 } };

    @Test
    void compute() {
        final int[] operationResults = computeAlgorithm(new OperationAlgorithm());
        final int[] termResults = computeAlgorithm(new TermAlgorithm());
        assertArrayEquals(operationResults, termResults);
    }

    private int[] computeAlgorithm(final Algorithm algorithm) {
        final int[] numberOfResults = new int[diceNumbers.length];
        final Solutions[] results = new Solutions[diceNumbers.length];

        for (int i = 0; i < diceNumbers.length; i++) {
            final long start = System.currentTimeMillis();
            for (int target = 1; target < 101; target++) {
                results[i] = algorithm.compute(diceNumbers[i][0], diceNumbers[i][1], diceNumbers[i][2], target);
            }
            final long finish = System.currentTimeMillis();
            final long timeElapsed = finish - start;
            System.out.format("%s finished with %d results in %d ms for targets 1..100 using [%d,%d,%d]%n", algorithm.getName(),
                    results[i].size(), timeElapsed, diceNumbers[i][0], diceNumbers[i][1], diceNumbers[i][2]);
            numberOfResults[i] = results[i].size();
        }
        return numberOfResults;
    }
}
