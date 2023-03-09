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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.schlawiner.engine.game.Numbers;
import io.schlawiner.engine.game.Player;
import io.schlawiner.engine.game.Players;

public class Scoreboard {

    private final Players players;
    private final NumberScore[] numberScores;
    private final PlayerScore[] playerScores;
    private final Map<Player, Integer> playerSums;

    public Scoreboard(final Players players, final Numbers numbers) {
        this.players = players;
        this.numberScores = new NumberScore[numbers.size()];
        this.playerScores = new PlayerScore[players.size()];
        this.playerSums = new HashMap<>();

        int numberIndex = 0;
        for (Integer number : numbers) {
            numberScores[numberIndex] = new NumberScore(numberIndex, number, players);
            numberIndex++;
        }
        int playerIndex = 0;
        for (Player player : players) {
            playerScores[playerIndex++] = new PlayerScore(player, numbers);
            playerSums.put(player, 0);
        }
    }

    public void setScore(final int numberIndex, final Player player, final String term, final int difference) {
        Score score = new Score(term, difference);
        if (numberIndex > -1 && numberIndex < numberScores.length) {
            numberScores[numberIndex].setScore(player, score);
        }
        int playerIndex = players.indexOf(player);
        if (playerIndex > -1 && playerIndex < players.size()) {
            playerScores[playerIndex].setScore(numberIndex, score);
        }
        if (playerSums.containsKey(player)) {
            int newScore = playerSums.get(player) + score.difference();
            playerSums.put(player, newScore);
        }
    }

    public Score getScore(final Player player, final int numberIndex) {
        return numberScores[numberIndex].getScore(player);
    }

    public List<NumberScore> getNumberScores() {
        return Arrays.asList(numberScores);
    }

    public List<PlayerScore> getPlayerScores() {
        ArrayList<PlayerScore> list = new ArrayList<>();
        Collections.addAll(list, playerScores);
        return list;
    }

    public int getSummedScore(final Player player) {
        if (playerSums.containsKey(player)) {
            return playerSums.get(player);
        }
        return 0;
    }

    public List<Player> getWinners() {
        int min = Integer.MAX_VALUE;
        for (Integer sum : playerSums.values()) {
            min = Math.min(min, sum);

        }
        List<Player> winners = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : playerSums.entrySet()) {
            if (entry.getValue() == min) {
                winners.add(entry.getKey());
            }
        }
        return winners;
    }
}
