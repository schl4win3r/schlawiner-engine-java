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

class PlayerScoreTest {
    private Player foo;
    private Numbers numbers;
    private Score one = new Score("1", 1);
    private Score two = new Score("2", 2);
    private PlayerScore playerScore;

    @BeforeEach
    void setUp() {
        foo = Player.human("foo", 5);
        numbers = new Numbers(8);
        playerScore = new PlayerScore(foo, numbers);
    }

    @Test
    void newInstance() {
        // assertEquals(42, playerScore.getNumber());
        // assertEquals(EMPTY, playerScore.getScore(me));
        // assertEquals(EMPTY, playerScore.getScore(computer));
        // assertEquals(EMPTY, playerScore.getScore(foo));
        // assertFalse(playerScore.hasScore(me));
        // assertFalse(playerScore.hasScore(computer));
        // assertFalse(playerScore.hasScore(foo));
    }

    @Test
    void scores() {
        // playerScore.setScore(me, one);
        // playerScore.setScore(foo, two);
        //
        // assertEquals(1, playerScore.getScore(me).getDifference());
        // assertEquals(EMPTY, playerScore.getScore(computer));
        // assertEquals(EMPTY, playerScore.getScore(foo));
        // assertTrue(playerScore.hasScore(me));
        // assertFalse(playerScore.hasScore(computer));
        // assertFalse(playerScore.hasScore(foo));
    }
}
