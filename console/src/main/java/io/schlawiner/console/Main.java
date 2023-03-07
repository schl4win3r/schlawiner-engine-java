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
package io.schlawiner.console;

import java.util.ArrayList;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import io.schlawiner.engine.algorithm.OperationAlgorithm;
import io.schlawiner.engine.algorithm.Solution;
import io.schlawiner.engine.game.Calculation;
import io.schlawiner.engine.game.Dice;
import io.schlawiner.engine.game.Game;
import io.schlawiner.engine.game.Level;
import io.schlawiner.engine.game.Numbers;
import io.schlawiner.engine.game.Player;
import io.schlawiner.engine.game.Players;
import io.schlawiner.engine.game.Settings;
import io.schlawiner.engine.score.Score;

import static java.util.stream.Collectors.joining;

public class Main {

    public static void main(final String[] args) {
        final TextIO textIO = TextIoFactory.getTextIO();
        final TextTerminal<?> terminal = textIO.getTextTerminal();
        new Main(textIO, terminal).start();
    }

    private final TextIO textIO;
    private final TextTerminal<?> terminal;
    private final List<Player> players;
    private Settings settings;

    private Main(final TextIO textIO, final TextTerminal<?> terminal) {
        this.textIO = textIO;
        this.terminal = terminal;
        this.players = new ArrayList<>();
        this.settings = Settings.defaults().withAutoDice(true);

        terminal.println(Texts.BANNER);
    }

    private void start() {
        while (true) {
            terminal.print(Texts.MAIN);
            final int option = textIO.newIntInputReader().withMinVal(0).withMaxVal(3).read("Please choose");

            switch (option) {
                case 1 -> settings();
                case 2 -> players();
                case 3 -> play();
                case 0 -> System.exit(0);
            }
        }
    }

    private void settings() {
        // noinspection InfiniteLoopStatement
        while (true) {
            terminal.printf(Texts.SETTINGS, settings.numbers(), settings.retries(), settings.penalty(), settings.level());
            final int option = textIO.newIntInputReader().withMinVal(0).withMaxVal(4).read("Please choose");
            switch (option) {
                case 1 -> settings = settings.withNumbers(textIO.newIntInputReader().withMinVal(2).withMaxVal(20)
                        .withDefaultValue(8).read("Number of numbers (2..20)"));
                case 2 -> settings = settings.withRetries(textIO.newIntInputReader().withMinVal(0).withMaxVal(5)
                        .withDefaultValue(3).read("Number of retries (0..5)"));
                case 3 -> settings = settings.withPenalty(textIO.newIntInputReader().withMinVal(1).withMaxVal(10)
                        .withDefaultValue(5).read("Penalty after timeout (1..10)"));
                case 4 -> settings = settings
                        .withLevel(textIO.newEnumInputReader(Level.class).withDefaultValue(Level.MEDIUM).read("Level"));
                case 0 -> start();
            }
        }
    }

    private void players() {
        // noinspection InfiniteLoopStatement
        while (true) {
            terminal.print(Texts.PLAYERS);
            final int option = textIO.newIntInputReader().withMinVal(0).withMaxVal(3).read("Please choose");

            switch (option) {
                case 1 -> {
                    final String name = textIO.newStringInputReader().read("Name");
                    final boolean human = textIO.newBooleanInputReader().withDefaultValue(true).read("Human");
                    players.add(new Player(name, human, settings.retries()));
                }
                case 2 -> {
                    terminal.println();
                    if (players.isEmpty()) {
                        terminal.println("No players");
                    }
                    for (final Player player : players) {
                        terminal.printf("%s%n", player);
                    }
                }
                case 3 -> {
                    int index = 1;
                    terminal.println();
                    for (final Player player : players) {
                        terminal.printf("[%d] %s%n", index, player);
                        index++;
                    }
                    terminal.printf("[0] Back%n%n");
                    index = textIO.newIntInputReader().withMaxVal(0).withMaxVal(players.size()).read("Please choose");
                    if (index != 0) {
                        index--;
                        players.remove(index);
                    }
                }
                case 0 -> start();
            }
        }
    }

    private void play() {
        final Game game = new Game("console-game", new Players(players), new Numbers(settings.numbers()),
                new OperationAlgorithm(), settings);

        terminal.print(Texts.PLAY);
        while (game.hasNext()) {
            game.next();
            game.dice(Dice.random());

            final Players players = game.getPlayers();
            final Player currentPlayer = players.current();
            final int currentNumber = game.getNumbers().current();
            if (players.isFirst()) {
                printScoreboard(game);
            }
            if (currentPlayer.human()) {
                String term = null;
                boolean validTerm = false;
                while (!validTerm && !game.isCanceled()) {
                    final String prompt = String.format("%s try to reach %d using %s", currentPlayer.name(), currentNumber,
                            game.getDice());
                    try {
                        term = textIO.newStringInputReader().read(prompt);
                        if ("retry".equalsIgnoreCase(term)) {
                            if (game.retry()) {
                                terminal.printf("You have %d retries left.%n", currentPlayer.retries());
                            } else {
                                terminal.println("Sorry you have no retries left");
                            }
                        } else if ("skip".equalsIgnoreCase(term)) {
                            game.skip();
                            validTerm = true;
                        } else if ("cancel".equalsIgnoreCase(term)) {
                            game.cancel();
                        } else {
                            final Calculation calculation = game.calculate(term);
                            if (calculation.isBest()) {
                                terminal.printf("Well done, your solution is the best.%n");
                            } else {
                                terminal.printf("Your difference is %d. The best solution is %s (difference %d)%n",
                                        calculation.getDifference(), calculation.getBestSolution(),
                                        calculation.getBestDifference());
                            }
                            game.score(term, calculation.getDifference());
                            validTerm = true;
                        }
                    } catch (final ArithmeticException e) {
                        terminal.printf("No valid term '%s': %s%n", term, e.getMessage());
                    }
                }
            } else {
                final Solution solution = game.solve();
                game.score(solution);
                terminal.printf("%s diced %s. Solution: %s%n", currentPlayer.name(), game.getDice(), solution);
            }
        }

        if (!game.isCanceled()) {
            printScoreboard(game);
            terminal.printf("Game over. ");
            final List<Player> winners = game.getScoreboard().getWinners();
            if (winners.size() == 1) {
                terminal.printf("The winner is %s!%n", winners.get(0).name());
            } else {
                terminal.printf("The winners are %s!%n", winners.stream().map(Player::name).collect(joining(", ")));
            }
        }
        start();
    }

    private void printScoreboard(final Game game) {
        // header
        terminal.println();
        terminal.printf("    ");
        for (final Player player : game.getPlayers()) {
            terminal.printf("| %-20s ", player.name());
        }
        terminal.println();
        terminal.printf("====");
        for (final Player ignored : game.getPlayers()) {
            terminal.printf("+=================+====");
        }
        terminal.println();

        // body
        int numberIndex = 0;
        for (final int number : game.getNumbers()) {
            terminal.printf("%3d ", number);
            for (final Player player : game.getPlayers()) {
                final Score score = game.getScoreboard().getScore(player, numberIndex);
                final String difference = score.difference() == -1 ? "  " : String.format("%2d", score.difference());
                terminal.printf("| %15s | %s ", (score.term() == null ? "" : score.term()), difference);
            }
            terminal.println();
            numberIndex++;
        }

        // footer
        terminal.printf("====");
        for (final Player ignored : game.getPlayers()) {
            terminal.printf("+=================+====");
        }
        terminal.println();
        terminal.printf("    ");
        for (final Player player : game.getPlayers()) {
            terminal.printf("|                 | %2d ", game.getScoreboard().getSummedScore(player));
        }
        terminal.println();
        terminal.println();
    }
}
