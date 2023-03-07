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

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayersTest {
    private Player foo;
    private Player bar;
    private Players players;

    @BeforeEach
    void setUp() {
        foo = Player.human("foo", 5);
        bar = Player.human("bar", 5);
        players = new Players(asList(foo, bar));
    }

    @Test
    void newInstance() {
        assertFalse(players.isFirst());
        assertFalse(players.isLast());
        assertFalse(players.isEmpty());
        assertNull(players.current());
    }

    @Test
    void lifecycle() {
        assertFalse(players.isFirst());
        assertFalse(players.isLast());
        assertEquals(2, players.size());
        assertNull(players.current());

        assertEquals(foo, players.next());
        assertTrue(players.isFirst());
        assertFalse(players.isLast());
        assertEquals(2, players.size());
        assertEquals(foo, players.current());

        assertEquals(bar, players.next());
        assertFalse(players.isFirst());
        assertTrue(players.isLast());
        assertEquals(2, players.size());
        assertEquals(bar, players.current());

        assertEquals(foo, players.next());
        assertTrue(players.isFirst());
        assertFalse(players.isLast());
        assertEquals(2, players.size());
        assertEquals(foo, players.current());
    }
}
