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

public record Player(String name, boolean human, int retries) {

    public static Player human(String name, int retries) {
        return new Player(name, true, retries);
    }

    static Player computer(String name) {
        return new Player(name, false, 0);
    }

    @Override
    public String toString() {
        return "Player(" + name + (human ? ": human)" : ": computer)");
    }

    Player retry() {
        return new Player(name, human, retries - 1);
    }
}
