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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TermTest {

    private Term _2Plus3Plus5;
    private Term _2Times3Plus5;
    private Term _2TimesInBrackets3Plus5;
    private Term inBrackets2Plus3Times5;
    private Term complex;

    @BeforeEach
    void setUp() {
        // 2 + 3 + 5
        _2Plus3Plus5 = new TermBuilder().op(Operator.PLUS).op(Operator.PLUS).val(2).val(3).val(5).build();

        // 2 * 3 + 5
        _2Times3Plus5 = new TermBuilder().op(Operator.PLUS).op(Operator.TIMES).val(2).val(3).val(5).build();

        // 2 * (3 + 5)
        _2TimesInBrackets3Plus5 = new TermBuilder().op(Operator.TIMES).val(2).op(Operator.PLUS).val(3).val(5).build();

        // (2 + 3) * 5
        inBrackets2Plus3Times5 = new TermBuilder().op(Operator.TIMES).op(Operator.PLUS).val(2).val(3).val(5).build();

        // 10 * (3 - 2) + (4 + 6) / n
        complex = new TermBuilder().op(Operator.PLUS).op(Operator.TIMES).val(10).op(Operator.MINUS).val(3).val(2)
                .op(Operator.DIVIDED).op(Operator.PLUS).val(4).val(6).var("n").build();
    }

    @Test
    void eval() {
        assertEquals(10, _2Plus3Plus5.eval());
        assertEquals(11, _2Times3Plus5.eval());
        assertEquals(16, _2TimesInBrackets3Plus5.eval());
        assertEquals(25, inBrackets2Plus3Times5.eval());
        assertEquals(12, complex.assign("n", 5).eval());
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
    void setValues() {
        _2Plus3Plus5.assign(1, 2, 3);
        assertEquals(6, _2Plus3Plus5.eval());
        assertEquals("1 + 2 + 3", _2Plus3Plus5.print());
    }
}
