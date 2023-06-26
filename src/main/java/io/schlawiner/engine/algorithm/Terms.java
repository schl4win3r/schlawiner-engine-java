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

import io.schlawiner.engine.term.Term;

interface Terms {

    // a + b + c
    Term ADD_ABC = Term.valueOf("a + b + c");

    // a - b - c
    Term SUBTRACT_ABC = Term.valueOf("a - b - c");
    Term SUBTRACT_BAC = Term.valueOf("b - a - c");
    Term SUBTRACT_CAB = Term.valueOf("c - a - b");

    // a * b * c
    Term MULTIPLY_ABC = Term.valueOf("a * b * c");

    // a / b / c
    Term DIVIDE_ABC = Term.valueOf("a / b / c");
    Term DIVIDE_BAC = Term.valueOf("b / a / c");
    Term DIVIDE_CAB = Term.valueOf("c / a / b");

    // a + b - c
    Term ADD_SUBTRACT_ABC = Term.valueOf("a + b - c");
    Term ADD_SUBTRACT_ACB = Term.valueOf("a + c - b");
    Term ADD_SUBTRACT_BCA = Term.valueOf("b + c - a");

    // a * b / c
    Term MULTIPLY_DIVIDE_ABC = Term.valueOf("a * b / c");
    Term MULTIPLY_DIVIDE_ACB = Term.valueOf("a * c / b");
    Term MULTIPLY_DIVIDE_BCA = Term.valueOf("b * c / a");

    // a * b + c
    Term MULTIPLY_ADD_ABC = Term.valueOf("a * b + c");
    Term MULTIPLY_ADD_ACB = Term.valueOf("a * c + b");
    Term MULTIPLY_ADD_BCA = Term.valueOf("b * c + a");

    // (a + b) * c
    Term ADD_MULTIPLY_ABC = Term.valueOf("(a + b) * c");
    Term ADD_MULTIPLY_ACB = Term.valueOf("(a + c) * b");
    Term ADD_MULTIPLY_BCA = Term.valueOf("(b + c) * a");

    // a * b - c
    Term MULTIPLY_SUBTRACT_1_ABC = Term.valueOf("a * b - c");
    Term MULTIPLY_SUBTRACT_1_ACB = Term.valueOf("a * c - b");
    Term MULTIPLY_SUBTRACT_1_BCA = Term.valueOf("b * c - a");

    // a - b * c
    Term MULTIPLY_SUBTRACT_2_ABC = Term.valueOf("a - b * c");
    Term MULTIPLY_SUBTRACT_2_BAC = Term.valueOf("b - a * c");
    Term MULTIPLY_SUBTRACT_2_CAB = Term.valueOf("c - a * b");

    // (a - b) * c
    Term SUBTRACT_MULTIPLY_ABC = Term.valueOf("(a - b) * c");
    Term SUBTRACT_MULTIPLY_BAC = Term.valueOf("(b - a) * c");
    Term SUBTRACT_MULTIPLY_ACB = Term.valueOf("(a - c) * b");
    Term SUBTRACT_MULTIPLY_CAB = Term.valueOf("(c - a) * b");
    Term SUBTRACT_MULTIPLY_BCA = Term.valueOf("(b - c) * a");
    Term SUBTRACT_MULTIPLY_CBA = Term.valueOf("(c - b) * a");

    // a / b + c
    Term DIVIDE_ADD_ABC = Term.valueOf("a / b + c");
    Term DIVIDE_ADD_BAC = Term.valueOf("b / a + c");
    Term DIVIDE_ADD_ACB = Term.valueOf("a / c + b");
    Term DIVIDE_ADD_CAB = Term.valueOf("c / a + b");
    Term DIVIDE_ADD_BCA = Term.valueOf("b / c + a");
    Term DIVIDE_ADD_CBA = Term.valueOf("c / b + a");

    // (a + b) / c
    Term ADD_DIVIDE_1_ABC = Term.valueOf("(a + b) / c");
    Term ADD_DIVIDE_1_ACB = Term.valueOf("(a + c) / b");
    Term ADD_DIVIDE_1_BCA = Term.valueOf("(b + c) / a");

    // a / (b + c)
    Term ADD_DIVIDE_2_ABC = Term.valueOf("a / (b + c)");
    Term ADD_DIVIDE_2_BAC = Term.valueOf("b / (a + c)");
    Term ADD_DIVIDE_2_CAB = Term.valueOf("c / (a + b)");

    // a / b - c
    Term DIVIDE_SUBTRACT_1_ABC = Term.valueOf("a / b - c");
    Term DIVIDE_SUBTRACT_1_ACB = Term.valueOf("a / c - b");
    Term DIVIDE_SUBTRACT_1_BAC = Term.valueOf("b / a - c");
    Term DIVIDE_SUBTRACT_1_BCA = Term.valueOf("b / c - a");
    Term DIVIDE_SUBTRACT_1_CAB = Term.valueOf("c / a - b");
    Term DIVIDE_SUBTRACT_1_CBA = Term.valueOf("c / b - a");

    // a - b / c
    Term DIVIDE_SUBTRACT_2_ABC = Term.valueOf("a - b / c");
    Term DIVIDE_SUBTRACT_2_ACB = Term.valueOf("a - c / b");
    Term DIVIDE_SUBTRACT_2_BAC = Term.valueOf("b - a / c");
    Term DIVIDE_SUBTRACT_2_BCA = Term.valueOf("b - c / a");
    Term DIVIDE_SUBTRACT_2_CAB = Term.valueOf("c - a / b");
    Term DIVIDE_SUBTRACT_2_CBA = Term.valueOf("c - b / a");

    // (a - b) / c
    Term SUBTRACT_DIVIDE_1_ABC = Term.valueOf("(a - b) / c");
    Term SUBTRACT_DIVIDE_1_ACB = Term.valueOf("(a - c) / b");
    Term SUBTRACT_DIVIDE_1_BAC = Term.valueOf("(b - a) / c");
    Term SUBTRACT_DIVIDE_1_BCA = Term.valueOf("(b - c) / a");
    Term SUBTRACT_DIVIDE_1_CAB = Term.valueOf("(c - a) / b");
    Term SUBTRACT_DIVIDE_1_CBA = Term.valueOf("(c - b) / a");

    // a / (b - c)
    Term SUBTRACT_DIVIDE_2_ABC = Term.valueOf("a / (b - c)");
    Term SUBTRACT_DIVIDE_2_ACB = Term.valueOf("a / (c - b)");
    Term SUBTRACT_DIVIDE_2_BAC = Term.valueOf("b / (a - c)");
    Term SUBTRACT_DIVIDE_2_BCA = Term.valueOf("b / (c - a)");
    Term SUBTRACT_DIVIDE_2_CAB = Term.valueOf("c / (a - b)");
    Term SUBTRACT_DIVIDE_2_CBA = Term.valueOf("c / (b - a)");
}
