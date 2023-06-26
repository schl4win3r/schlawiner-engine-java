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

import io.schlawiner.engine.game.Dice;

import static java.lang.Math.abs;

public final class ComputeBestSolution {

    public static void main(final String[] args) {
        int target = 74;
        Dice dice = new Dice(6, 6, 3);

        Algorithm algorithm = new OperationAlgorithm();
        Solutions solutions = algorithm.compute(dice.numbers()[0], dice.numbers()[1], dice.numbers()[2], target);
        System.out.printf("Best solution for %d, %d, %d and target %s: %s (difference %d)%n", dice.numbers()[0],
                dice.numbers()[1], dice.numbers()[2], target, solutions.bestSolution(),
                abs(target - solutions.bestSolution().result()));
    }
}
