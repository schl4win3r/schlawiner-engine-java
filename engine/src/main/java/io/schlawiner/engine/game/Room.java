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

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.unmodifiableCollection;

public class Room {

    private final String name;
    private final int limit;
    private final Player owner;
    private final Set<Player> players;

    public Room(final String name, final int limit, final Player owner) {
        this.name = name;
        this.limit = limit;
        this.owner = owner;
        this.players = new LinkedHashSet<>();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Room room)) {
            return false;
        }
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Room(" + name + " by " + owner + ": " + players.size() + " / " + limit + ")";
    }

    public String name() {
        return name;
    }

    public int limit() {
        return limit;
    }

    public Player owner() {
        return owner;
    }

    public Iterable<Player> players() {
        return unmodifiableCollection(players);
    }

    public boolean empty() {
        return players.isEmpty();
    }

    public boolean join(final Player player) {
        boolean result = false;
        if (player != null && !player.equals(owner)) {
            if (players.size() < limit) {
                result = players.add(player);
            }
        }
        return result;
    }

    public boolean leave(final Player player) {
        boolean result = false;
        if (player != null && !player.equals(owner)) {
            result = players.remove(player);
        }
        return result;
    }
}
