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
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    private Dice dice;

    @BeforeEach
    void setUp() {
        dice = new Dice(1, 2, 3);
    }

    @Test
    void calculate() {
        assertEquals(16, Calculator.calculate("10+2*3", dice));
        assertEquals(36, Calculator.calculate("(10+2)*3", dice));
        assertEquals(16, Calculator.calculate("10 + 2 * 3", dice));
        assertEquals(36, Calculator.calculate("(10 + 2) * 3", dice));
        assertEquals(36, Calculator.calculate("((10 + 2) * (3))", dice));
    }

    @Test
    void noExpression() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("foo", dice));
    }

    @Test
    void wrongExpression() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("10 + 3 *", dice));
    }

    @Test
    void wrongOperator() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("10 & 2 % 3", dice));
    }

    @Test
    void noInteger() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("10 + 2 / 3", dice));
    }

    @Test
    void wrongDiceNumbers() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("4 + 5 + 6", dice));
    }

    @Test
    void wrongMultipliers() {
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("1 + 2 + 3000", dice));
    }
}
