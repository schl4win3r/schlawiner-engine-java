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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InfixToRPNTest {

    // @formatter:off
    private static final String[][] PERMUTATIONS = new String[][] {
            { "a + b + c", "a b + c +" },
            { "a - b - c", "a b - c -" },
            { "a * b * c", "a b * c *" },
            { "a / b / c", "a b / c /" },
            { "a + b - c", "a b + c -" },
            { "a * b / c", "a b * c /" },
            { "a * b + c", "a b * c +" },
            { "(a + b) * c", "a b + c *" },
            { "a * b - c", "a b * c -" },
            { "a - b * c", "a b c * -" },
            { "(a - b) * c", "a b - c *" },
            { "a / b + c", "a b / c +" },
            { "(a + b) / c", "a b + c /" },
            { "a / (b + c)", "a b c + /" },
            { "a / b - c", "a b / c -" },
            { "a - b / c", "a b c / -" },
            { "(a - b) / c", "a b - c /" },
            { "a / (b - c)", "a b c - /" },
    };
    // @formatter:on

    @Test
    void nil() {
        assertArrayEquals(new String[0], InfixToRPN.infixToRPN(null));
    }

    @Test
    void empty() {
        assertArrayEquals(new String[0], InfixToRPN.infixToRPN(""));
    }

    @Test
    void blank() {
        assertArrayEquals(new String[0], InfixToRPN.infixToRPN("  "));
    }

    @Test
    void permutations() {
        for (int i = 0; i < PERMUTATIONS.length; i++) {
            String[] permutation = PERMUTATIONS[i];
            assertEquals(permutation[1], String.join(" ", InfixToRPN.infixToRPN(permutation[0])), "Failed at index " + i);
        }
    }
}
