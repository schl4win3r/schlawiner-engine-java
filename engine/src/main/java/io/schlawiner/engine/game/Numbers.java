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

import java.util.Iterator;
import java.util.Random;

import static io.schlawiner.engine.util.Iterators.forArray;

public class Numbers implements Iterable<Integer> {

    private final int count;
    private final Integer[] numbers;
    private int index;
    private int current;

    /** Generates {@code count} random numbers */
    public Numbers(final int count) {
        this.count = count;
        this.index = -1;
        this.current = -1;
        this.numbers = new Integer[count];
        Random random = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 1 + random.nextInt(100);
        }
    }

    // Used for unit tests
    Numbers(final int[] numbers) {
        this.count = numbers.length;
        this.index = -1;
        this.current = -1;
        this.numbers = new Integer[count];
        for (int i = 0; i < count; i++) {
            this.numbers[i] = numbers[i];
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return forArray(numbers);
    }

    public int next() {
        index++;
        if (index < numbers.length) {
            current = numbers[index];
            return current;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public boolean hasNext() {
        return index + 1 < numbers.length;
    }

    public int index() {
        return index;
    }

    public int size() {
        return numbers.length;
    }

    public int current() {
        return current;
    }

    public int get(final int index) {
        if (index > -1 && index < numbers.length) {
            return numbers[index];
        }
        return -1;
    }

    public boolean isEmpty() {
        return numbers.length == 0;
    }
}
