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
import java.util.List;
import java.util.Objects;

import io.schlawiner.engine.game.Numbers;
import io.schlawiner.engine.game.Player;

/**
 * Stores the players as rows and the numbers as columns.
 *
 * <table>
 * <colgroup> <col style="width: 20%"> <col style="width: 11.4%"> <col style="width: 11.4%"> <col style="width: 11.4">
 * <col style="width: 11.4"> <col style="width: 11.4"> <col style="width: 11.4"> <col style="width: 11.4">
 * <col style="width: 11.4"> <col style="width: 11.4"> <col style="width: 11.4"> <col style="width: 11.4">
 * <col style="width: 11.4"> <col style="width: 11.4"> <col style="width: 11.4"> <col style="width: 11.4">
 * <col style="width: 11.4"> <col style="width: 11.4"> <col style="width: 11.4"> <col style="width: 11.4">
 * <col style="width: 11.4"> <col style="width: 11.4"> </colgroup> <thead>
 * <tr>
 * <th>&nbsp;</th>
 * <th>12</th>
 * <th>34</th>
 * <th>4</th>
 * <th>52</th>
 * <th>57</th>
 * <th>80</th>
 * <th>&nbsp;</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>Player 1</td>
 * <td>1</td>
 * <td>0</td>
 * <td>2</td>
 * <td>1</td>
 * <td>0</td>
 * <td>4</td>
 * </tr>
 * <tr>
 * <td>Player 2</td>
 * <td>0</td>
 * <td>0</td>
 * <td>1</td>
 * <td>2</td>
 * <td>0</td>
 * <td>3</td>
 * </tr>
 * </tbody>
 * </table>
 */
public class PlayerScore {

    private final Player player;
    private final List<Score> scores;

    PlayerScore(final Player player, final Numbers numbers) {
        this.player = player;
        this.scores = new ArrayList<>();
        for (int index = 0; index < numbers.size(); index++) {
            scores.add(Score.EMPTY);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final PlayerScore that)) {
            return false;
        }

        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return player != null ? player.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PlayerScore{" + player + ": " + scores + '}';
    }

    void setScore(final int numberIndex, final Score score) {
        if (numberIndex > -1 && numberIndex < scores.size()) {
            scores.set(numberIndex, score);
        }
    }

    Score getScore(final int numberIndex) {
        if (numberIndex > -1 && numberIndex < scores.size()) {
            return scores.get(numberIndex);
        }
        return null;
    }

    Player getPlayer() {
        return player;
    }
}
