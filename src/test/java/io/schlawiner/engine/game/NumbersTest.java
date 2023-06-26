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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumbersTest {

    private Numbers numbers;

    @BeforeEach
    void setUp() {
        numbers = new Numbers(10);
    }

    @Test
    void newInstance() {
        assertEquals(-1, numbers.current());
        assertEquals(-1, numbers.index());
        assertEquals(10, numbers.size());
        assertTrue(0 != numbers.next());
        assertTrue(numbers.hasNext());
        assertFalse(numbers.empty());

    }

    @Test
    void next() {
        numbers.next();

        assertEquals(0, numbers.index());
        assertEquals(10, numbers.size());
        assertTrue(0 != numbers.current());
        assertTrue(0 != numbers.next());
        assertTrue(numbers.hasNext());
        assertFalse(numbers.empty());
    }
}
