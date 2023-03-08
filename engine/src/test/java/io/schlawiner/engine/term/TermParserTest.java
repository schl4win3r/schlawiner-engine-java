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
package io.schlawiner.engine.term;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TermParserTest {

    @Test
    void nil() {
        assertThrows(ArithmeticException.class, () -> TermParser.parse(null));
    }

    @Test
    void empty() {
        assertThrows(ArithmeticException.class, () -> TermParser.parse(""));
    }

    @Test
    void blank() {
        assertThrows(ArithmeticException.class, () -> TermParser.parse("  "));
    }

    @Test
    void noExpression() {
        assertThrows(ArithmeticException.class, () -> TermParser.parse("foo"));
    }

    @Test
    void oneNumber() {
        assertThrows(ArithmeticException.class, () -> TermParser.parse("1"));
    }

    @Test
    void oneOperator() {
        assertThrows(ArithmeticException.class, () -> TermParser.parse("+"));
    }

    @Test
    void noOperator() {
        assertThrows(ArithmeticException.class, () -> TermParser.parse("1 2"));
    }

    @Test
    void wrongOperator() {
        assertThrows(ArithmeticException.class, () -> TermParser.parse("10 & 2 % 3"));
    }

    @Test
    void eval() {
        assertEquals(16, TermParser.parse("10+2*3").eval());
        assertEquals(36, TermParser.parse("(10+2)*3").eval());
        assertEquals(16, TermParser.parse("10 + 2 * 3").eval());
        assertEquals(36, TermParser.parse("(10 + 2) * 3").eval());
        assertEquals(36, TermParser.parse("((10 + 2) * (3))").eval());
    }

    @Test
    void print() {
        assertEquals("10 + 2 * 3", TermParser.parse("10+2*3").print());
        assertEquals("(10 + 2) * 3", TermParser.parse("(10+2)*3").print());
        assertEquals("10 + 2 * 3", TermParser.parse("10 + 2 * 3").print());
        assertEquals("(10 + 2) * 3", TermParser.parse("(10 + 2) * 3").print());
        assertEquals("(10 + 2) * 3", TermParser.parse("((10 + 2) * (3))").print());
    }
}
