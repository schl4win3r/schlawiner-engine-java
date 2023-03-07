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

import java.util.Random;

public record Dice(int[] numbers) {

    Dice(int a, int b, int c) {
        this(new int[] { a, b, c });
    }

    /**
     * New dice with three random numbers
     */
    public static Dice random() {
        final Random random = new Random();
        int[] numbers = new int[] { 1 + random.nextInt(6), 1 + random.nextInt(6), 1 + random.nextInt(6) };
        return new Dice(numbers);
    }

    @Override
    public String toString() {
        return numbers[0] + " " + numbers[1] + " " + numbers[2];
    }
}
