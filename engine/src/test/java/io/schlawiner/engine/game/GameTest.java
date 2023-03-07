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

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.schlawiner.engine.algorithm.OperationAlgorithm;
import io.schlawiner.engine.algorithm.Solution;
import io.schlawiner.engine.score.Scoreboard;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    private Player foo;
    private Player computer;
    private Game game;

    @BeforeEach
    void setUp() {
        Settings settings = Settings.defaults().withLevel(Level.HARD);
        foo = Player.human("foo", settings.retries());
        computer = Player.computer("computer");
        game = new Game("test-game", new Players(asList(foo, computer)), new Numbers(new int[] { 16, 23, 42 }),
                new OperationAlgorithm(), settings);
    }

    @Test
    void humanVsComputerDraw() {
        String term;
        Calculation calculation;
        Solution solution;

        // 16
        term = "10 + 2 * 3";
        game.dice(new Dice(1, 2, 3));
        game.next(); // foo
        calculation = game.calculate(term);
        game.score(term, calculation.getDifference());
        game.next(); // computer
        solution = game.solve();
        game.score(solution);

        // 23
        term = "30 - 10 + 4";
        game.dice(new Dice(4, 3, 1));
        game.next(); // foo
        calculation = game.calculate(term);
        game.score(term, calculation.getDifference());
        game.next(); // computer
        solution = game.solve();
        game.score(solution);

        // 42
        term = "50 - 6 - 2";
        game.dice(new Dice(2, 5, 6));
        game.next(); // foo
        calculation = game.calculate(term);
        game.score(term, calculation.getDifference());
        game.next(); // computer
        solution = game.solve();
        game.score(solution);

        // game over
        final Scoreboard scoreboard = game.getScoreboard();
        final int fooScore = scoreboard.getSummedScore(foo);
        final int computerScore = scoreboard.getSummedScore(computer);

        assertEquals(fooScore, computerScore);
        final List<Player> winners = scoreboard.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(foo));
        assertTrue(winners.contains(computer));
    }
}
