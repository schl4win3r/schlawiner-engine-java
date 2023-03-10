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
import java.util.List;

import io.schlawiner.engine.term.Assignment;
import io.schlawiner.engine.term.Term;
import io.schlawiner.engine.term.TermException;

public class TermAlgorithm extends AbstractAlgorithm implements Serializable {

    private static final List<Term> SAME_NUMBERS = new ArrayList<>();
    private static final List<Term> DIFF_NUMBERS = new ArrayList<>();
    static {
        // a + b + c
        SAME_NUMBERS.add(Term.valueOf("a + b + c"));

        // a - b - c
        SAME_NUMBERS.add(Term.valueOf("a - b - c"));
        DIFF_NUMBERS.add(Term.valueOf("b - a - c"));
        DIFF_NUMBERS.add(Term.valueOf("c - a - b"));

        // a * b * c
        SAME_NUMBERS.add(Term.valueOf("a * b * c"));

        // a / b / c
        SAME_NUMBERS.add(Term.valueOf("a / b / c"));
        DIFF_NUMBERS.add(Term.valueOf("b / a / c"));
        DIFF_NUMBERS.add(Term.valueOf("c / a / b"));

        // a + b - c
        SAME_NUMBERS.add(Term.valueOf("a + b - c"));
        DIFF_NUMBERS.add(Term.valueOf("a + c - b"));
        DIFF_NUMBERS.add(Term.valueOf("b + c - a"));

        // a * b / c
        SAME_NUMBERS.add(Term.valueOf("a * b / c"));
        DIFF_NUMBERS.add(Term.valueOf("a * c / b"));
        DIFF_NUMBERS.add(Term.valueOf("b * c / a"));

        // a * b + c
        SAME_NUMBERS.add(Term.valueOf("a * b + c"));
        DIFF_NUMBERS.add(Term.valueOf("a * c + b"));
        DIFF_NUMBERS.add(Term.valueOf("b * c + a"));

        // (a + b) * c
        SAME_NUMBERS.add(Term.valueOf("(a + b) * c"));
        DIFF_NUMBERS.add(Term.valueOf("(a + c) * b"));
        DIFF_NUMBERS.add(Term.valueOf("(b + c) * a"));

        // a * b - c
        SAME_NUMBERS.add(Term.valueOf("a * b - c"));
        DIFF_NUMBERS.add(Term.valueOf("a * c - b"));
        DIFF_NUMBERS.add(Term.valueOf("b * c - a"));

        // a - b * c
        SAME_NUMBERS.add(Term.valueOf("a - b * c"));
        DIFF_NUMBERS.add(Term.valueOf("b - a * c"));
        DIFF_NUMBERS.add(Term.valueOf("c - a * b"));

        // (a - b) * c
        SAME_NUMBERS.add(Term.valueOf("(a - b) * c"));
        DIFF_NUMBERS.add(Term.valueOf("(b - a) * c"));
        DIFF_NUMBERS.add(Term.valueOf("(a - c) * b"));
        DIFF_NUMBERS.add(Term.valueOf("(c - a) * b"));
        DIFF_NUMBERS.add(Term.valueOf("(b - c) * a"));
        DIFF_NUMBERS.add(Term.valueOf("(c - b) * a"));

        // a / b + c
        SAME_NUMBERS.add(Term.valueOf("a / b + c"));
        DIFF_NUMBERS.add(Term.valueOf("b / a + c"));
        DIFF_NUMBERS.add(Term.valueOf("a / c + b"));
        DIFF_NUMBERS.add(Term.valueOf("c / a + b"));
        DIFF_NUMBERS.add(Term.valueOf("b / c + a"));
        DIFF_NUMBERS.add(Term.valueOf("c / b + a"));

        // (a + b) / c
        SAME_NUMBERS.add(Term.valueOf("(a + b) / c"));
        DIFF_NUMBERS.add(Term.valueOf("(a + c) / b"));
        DIFF_NUMBERS.add(Term.valueOf("(b + c) / a"));

        // a / (b + c)
        SAME_NUMBERS.add(Term.valueOf("a / (b + c)"));
        DIFF_NUMBERS.add(Term.valueOf("b / (a + c)"));
        DIFF_NUMBERS.add(Term.valueOf("c / (a + b)"));

        // a / b - c
        SAME_NUMBERS.add(Term.valueOf("a / b - c"));
        DIFF_NUMBERS.add(Term.valueOf("a / c - b"));
        DIFF_NUMBERS.add(Term.valueOf("b / a - a"));
        DIFF_NUMBERS.add(Term.valueOf("b / c - a"));
        DIFF_NUMBERS.add(Term.valueOf("c / a - b"));
        DIFF_NUMBERS.add(Term.valueOf("c / b - a"));

        // a - b / c
        SAME_NUMBERS.add(Term.valueOf("a - b / c"));
        DIFF_NUMBERS.add(Term.valueOf("a - c / b"));
        DIFF_NUMBERS.add(Term.valueOf("b - a / a"));
        DIFF_NUMBERS.add(Term.valueOf("b - c / a"));
        DIFF_NUMBERS.add(Term.valueOf("c - a / b"));
        DIFF_NUMBERS.add(Term.valueOf("c - b / a"));

        // (a - b) / c
        SAME_NUMBERS.add(Term.valueOf("(a - b) / c"));
        DIFF_NUMBERS.add(Term.valueOf("(a - c) / b"));
        DIFF_NUMBERS.add(Term.valueOf("(b - a) / a"));
        DIFF_NUMBERS.add(Term.valueOf("(b - c) / a"));
        DIFF_NUMBERS.add(Term.valueOf("(c - a) / b"));
        DIFF_NUMBERS.add(Term.valueOf("(c - b) / a"));

        // a / (b - c)
        SAME_NUMBERS.add(Term.valueOf("a / (b - c)"));
        DIFF_NUMBERS.add(Term.valueOf("a / (c - b)"));
        DIFF_NUMBERS.add(Term.valueOf("b / (a - a)"));
        DIFF_NUMBERS.add(Term.valueOf("b / (c - a)"));
        DIFF_NUMBERS.add(Term.valueOf("c / (a - b)"));
        DIFF_NUMBERS.add(Term.valueOf("c / (b - a)"));
    }

    TermAlgorithm() {
        super("Algorithm based on variable terms");
    }

    @Override
    protected void computePermutation(final int a, final int b, final int c, final int target, final Solutions solutions) {
        Assignment[] assignments = new Assignment[] { new Assignment("a", a), new Assignment("b", b), new Assignment("c", c) };
        for (Term term : SAME_NUMBERS) {
            try {
                solutions.add(new Solution(term.print(assignments), term.eval(assignments)));
            } catch (TermException ignore) {
            }
        }
        if (!sameDiceNumbers(a, b, c)) {
            for (Term term : DIFF_NUMBERS) {
                try {
                    solutions.add(new Solution(term.print(assignments), term.eval(assignments)));
                } catch (TermException ignore) {
                }
            }
        }
    }
}
