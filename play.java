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
///usr/bin/env jbang "$0" "$@" ; exit $?

//JAVA 21
//COMPILE_OPTIONS -source 21

//DEPS org.beryx:text-io:3.4.1
//DEPS io.schlawiner:schlawiner-engine-java:0.0.1

import java.util.ArrayList;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import io.schlawiner.engine.algorithm.OperationAlgorithm;
import io.schlawiner.engine.algorithm.Solution;
import io.schlawiner.engine.game.Calculation;
import io.schlawiner.engine.game.Dice;
import io.schlawiner.engine.game.DiceException;
import io.schlawiner.engine.game.Game;
import io.schlawiner.engine.game.Level;
import io.schlawiner.engine.game.Numbers;
import io.schlawiner.engine.game.Player;
import io.schlawiner.engine.game.Players;
import io.schlawiner.engine.game.Settings;
import io.schlawiner.engine.score.Score;
import io.schlawiner.engine.term.TermException;

import static java.lang.System.*;
import static java.util.stream.Collectors.joining;

public class play {

    private static final String BANNER = """
                                                           ____
                                                          /\\' .\\    _____
                                                         /: \\___\\  / .  /\\
                                                         \\' / . / /____/..\\
                                                          \\/___/  \\'  '\\  /
  _________      .__    .__                .__                     \\'__'\\/
 /   _____/ ____ |  |__ |  | _____ __  _  _|__| ____   ___________
 \\_____  \\_/ ___\\|  |  \\|  | \\__  \\\\ \\/ \\/ /  |/    \\_/ __ \\_  __ \\
 /        \\  \\___|   Y  \\  |__/ __ \\\\     /|  |   |  \\  ___/|  | \\/
/_______  /\\___  >___|  /____(____  /\\/\\_/ |__|___|  /\\___  >__|
        \\/     \\/     \\/          \\/               \\/     \\/
        
""";

    private static final String MAIN = """

# Main Menu

[1] Settings
[2] Players
[3] Play
[0] Exit

""";

    private static final String SETTINGS = """

## Settings

[1] Numbers:\t%3d
[2] Retries:\t%3d
[3] Penalty:\t%3d
[4] Level:\t%s
[0] Back

""";

    private static final String PLAYERS = """

## Players

[1] Add Player
[2] Show Players
[3] Remove Player
[0] Back

""";

    private static final String PLAY = """
## Play

To dice again, enter 'retry'.
To skip the current number, enter 'skip'
To cancel the game, enter 'cancel'
""";

    public static void main(String... args) {
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> terminal = textIO.getTextTerminal();
        new play(textIO, terminal).start();
    }

    final TextIO textIO;
    final TextTerminal<?> terminal;
    final List<Player> players;
    Settings settings;

    play(final TextIO textIO, final TextTerminal<?> terminal) {
        this.textIO = textIO;
        this.terminal = terminal;
        this.settings = Settings.defaults().withAutoDice(true);
        this.players = List.of(Player.human("Player 1", settings.retries()), Player.computer("Computer"));

        terminal.println(BANNER);
    }

    void start() {
        while (true) {
            terminal.print(MAIN);
            int option = textIO.newIntInputReader().withMinVal(0).withMaxVal(3).read("Please choose");

            switch (option) {
                case 1 -> settings();
                case 2 -> players();
                case 3 -> play();
                case 0 -> System.exit(0);
            }
        }
    }

    void settings() {
        while (true) {
            terminal.printf(SETTINGS, settings.numbers(), settings.retries(), settings.penalty(), settings.level());
            int option = textIO.newIntInputReader().withMinVal(0).withMaxVal(4).read("Please choose");
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

    void players() {
        while (true) {
            terminal.print(PLAYERS);
            int option = textIO.newIntInputReader().withMinVal(0).withMaxVal(3).read("Please choose");

            switch (option) {
                case 1 -> {
                    String name = textIO.newStringInputReader().read("Name");
                    boolean human = textIO.newBooleanInputReader().withDefaultValue(true).read("Human");
                    players.add(new Player(name, human, settings.retries()));
                }
                case 2 -> {
                    terminal.println();
                    if (players.isEmpty()) {
                        terminal.println("No players!");
                    }
                    for (Player player : players) {
                        terminal.printf("%s%n", player);
                    }
                }
                case 3 -> {
                    int index = 1;
                    terminal.println();
                    for (Player player : players) {
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

    void play() {
        if (players.isEmpty()) {
            terminal.println("No players!");
        } else {
            Game game = new Game("console-game", new Players(players), new Numbers(settings.numbers()), new OperationAlgorithm(),
                    settings);

            terminal.print(PLAY);
            while (game.hasNext()) {
                game.next();
                game.dice(Dice.random());

                Players players = game.players();
                Player currentPlayer = players.current();
                int currentNumber = game.numbers().current();
                if (players.first()) {
                    printScoreboard(game);
                }
                if (currentPlayer.human()) {
                    String expression;
                    boolean validTerm = false;
                    while (!validTerm && !game.canceled()) {
                        String prompt = "%s try to reach %d using %s".formatted(currentPlayer.name(), currentNumber, game.dice());
                        try {
                            expression = textIO.newStringInputReader().read(prompt);
                            if ("retry".equalsIgnoreCase(expression)) {
                                if (game.retry()) {
                                    terminal.printf("You have %d retries left.%n", currentPlayer.retries());
                                } else {
                                    terminal.println("Sorry you have no retries left");
                                }
                            } else if ("skip".equalsIgnoreCase(expression)) {
                                game.skip();
                                validTerm = true;
                            } else if ("cancel".equalsIgnoreCase(expression)) {
                                game.cancel();
                            } else {
                                Calculation calculation = game.calculate(expression);
                                if (calculation.best()) {
                                    terminal.printf("Well done, your solution is the best.%n");
                                } else {
                                    terminal.printf("Your difference is %d. The best solution is %s (difference %d)%n",
                                            calculation.difference(), calculation.bestSolution(), calculation.bestDifference());
                                }
                                game.score(calculation.term(), calculation.difference());
                                validTerm = true;
                            }
                        } catch (TermException | DiceException e) {
                            terminal.printf("%s%n", e.getMessage());
                        }
                    }
                } else {
                    Solution solution = game.solve();
                    game.score(solution);
                    terminal.printf("%s diced %s. Solution: %s%n", currentPlayer.name(), game.dice(), solution);
                }
            }

            if (!game.canceled()) {
                printScoreboard(game);
                terminal.printf("Game over. ");
                List<Player> winners = game.scoreboard().winners();
                if (winners.size() == 1) {
                    terminal.printf("The winner is %s!%n", winners.get(0).name());
                } else {
                    terminal.printf("The winners are %s!%n", winners.stream().map(Player::name).collect(joining(", ")));
                }
            }
        }
        start();
    }

    void printScoreboard(final Game game) {
        // header
        terminal.println();
        terminal.printf("    ");
        for (Player player : game.players()) {
            terminal.printf("│ %-20s ", player.name());
        }
        terminal.println();
        terminal.printf("━━━━");
        for (Player ignored : game.players()) {
            terminal.printf("┿━━━━━━━━━━━━━━━━━┯━━━━");
        }
        terminal.println();

        // body
        int numberIndex = 0;
        for (int number : game.numbers()) {
            terminal.printf("%3d ", number);
            for (Player player : game.players()) {
                Score score = game.scoreboard().score(player, numberIndex);
                String difference = score.difference() == -1 ? "  " : "%2d".formatted(score.difference());
                terminal.printf("│ %15s │ %s ", score.term(), difference);
            }
            terminal.println();
            numberIndex++;
        }

        // footer
        terminal.printf("━━━━");
        for (Player ignored : game.players()) {
            terminal.printf("┿━━━━━━━━━━━━━━━━━┿━━━━");
        }
        terminal.println();
        terminal.printf("    ");
        for (Player player : game.players()) {
            terminal.printf("│                 │ %2d ", game.scoreboard().summedScore(player));
        }
        terminal.println();
        terminal.println();
    }
}
