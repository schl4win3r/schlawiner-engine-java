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

public class Player {

    public static Player human(final String name, final int retries) {
        return new Player(name, true, retries);
    }

    static Player computer(final String name) {
        return new Player(name, false, 0);
    }

    private final String name;
    private final boolean human;
    private int retries;

    public Player(String name, boolean human, int retries) {
        this.name = name;
        this.human = human;
        this.retries = retries;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Player) obj;
        return Objects.equals(this.name, that.name) && this.human == that.human;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, human);
    }

    @Override
    public String toString() {
        return "Player(" + name + (human ? ": human)" : ": computer)");
    }

    void retry() {
        this.retries--;
    }

    public String name() {
        return name;
    }

    public boolean human() {
        return human;
    }

    public int retries() {
        return retries;
    }

    public void retries(int retries) {
        this.retries = retries;
    }
}
