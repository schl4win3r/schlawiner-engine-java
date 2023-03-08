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

import java.util.EnumMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.schlawiner.engine.term.TermBuilder.Order;

import static io.schlawiner.engine.term.Operator.DIVIDED;
import static io.schlawiner.engine.term.Operator.MINUS;
import static io.schlawiner.engine.term.Operator.PLUS;
import static io.schlawiner.engine.term.Operator.TIMES;
import static io.schlawiner.engine.term.TermBuilder.Order.LEFT_RIGHT;
import static io.schlawiner.engine.term.TermBuilder.Order.RIGHT_LEFT;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class TermTest {

    /**
     * <ul>
     * <li>Term: {@code 2 + 3 + 5}</li>
     * <li>RPN: {@code 2 3 + 5 +}</li>
     * <li>Graph
     *
     * <pre>
     *       +
     *    ┌──┴──┐
     *    +     5
     * ┌──┴──┐
     * 2     3
     * </pre>
     *
     * </li>
     * </ul>
     */
    private EnumMap<Order, Term> _2Plus3Plus5;

    /**
     * <ul>
     * <li>Term: {@code 2 * 3 + 5}</li>
     * <li>RPN: {@code 2 3 * 5 +}</li>
     * <li>Graph
     *
     * <pre>
     *       +
     *    ┌──┴──┐
     *    *     5
     * ┌──┴──┐
     * 2     3
     * </pre>
     *
     * </li>
     * </ul>
     */
    private EnumMap<Order, Term> _2Times3Plus5;

    /**
     * <ul>
     * <li>Term: {@code 2 * (3 + 5)}</li>
     * <li>RPN: {@code 2 3 5 + *}</li>
     * <li>Graph
     *
     * <pre>
     *       *
     *    ┌──┴──┐
     *    2     +
     *       ┌──┴──┐
     *       2     5
     * </pre>
     *
     * </li>
     * </ul>
     */
    private EnumMap<Order, Term> _2TimesInBrackets3Plus5;

    /**
     * <ul>
     * <li>Term: {@code (2 + 3) * 5}</li>
     * <li>RPN: {@code 2 3 + 5 *}</li>
     * <li>Graph
     *
     * <pre>
     *       *
     *    ┌──┴──┐
     *    +     5
     * ┌──┴──┐
     * 2     3
     * </pre>
     *
     * </li>
     * </ul>
     */
    private EnumMap<Order, Term> inBrackets2Plus3Times5;

    /**
     * <ul>
     * <li>Term: {@code 10 * (3 - 2) + (4 + 6) / n}</li>
     * <li>RPN: {@code 10 3 2 - * 4 6 + n / +}</li>
     * <li>Graph
     *
     * <pre>
     *             +
     *     ┌───────┴───────┐
     *     *               /
     *  ┌──┴──┐         ┌──┴──┐
     * 10     -         +     n
     *     ┌──┴──┐   ┌──┴──┐
     *     3     2   4     6
     * </pre>
     *
     * </li>
     * </ul>
     */
    private EnumMap<Order, Term> complex;

    @BeforeEach
    void setUp() {
        _2Plus3Plus5 = new EnumMap<>(Order.class);
        _2Plus3Plus5.put(LEFT_RIGHT, new TermBuilder(LEFT_RIGHT).op(PLUS).op(PLUS).val(2).val(3).val(5).build());
        _2Plus3Plus5.put(RIGHT_LEFT, new TermBuilder(RIGHT_LEFT).op(PLUS).val(5).op(PLUS).val(3).val(2).build());

        _2Times3Plus5 = new EnumMap<>(Order.class);
        _2Times3Plus5.put(LEFT_RIGHT, new TermBuilder(LEFT_RIGHT).op(PLUS).op(TIMES).val(2).val(3).val(5).build());
        _2Times3Plus5.put(RIGHT_LEFT, new TermBuilder(RIGHT_LEFT).op(PLUS).val(5).op(TIMES).val(3).val(2).build());

        _2TimesInBrackets3Plus5 = new EnumMap<>(Order.class);
        _2TimesInBrackets3Plus5.put(LEFT_RIGHT, new TermBuilder(LEFT_RIGHT).op(TIMES).val(2).op(PLUS).val(3).val(5).build());
        _2TimesInBrackets3Plus5.put(RIGHT_LEFT, new TermBuilder(RIGHT_LEFT).op(TIMES).op(PLUS).val(5).val(3).val(2).build());

        inBrackets2Plus3Times5 = new EnumMap<>(Order.class);
        inBrackets2Plus3Times5.put(LEFT_RIGHT, new TermBuilder(LEFT_RIGHT).op(TIMES).op(PLUS).val(2).val(3).val(5).build());
        inBrackets2Plus3Times5.put(RIGHT_LEFT, new TermBuilder(RIGHT_LEFT).op(TIMES).val(5).op(PLUS).val(3).val(2).build());

        complex = new EnumMap<>(Order.class);
        complex.put(LEFT_RIGHT, new TermBuilder(LEFT_RIGHT).op(PLUS).op(TIMES).val(10).op(Operator.MINUS).val(3).val(2)
                .op(DIVIDED).op(PLUS).val(4).val(6).var("n").build());
        complex.put(RIGHT_LEFT, new TermBuilder(RIGHT_LEFT).op(PLUS).op(DIVIDED).var("n").op(PLUS).val(6).val(4).op(TIMES)
                .op(Operator.MINUS).val(2).val(3).val(10).build());
    }

    @Test
    void eval() {
        assertEquals(10, _2Plus3Plus5.get(LEFT_RIGHT).eval());
        assertEquals(10, _2Plus3Plus5.get(RIGHT_LEFT).eval());
        assertEquals(11, _2Times3Plus5.get(LEFT_RIGHT).eval());
        assertEquals(11, _2Times3Plus5.get(RIGHT_LEFT).eval());
        assertEquals(16, _2TimesInBrackets3Plus5.get(LEFT_RIGHT).eval());
        assertEquals(16, _2TimesInBrackets3Plus5.get(RIGHT_LEFT).eval());
        assertEquals(25, inBrackets2Plus3Times5.get(LEFT_RIGHT).eval());
        assertEquals(25, inBrackets2Plus3Times5.get(RIGHT_LEFT).eval());
        assertEquals(12, complex.get(LEFT_RIGHT).assign("n", 5).eval());
        assertEquals(12, complex.get(RIGHT_LEFT).assign("n", 5).eval());
    }

    @Test
    void print() {
        assertEquals("2 + 3 + 5", _2Plus3Plus5.get(LEFT_RIGHT).print());
        assertEquals("2 + 3 + 5", _2Plus3Plus5.get(RIGHT_LEFT).print());
        assertEquals("2 * 3 + 5", _2Times3Plus5.get(LEFT_RIGHT).print());
        assertEquals("2 * 3 + 5", _2Times3Plus5.get(RIGHT_LEFT).print());
        assertEquals("2 * (3 + 5)", _2TimesInBrackets3Plus5.get(LEFT_RIGHT).print());
        assertEquals("2 * (3 + 5)", _2TimesInBrackets3Plus5.get(RIGHT_LEFT).print());
        assertEquals("(2 + 3) * 5", inBrackets2Plus3Times5.get(LEFT_RIGHT).print());
        assertEquals("(2 + 3) * 5", inBrackets2Plus3Times5.get(RIGHT_LEFT).print());
        assertEquals("10 * (3 - 2) + (4 + 6) / n", complex.get(LEFT_RIGHT).print());
        assertEquals("10 * (3 - 2) + (4 + 6) / n", complex.get(RIGHT_LEFT).print());
    }

    @Test
    void setValues() {
        _2Plus3Plus5.get(LEFT_RIGHT).assign(1, 2, 3);
        _2Plus3Plus5.get(RIGHT_LEFT).assign(1, 2, 3);
        assertEquals(6, _2Plus3Plus5.get(LEFT_RIGHT).eval());
        assertEquals(6, _2Plus3Plus5.get(RIGHT_LEFT).eval());
        assertEquals("1 + 2 + 3", _2Plus3Plus5.get(LEFT_RIGHT).print());
        assertEquals("1 + 2 + 3", _2Plus3Plus5.get(RIGHT_LEFT).print());
    }

    @Test
    void values() {
        assertArrayEquals(new int[] { 2, 3, 5 }, stream(_2Plus3Plus5.get(LEFT_RIGHT).getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 5 }, stream(_2Plus3Plus5.get(RIGHT_LEFT).getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 5 }, stream(_2Times3Plus5.get(LEFT_RIGHT).getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 5 }, stream(_2Times3Plus5.get(RIGHT_LEFT).getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 5 },
                stream(_2TimesInBrackets3Plus5.get(LEFT_RIGHT).getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 5 },
                stream(_2TimesInBrackets3Plus5.get(RIGHT_LEFT).getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 5 }, stream(inBrackets2Plus3Times5.get(LEFT_RIGHT).getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 5 }, stream(inBrackets2Plus3Times5.get(RIGHT_LEFT).getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 4, 6, 10 }, stream(complex.get(LEFT_RIGHT).getValues()).sorted().toArray());
        assertArrayEquals(new int[] { 2, 3, 4, 6, 10 }, stream(complex.get(RIGHT_LEFT).getValues()).sorted().toArray());
    }

    @Test
    void operators() {
        assertIterableEquals(List.of(PLUS, PLUS), _2Plus3Plus5.get(LEFT_RIGHT).getOperators());
        assertIterableEquals(List.of(PLUS, PLUS), _2Plus3Plus5.get(RIGHT_LEFT).getOperators());
        assertIterableEquals(List.of(TIMES, PLUS), _2Times3Plus5.get(LEFT_RIGHT).getOperators());
        assertIterableEquals(List.of(TIMES, PLUS), _2Times3Plus5.get(RIGHT_LEFT).getOperators());
        assertIterableEquals(List.of(TIMES, PLUS), _2TimesInBrackets3Plus5.get(LEFT_RIGHT).getOperators());
        assertIterableEquals(List.of(TIMES, PLUS), _2TimesInBrackets3Plus5.get(RIGHT_LEFT).getOperators());
        assertIterableEquals(List.of(PLUS, TIMES), inBrackets2Plus3Times5.get(LEFT_RIGHT).getOperators());
        assertIterableEquals(List.of(PLUS, TIMES), inBrackets2Plus3Times5.get(RIGHT_LEFT).getOperators());
        assertIterableEquals(List.of(TIMES, MINUS, PLUS, PLUS, DIVIDED), complex.get(LEFT_RIGHT).getOperators());
        assertIterableEquals(List.of(TIMES, MINUS, PLUS, PLUS, DIVIDED), complex.get(RIGHT_LEFT).getOperators());
    }
}
