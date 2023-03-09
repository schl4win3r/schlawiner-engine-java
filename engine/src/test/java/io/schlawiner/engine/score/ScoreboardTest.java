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

import io.schlawiner.engine.game.Numbers;
import io.schlawiner.engine.game.Player;
import io.schlawiner.engine.game.Players;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreboardTest {

    private Player foo;
    private Player bar;
    private Players players;
    private Numbers numbers;
    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        foo = Player.human("foo", 5);
        bar = Player.human("bar", 5);
        players = new Players(asList(foo, bar));
        numbers = new Numbers(8);
        scoreboard = new Scoreboard(players, numbers);
    }

    @Test
    void newInstance() {
        assertFalse(scoreboard.getNumberScores().isEmpty());
        assertFalse(scoreboard.getPlayerScores().isEmpty());
    }

    @Test
    void setScore() {
        scoreboard.setScore(0, foo, "1", 1);
        assertEquals(1, scoreboard.getNumberScores().get(0).getScore(foo).difference());
    }

    @Test
    void oneWinner() {
        scoreboard.setScore(0, foo, "0", 0);
        scoreboard.setScore(0, bar, "1", 1);
        scoreboard.setScore(1, foo, "1", 1);
        scoreboard.setScore(1, bar, "1", 1);

        assertEquals(1, scoreboard.getWinners().size());
        assertEquals(foo, scoreboard.getWinners().get(0));
    }

    @Test
    void twoWinner() {
        scoreboard.setScore(0, foo, "0", 0);
        scoreboard.setScore(0, bar, "0", 0);
        scoreboard.setScore(1, foo, "1", 1);
        scoreboard.setScore(1, bar, "1", 1);

        assertEquals(2, scoreboard.getWinners().size());
        assertTrue(scoreboard.getWinners().contains(foo));
        assertTrue(scoreboard.getWinners().contains(bar));
    }
}
