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

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlgorithmComparisonTest {

    // @formatter:off
    private final int[][] diceNumbers = new int[][] {
            { 2, 3, 5 },
            { 3, 6, 6 },
            { 4, 4, 4 }
    };
    // @formatter:on

    @Test
    void compute() {
        Solutions[] operationResults = computeAlgorithm(new OperationAlgorithm());
        Solutions[] termResults = computeAlgorithm(new TermAlgorithm());
        for (int i = 0; i < diceNumbers.length; i++) {
            assertEquals(operationResults[i].bestSolution(), termResults[i].bestSolution());
        }
    }

    private Solutions[] computeAlgorithm(final Algorithm algorithm) {
        Solutions[] results = new Solutions[diceNumbers.length];

        for (int i = 0; i < diceNumbers.length; i++) {
            Instant start = Instant.now();
            for (int target = 1; target < 101; target++) {
                results[i] = algorithm.compute(diceNumbers[i][0], diceNumbers[i][1], diceNumbers[i][2], target);
            }
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.format("%s finished in %d ms for targets 1..100 using [%d,%d,%d]%n", algorithm.name(), timeElapsed,
                    diceNumbers[i][0], diceNumbers[i][1], diceNumbers[i][2]);
        }
        return results;
    }
}
