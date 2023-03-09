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

import java.util.List;

import org.junit.jupiter.api.Test;

import static io.schlawiner.engine.term.Operator.DIVIDED;
import static io.schlawiner.engine.term.Operator.MINUS;
import static io.schlawiner.engine.term.Operator.PLUS;
import static io.schlawiner.engine.term.Operator.TIMES;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TermTest implements TermFixtures {

    @Test
    void nil() {
        assertThrows(TermException.class, () -> Term.valueOf(null));
    }

    @Test
    void empty() {
        assertThrows(TermException.class, () -> Term.valueOf(""));
    }

    @Test
    void blank() {
        assertThrows(TermException.class, () -> Term.valueOf("  "));
    }

    @Test
    void invalid() {
        assertThrows(TermException.class, () -> Term.valueOf("foo"));
    }

    @Test
    void oneNumber() {
        assertThrows(TermException.class, () -> Term.valueOf("1"));
    }

    @Test
    void oneOperator() {
        assertThrows(TermException.class, () -> Term.valueOf("+"));
    }

    @Test
    void noOperator() {
        assertThrows(TermException.class, () -> Term.valueOf("1 2"));
    }

    @Test
    void wrongOperator() {
        assertThrows(TermException.class, () -> Term.valueOf("10 & 2 % 3"));
    }

    @Test
    void eval() {
        assertEquals(10, _2Plus3Plus5.eval());
        assertEquals(11, _2Times3Plus5.eval());
        assertEquals(16, _2TimesInBrackets3Plus5.eval());
        assertEquals(25, inBrackets2Plus3Times5.eval());
        assertEquals(12, complex.eval(new Assignment("n", 5)));
    }

    @Test
    void print() {
        assertEquals("2 + 3 + 5", _2Plus3Plus5.print());
        assertEquals("2 * 3 + 5", _2Times3Plus5.print());
        assertEquals("2 * (3 + 5)", _2TimesInBrackets3Plus5.print());
        assertEquals("(2 + 3) * 5", inBrackets2Plus3Times5.print());
        assertEquals("10 * (3 - 2) + (4 + 6) / n", complex.print());
    }

    @Test
    void values() {
        assertArrayEquals(new int[] { 2, 3, 5 }, stream(_2Plus3Plus5.getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 5 }, stream(_2Times3Plus5.getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 5 }, stream(_2TimesInBrackets3Plus5.getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 5 }, stream(inBrackets2Plus3Times5.getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 4, 6, 10 }, stream(complex.getValues()).sorted().toArray());
    }

    @Test
    void operators() {
        assertIterableEquals(List.of(PLUS, PLUS), _2Plus3Plus5.getOperators());
        assertIterableEquals(List.of(TIMES, PLUS), _2Times3Plus5.getOperators());
        assertIterableEquals(List.of(TIMES, PLUS), _2TimesInBrackets3Plus5.getOperators());
        assertIterableEquals(List.of(PLUS, TIMES), inBrackets2Plus3Times5.getOperators());
        assertIterableEquals(List.of(TIMES, MINUS, PLUS, PLUS, DIVIDED), complex.getOperators());
    }
}
