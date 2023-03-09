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

import io.schlawiner.engine.term.Term;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiceValidatorTest {

    private Dice dice;

    @BeforeEach
    void setUp() {
        dice = new Dice(1, 2, 3);
    }

    @Test
    void twoNumbers() {
        assertThrows(DiceException.class, () -> DiceValidator.validate(dice, Term.valueOf("1 + 2")));
    }

    @Test
    void fourNumbers() {
        assertThrows(DiceException.class, () -> DiceValidator.validate(dice, Term.valueOf("1 + 2 + 3 + 4")));
    }

    @Test
    void wrongNumbers() {
        assertThrows(DiceException.class, () -> DiceValidator.validate(dice, Term.valueOf("1 + 2 + 4")));
    }

    @Test
    void wrongMultiplier() {
        assertThrows(DiceException.class, () -> DiceValidator.validate(dice, Term.valueOf("1 + 2 + 3000")));
    }

    @Test
    void validate() {
        DiceValidator.validate(dice, Term.valueOf("1 + 2 + 3"));
        DiceValidator.validate(dice, Term.valueOf("1 + 20 + 300"));
    }

    @Test
    void used() {
        assertArrayEquals(new boolean[] { false, false, false }, DiceValidator.used(dice, null));
        assertArrayEquals(new boolean[] { false, false, false }, DiceValidator.used(dice, ""));
        assertArrayEquals(new boolean[] { false, false, false }, DiceValidator.used(dice, "    "));

        assertArrayEquals(new boolean[] { true, false, false }, DiceValidator.used(dice, "1"));
        assertArrayEquals(new boolean[] { false, true, false }, DiceValidator.used(dice, "2"));
        assertArrayEquals(new boolean[] { false, false, true }, DiceValidator.used(dice, "3"));

        assertArrayEquals(new boolean[] { true, false, false }, DiceValidator.used(dice, "4 1"));
        assertArrayEquals(new boolean[] { false, true, false }, DiceValidator.used(dice, "5 2"));
        assertArrayEquals(new boolean[] { false, false, true }, DiceValidator.used(dice, "6 3"));

        assertArrayEquals(new boolean[] { true, false, false }, DiceValidator.used(dice, "1 4"));
        assertArrayEquals(new boolean[] { false, true, false }, DiceValidator.used(dice, "2 5"));
        assertArrayEquals(new boolean[] { false, false, true }, DiceValidator.used(dice, "3 6"));

        assertArrayEquals(new boolean[] { true, false, false }, DiceValidator.used(dice, "4 1 4"));
        assertArrayEquals(new boolean[] { false, true, false }, DiceValidator.used(dice, "5 2 5"));
        assertArrayEquals(new boolean[] { false, false, true }, DiceValidator.used(dice, "6 3 6"));

        assertArrayEquals(new boolean[] { true, false, false }, DiceValidator.used(dice, "10"));
        assertArrayEquals(new boolean[] { true, false, false }, DiceValidator.used(dice, "100"));
        assertArrayEquals(new boolean[] { true, false, false }, DiceValidator.used(dice, "1 10 100"));
        assertArrayEquals(new boolean[] { true, false, false }, DiceValidator.used(dice, "4 5 100"));
    }

    private void assertArrayEquals(final boolean[] expected, final boolean[] actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}
