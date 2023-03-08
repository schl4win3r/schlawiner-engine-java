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

import static io.schlawiner.engine.term.Operator.DIVIDED;
import static io.schlawiner.engine.term.Operator.MINUS;
import static io.schlawiner.engine.term.Operator.PLUS;
import static io.schlawiner.engine.term.Operator.TIMES;
import static org.junit.jupiter.api.Assertions.*;

class OperatorTest {

    @Test
    void invalid() {
        assertNull(Operator.toOperator(null));
        assertNull(Operator.toOperator(""));
        assertNull(Operator.toOperator("    "));
        assertNull(Operator.toOperator("%"));
        assertNull(Operator.toOperator("foo"));
    }

    @Test
    void operators() {
        assertEquals(PLUS, Operator.toOperator("+"));
        assertEquals(MINUS, Operator.toOperator("-"));
        assertEquals(TIMES, Operator.toOperator("*"));
        assertEquals(DIVIDED, Operator.toOperator("/"));
    }
}