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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.schlawiner.engine.util.Iterators.cycle;

/** The players in a game */
public class Players implements Iterable<Player> {

    private final List<Player> players;
    private final transient Iterator<Player> iterator;
    private Player current;

    public Players(final Iterable<Player> players) {
        this.players = new ArrayList<>();
        for (Player player : players) {
            this.players.add(player);
        }
        this.iterator = cycle(players);
    }

    public Player next() {
        if (iterator.hasNext()) {
            current = iterator.next();
        }
        return current;
    }

    public boolean isFirst() {
        return current != null && players.indexOf(current) == 0;
    }

    public boolean isLast() {
        return current != null && players.indexOf(current) == players.size() - 1;
    }

    public Player current() {
        return current;
    }

    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }

    public int indexOf(final Player p) {
        return players.indexOf(p);
    }

    public int size() {
        return players.size();
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }
}
