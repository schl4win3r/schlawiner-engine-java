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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SolutionsTest {

    private Solutions solutions;

    @Test
    void bestSolutionInRange() {
        solutions = new Solutions(23, 10);
        solutions.add(new Solution("term 1", 20));
        solutions.add(new Solution("term 2", 20));
        solutions.add(new Solution("term 3", 22));
        solutions.add(new Solution("term 4", 24));
        solutions.add(new Solution("term 5", 24));
        solutions.add(new Solution("term 6", 24));
        solutions.add(new Solution("term 7", 25));
        solutions.add(new Solution("term 8", 26));

        final Solution solution = solutions.bestSolution();
        assertEquals(22, solution.value());
    }

    @Test
    void bestSolutionsOutsideRange() {
        solutions = new Solutions(33, 10);
        solutions.add(new Solution("", 50));
        assertNull(solutions.bestSolution());
    }
}
