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

import static io.schlawiner.engine.game.Level.MEDIUM;

public record Settings(int timeout, int penalty, int retries, int numbers, boolean autoDice, Level level) {

    public static Settings defaults() {
        return new Settings(60, 5, 3, 8, false, MEDIUM);
    }

    public Settings withTimeout(int timeout) {
        return new Settings(timeout, penalty, retries, numbers, autoDice, level);
    }

    public Settings withPenalty(int penalty) {
        return new Settings(timeout, penalty, retries, numbers, autoDice, level);
    }

    public Settings withRetries(int retries) {
        return new Settings(timeout, penalty, retries, numbers, autoDice, level);
    }

    public Settings withNumbers(int numbers) {
        return new Settings(timeout, penalty, retries, numbers, autoDice, level);
    }

    public Settings withLevel(Level level) {
        return new Settings(timeout, penalty, retries, numbers, autoDice, level);
    }

    public Settings withAutoDice(boolean autoDice) {
        return new Settings(timeout, penalty, retries, numbers, autoDice, level);
    }
}
