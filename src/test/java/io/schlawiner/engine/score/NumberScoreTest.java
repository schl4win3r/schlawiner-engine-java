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
package io.schlawiner.engine.score;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.schlawiner.engine.game.Player;
import io.schlawiner.engine.game.Players;

import static io.schlawiner.engine.score.Score.EMPTY;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberScoreTest {

    private Player foo;
    private Player bar;
    private Players players;
    private Score one = new Score("1", 1);
    private Score two = new Score("2", 2);
    private NumberScore numberScore;

    @BeforeEach
    void setUp() {
        foo = Player.human("foo", 5);
        bar = Player.human("bar", 5);
        players = new Players(asList(foo, bar));
        numberScore = new NumberScore(0, 42, players);
    }

    @Test
    void newInstance() {
        assertEquals(42, numberScore.number());
        assertEquals(EMPTY, numberScore.score(foo));
        assertEquals(EMPTY, numberScore.score(bar));
        assertFalse(numberScore.hasScore(foo));
        assertFalse(numberScore.hasScore(bar));
    }

    @Test
    void scores() {
        numberScore.score(foo, one);
        numberScore.score(bar, two);

        assertEquals(1, numberScore.score(foo).difference());
        assertEquals(2, numberScore.score(bar).difference());
        assertTrue(numberScore.hasScore(foo));
        assertTrue(numberScore.hasScore(bar));
    }
}
