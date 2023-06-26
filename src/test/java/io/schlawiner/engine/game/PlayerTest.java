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
package io.schlawiner.engine.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {

    private Player foo;
    private Player hal;

    @BeforeEach
    void setUp() {
        foo = Player.human("foo", 5);
        hal = Player.computer("hal");
    }

    @Test
    void human() {
        assertTrue(foo.human());
        assertFalse(hal.human());
    }

    @Test
    void retry() {
        assertEquals(5, foo.retries());
        foo.retry();
        assertEquals(4, foo.retries());
    }
}
