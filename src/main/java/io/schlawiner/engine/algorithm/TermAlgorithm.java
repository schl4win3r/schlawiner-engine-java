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

import java.util.ArrayList;
import java.util.List;

import io.schlawiner.engine.term.Assignment;
import io.schlawiner.engine.term.Term;
import io.schlawiner.engine.term.TermException;

public class TermAlgorithm extends AbstractAlgorithm implements Terms {

    private static final List<Term> ABC = new ArrayList<>();
    private static final List<Term> PERMUTATIONS = new ArrayList<>();

    static {
        // a + b + c
        ABC.add(ADD_ABC);

        // a - b - c
        ABC.add(SUBTRACT_ABC);
        PERMUTATIONS.add(SUBTRACT_BAC);
        PERMUTATIONS.add(SUBTRACT_CAB);

        // a * b * c
        ABC.add(MULTIPLY_ABC);

        // a / b / c
        ABC.add(DIVIDE_ABC);
        PERMUTATIONS.add(DIVIDE_BAC);
        PERMUTATIONS.add(DIVIDE_CAB);

        // a + b - c
        ABC.add(ADD_SUBTRACT_ABC);
        PERMUTATIONS.add(ADD_SUBTRACT_ACB);
        PERMUTATIONS.add(ADD_SUBTRACT_BCA);

        // a * b / c
        ABC.add(MULTIPLY_DIVIDE_ABC);
        PERMUTATIONS.add(MULTIPLY_DIVIDE_ACB);
        PERMUTATIONS.add(MULTIPLY_DIVIDE_BCA);

        // a * b + c
        ABC.add(MULTIPLY_ADD_ABC);
        PERMUTATIONS.add(MULTIPLY_ADD_ACB);
        PERMUTATIONS.add(MULTIPLY_ADD_BCA);

        // (a + b) * c
        ABC.add(ADD_MULTIPLY_ABC);
        PERMUTATIONS.add(ADD_MULTIPLY_ACB);
        PERMUTATIONS.add(ADD_MULTIPLY_BCA);

        // a * b - c
        ABC.add(MULTIPLY_SUBTRACT_1_ABC);
        PERMUTATIONS.add(MULTIPLY_SUBTRACT_1_ACB);
        PERMUTATIONS.add(MULTIPLY_SUBTRACT_1_BCA);

        // a - b * c
        ABC.add(MULTIPLY_SUBTRACT_2_ABC);
        PERMUTATIONS.add(MULTIPLY_SUBTRACT_2_BAC);
        PERMUTATIONS.add(MULTIPLY_SUBTRACT_2_CAB);

        // (a - b) * c
        ABC.add(SUBTRACT_MULTIPLY_ABC);
        PERMUTATIONS.add(SUBTRACT_MULTIPLY_BAC);
        PERMUTATIONS.add(SUBTRACT_MULTIPLY_ACB);
        PERMUTATIONS.add(SUBTRACT_MULTIPLY_CAB);
        PERMUTATIONS.add(SUBTRACT_MULTIPLY_BCA);
        PERMUTATIONS.add(SUBTRACT_MULTIPLY_CBA);

        // a / b + c
        ABC.add(DIVIDE_ADD_ABC);
        PERMUTATIONS.add(DIVIDE_ADD_BAC);
        PERMUTATIONS.add(DIVIDE_ADD_ACB);
        PERMUTATIONS.add(DIVIDE_ADD_CAB);
        PERMUTATIONS.add(DIVIDE_ADD_BCA);
        PERMUTATIONS.add(DIVIDE_ADD_CBA);

        // (a + b) / c
        ABC.add(ADD_DIVIDE_1_ABC);
        PERMUTATIONS.add(ADD_DIVIDE_1_ACB);
        PERMUTATIONS.add(ADD_DIVIDE_1_BCA);

        // a / (b + c)
        ABC.add(ADD_DIVIDE_2_ABC);
        PERMUTATIONS.add(ADD_DIVIDE_2_BAC);
        PERMUTATIONS.add(ADD_DIVIDE_2_CAB);

        // a / b - c
        ABC.add(DIVIDE_SUBTRACT_1_ABC);
        PERMUTATIONS.add(DIVIDE_SUBTRACT_1_ACB);
        PERMUTATIONS.add(DIVIDE_SUBTRACT_1_BAC);
        PERMUTATIONS.add(DIVIDE_SUBTRACT_1_BCA);
        PERMUTATIONS.add(DIVIDE_SUBTRACT_1_CAB);
        PERMUTATIONS.add(DIVIDE_SUBTRACT_1_CBA);

        // a - b / c
        ABC.add(DIVIDE_SUBTRACT_2_ABC);
        PERMUTATIONS.add(DIVIDE_SUBTRACT_2_ACB);
        PERMUTATIONS.add(DIVIDE_SUBTRACT_2_BAC);
        PERMUTATIONS.add(DIVIDE_SUBTRACT_2_BCA);
        PERMUTATIONS.add(DIVIDE_SUBTRACT_2_CAB);
        PERMUTATIONS.add(DIVIDE_SUBTRACT_2_CBA);

        // (a - b) / c
        ABC.add(SUBTRACT_DIVIDE_1_ABC);
        PERMUTATIONS.add(SUBTRACT_DIVIDE_1_ACB);
        PERMUTATIONS.add(SUBTRACT_DIVIDE_1_BAC);
        PERMUTATIONS.add(SUBTRACT_DIVIDE_1_BCA);
        PERMUTATIONS.add(SUBTRACT_DIVIDE_1_CAB);
        PERMUTATIONS.add(SUBTRACT_DIVIDE_1_CBA);

        // a / (b - c)
        ABC.add(SUBTRACT_DIVIDE_2_ABC);
        PERMUTATIONS.add(SUBTRACT_DIVIDE_2_ACB);
        PERMUTATIONS.add(SUBTRACT_DIVIDE_2_BAC);
        PERMUTATIONS.add(SUBTRACT_DIVIDE_2_BCA);
        PERMUTATIONS.add(SUBTRACT_DIVIDE_2_CAB);
        PERMUTATIONS.add(SUBTRACT_DIVIDE_2_CBA);
    }

    TermAlgorithm() {
        super("Algorithm based on variable terms");
    }

    @Override
    protected void computePermutation(final int a, final int b, final int c, final int target, final Solutions solutions) {
        // @formatter:off
        Assignment[] assignments = new Assignment[] {
                new Assignment("a", a),
                new Assignment("b", b),
                new Assignment("c", c)
        };
        // @formatter:on

        for (Term term : ABC) {
            try {
                solutions.add(new Solution(term.print(assignments), term.eval(assignments)));
            } catch (TermException ignore) {
            }
        }
        if (differentDiceNumbers(a, b, c)) {
            for (Term term : PERMUTATIONS) {
                try {
                    solutions.add(new Solution(term.print(assignments), term.eval(assignments)));
                } catch (TermException ignore) {
                }
            }
        }
    }
}
