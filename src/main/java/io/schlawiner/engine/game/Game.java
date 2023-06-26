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

import java.util.Objects;
import java.util.UUID;

import io.schlawiner.engine.algorithm.Algorithm;
import io.schlawiner.engine.algorithm.Solution;
import io.schlawiner.engine.algorithm.Solutions;
import io.schlawiner.engine.score.Scoreboard;
import io.schlawiner.engine.term.Term;
import io.schlawiner.engine.term.TermException;

import static java.lang.Math.abs;

/**
 * Implements the basic game workflow w/o timout handling. Timeouts are meant to be implemented outside this class.
 */
public class Game {

    private final String id;
    private final String name;
    private final Players players;
    private final Numbers numbers;
    private final Algorithm algorithm;
    private final Settings settings;
    private final Scoreboard scoreboard;
    private Dice dice;
    private boolean canceled;

    public Game(final String name, final Players players, final Numbers numbers, final Algorithm algorithm,
            final Settings settings) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.players = players;
        this.numbers = numbers;
        this.algorithm = algorithm;
        this.settings = settings;
        this.scoreboard = new Scoreboard(players, numbers);
        this.canceled = false;

        for (Player player : this.players) {
            player.retries(settings.retries());
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game game)) {
            return false;
        }
        return id.equals(game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Passes the dice to the next player. If the player is the first player, then it's the next number's turn.
     */
    public void next() {
        players.next();
        if (players.first()) {
            numbers.next();
        }
    }

    /**
     * @return {@code true} if there are more numbers or if it's not the last player and the game was not canceled,
     *         {@code false} otherwise
     */
    public boolean hasNext() {
        return (numbers.hasNext() || !players.last()) && !canceled;
    }

    /**
     * Sets the specified dice numbers.
     */
    public void dice(final Dice dice) {
        this.dice = dice;
    }

    /**
     * If the current player is human and has retries left, its retry count is decreased and new dice numbers are set. Otherwise
     * this method does nothing.
     *
     * @return {@code true} if retry was successful, {@code false} otherwise
     */
    public boolean retry() {
        Player currentPlayer = players.current();
        if (currentPlayer.human() && currentPlayer.retries() > 0) {
            currentPlayer.retry();
            dice(Dice.random());
            return true;
        }
        return false;
    }

    /**
     * Skips the current number and scores {@link Settings#penalty()} points as penalty. Does <strong>not</strong> call
     * {@link #next()}
     */
    public void skip() {
        score("Skipped", settings.penalty());
    }

    /**
     * Cancels this game
     */
    public void cancel() {
        canceled = true;
    }

    /**
     * Scores {@link Settings#penalty()} points as penalty. Meant to be called after a timeout.
     */
    public void timeout() {
        score("Timeout", settings.penalty());
    }

    /**
     * Calculates the specified term for the current dice numbers and current target number. Stores the difference in the score
     * board.
     * <p>
     * Meant to be called for human players.
     *
     * @return the difference between the calculated solution and the current number
     * @throws DiceException if the dice numbers aren't used correctly
     * @throws TermException if the expression isn't a valid term
     */
    public Calculation calculate(final String expression) throws DiceException, TermException {
        Calculation calculation;
        Term term = Term.valueOf(expression);
        DiceValidator.validate(dice, term);

        int result = term.eval();
        int difference = abs(result - numbers.current());
        if (difference > 0) {
            Solutions solutions = algorithm.compute(dice.numbers()[0], dice.numbers()[1], dice.numbers()[2], numbers.current());
            calculation = new Calculation(term, numbers.current(), solutions.bestSolution());
        } else {
            calculation = new Calculation(term, numbers.current(), new Solution(term.print(), result));
        }
        return calculation;
    }

    /**
     * Computes the best solution for the current dice numbers and current target number based on the level. Stores the
     * difference in the score board.
     * <p>
     * Meant to be called for computer players.
     *
     * @return the best solution based on the level
     */
    public Solution solve() {
        return algorithm.compute(dice.numbers()[0], dice.numbers()[1], dice.numbers()[2], numbers.current())
                .bestSolution(settings.level());
    }

    /**
     * Stores the difference to the current number for the current player in the score board.
     */
    public void score(final Term term, final int difference) {
        score(term.print(), difference);
    }

    public void score(final String term, final int difference) {
        scoreboard.score(numbers.index(), players.current(), term, difference);
    }

    /**
     * Stores the solution for the current player in the score board.
     */
    public void score(final Solution solution) {
        scoreboard.score(numbers.index(), players.current(), solution.term(), abs(solution.result() - numbers.current()));
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Players players() {
        return players;
    }

    public Numbers numbers() {
        return numbers;
    }

    public Algorithm algorithm() {
        return algorithm;
    }

    public Dice dice() {
        return dice;
    }

    public Scoreboard scoreboard() {
        return scoreboard;
    }

    public boolean canceled() {
        return canceled;
    }
}
