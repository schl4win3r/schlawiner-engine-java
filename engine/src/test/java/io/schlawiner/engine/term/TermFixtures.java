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

interface TermFixtures {

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
    Term _2Plus3Plus5 = Term.valueOf("2 + 3 + 5");

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
    Term _2Times3Plus5 = Term.valueOf("2 * 3 + 5");

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
    Term _2TimesInBrackets3Plus5 = Term.valueOf("2 * (3 + 5)");

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
    Term inBrackets2Plus3Times5 = Term.valueOf("(2 + 3) * 5");

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
    Term complex = Term.valueOf("10 * (3 - 2) + (4 + 6) / n");
}
