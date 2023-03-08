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
package io.schlawiner.engine.algorithm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.schlawiner.engine.term.Operator;
import io.schlawiner.engine.term.Term;
import io.schlawiner.engine.term.TermBuilder;

import static io.schlawiner.engine.term.TermBuilder.Order.LEFT_RIGHT;

public class TermAlgorithm extends AbstractAlgorithm implements Serializable {

    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";

    private final List<Term> terms;

    TermAlgorithm() {
        super("Algorithm based on variable terms");
        terms = new ArrayList<>();

        // a + b + c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.PLUS).op(Operator.PLUS).var(A).var(B).var(C).build());
        // a - b - c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.MINUS).op(Operator.MINUS).var(A).var(B).var(C).build());
        // a * b * c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.TIMES).op(Operator.TIMES).var(A).var(B).var(C).build());
        // a / b / c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.DIVIDED).op(Operator.DIVIDED).var(A).var(B).var(C).build());
        // a + b - c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.MINUS).op(Operator.PLUS).var(A).var(B).var(C).build());
        // a * b / c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.DIVIDED).op(Operator.TIMES).var(A).var(B).var(C).build());
        // a * b + c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.PLUS).op(Operator.TIMES).var(A).var(B).var(C).build());
        // (a + b) * c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.TIMES).op(Operator.PLUS).var(A).var(B).var(C).build());
        // a * b - c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.MINUS).op(Operator.TIMES).var(A).var(B).var(C).build());
        // a - b * c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.MINUS).var(A).op(Operator.TIMES).var(B).var(C).build());
        // (a - b) * c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.TIMES).op(Operator.MINUS).var(A).var(B).var(C).build());
        // a / b + c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.PLUS).op(Operator.DIVIDED).var(A).var(B).var(C).build());
        // (a + b) / c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.DIVIDED).op(Operator.PLUS).var(A).var(B).var(C).build());
        // a / (b + c)
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.DIVIDED).var(A).op(Operator.PLUS).var(B).var(C).build());
        // a / b - c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.MINUS).op(Operator.DIVIDED).var(A).var(B).var(C).build());
        // a - b / c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.MINUS).var(A).op(Operator.DIVIDED).var(B).var(C).build());
        // (a - b) / c
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.DIVIDED).op(Operator.MINUS).var(A).var(B).var(C).build());
        // a / (b - c)
        terms.add(new TermBuilder(LEFT_RIGHT).op(Operator.DIVIDED).var(A).op(Operator.MINUS).var(B).var(C).build());
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void computePermutation(final int a, final int b, final int c, final int target, final Solutions solutions) {
        final Iterator<Term> iter = terms.iterator();

        // a + b + c
        Term term = iter.next();
        add(term.assign(a, b, c), solutions);

        // a - b - c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(c, a, b), solutions);
        }

        // a * b * c
        term = iter.next();
        add(term.assign(a, b, c), solutions);

        // a / b / c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(c, a, b), solutions);
        }

        // a + b - c
        term = iter.next();
        add(term.assign(b, c, a), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(a, b, c), solutions);
        }

        // a * b / c
        term = iter.next();
        add(term.assign(b, c, a), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(a, b, c), solutions);
        }

        // a * b + c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, c, a), solutions);
        }

        // (a + b) * c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, c, a), solutions);
        }

        // a * b - c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, c, a), solutions);
        }

        // a - b * c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(c, a, b), solutions);
        }

        // (a - b) * c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(a, c, b), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, b, a), solutions);
        }

        // a / b + c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(a, c, b), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, b, a), solutions);
        }

        // (a + b) / c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, c, a), solutions);
        }

        // a / (b + c)
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(b, a, c), solutions);
            add(term.assign(c, a, b), solutions);
        }

        // a / b - c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, a, a), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(c, b, a), solutions);
        }

        // a - b / c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, a, a), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(c, b, a), solutions);
        }

        // (a - b) / c
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, a, a), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(c, b, a), solutions);
        }

        // a / (b - c)
        term = iter.next();
        add(term.assign(a, b, c), solutions);
        if (!sameDiceNumbers(a, b, c)) {
            add(term.assign(a, c, b), solutions);
            add(term.assign(b, a, a), solutions);
            add(term.assign(b, c, a), solutions);
            add(term.assign(c, a, b), solutions);
            add(term.assign(c, b, a), solutions);
        }
    }

    private void add(final Term term, final Solutions solutions) {
        try {
            solutions.add(new Solution(term.print(), term.eval()));
        } catch (final ArithmeticException ignore) {
            // no valid result
        }
    }
}
