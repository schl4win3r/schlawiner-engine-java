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

public final class FindDifference {

    // @formatter:off
    private static final int[][] DICE_NUMBER_COMBINATIONS = new int[][] {
            { 1, 1, 1 }, { 1, 1, 2 }, { 1, 1, 3 }, { 1, 1, 4 }, { 1, 1, 5 }, { 1, 1, 6 },
            { 1, 2, 2 }, { 1, 2, 3 }, { 1, 2, 4 }, { 1, 2, 5 }, { 1, 2, 6 },
            { 1, 3, 3 }, { 1, 3, 4 }, { 1, 3, 5 }, { 1, 3, 6 },
            { 1, 4, 4 }, { 1, 4, 5 }, { 1, 4, 6 },
            { 1, 5, 5 }, { 1, 5, 6 },
            { 1, 6, 6 },
            { 2, 2, 2 }, { 2, 2, 3 }, { 2, 2, 4 }, { 2, 2, 5 }, { 2, 2, 6 },
            { 2, 3, 3 }, { 2, 3, 4 }, { 2, 3, 5 }, { 2, 3, 6 },
            { 2, 4, 4 }, { 2, 4, 5 }, { 2, 4, 6 },
            { 2, 5, 5 }, { 2, 5, 6 },
            { 2, 6, 6 },
            { 3, 3, 3 }, { 3, 3, 4 }, { 3, 3, 5 }, { 3, 3, 6 },
            { 3, 4, 4 }, { 3, 4, 5 }, { 3, 4, 6 },
            { 3, 5, 5 }, { 3, 5, 6 },
            { 3, 6, 6 },
            { 4, 4, 4 }, { 4, 4, 5 }, { 4, 4, 6 },
            { 4, 5, 5 }, { 4, 5, 6 },
            { 4, 6, 6 },
            { 5, 5, 5 }, { 5, 5, 6 }, { 5, 6, 6 },
            { 6, 6, 6 },
    };
    // @formatter:on

    public static void main(final String[] args) {
        int allowedDifference = 0;
        while (true) {
            boolean solutionForAnyCombination = true;
            System.out.printf("Checking %2d%n", allowedDifference);
            Algorithm algorithm = new OperationAlgorithm(allowedDifference);

            mainLoop: for (int target = 1; target < 101; target++) {
                for (int[] dnc : DICE_NUMBER_COMBINATIONS) {
                    Solutions solutions = algorithm.compute(dnc[0], dnc[1], dnc[2], target);
                    if (solutions.bestSolution() == null) {
                        solutionForAnyCombination = false;
                        break mainLoop;
                    }
                }
            }

            if (solutionForAnyCombination) {
                System.out.println("\nMax difference: " + allowedDifference);
                break;
            }
            allowedDifference++;
        }
    }
}
