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

public interface Texts {

    String BANNER = "\n" + "                                                           ____\n"
            + "                                                          /\\' .\\    _____\n"
            + "                                                         /: \\___\\  / .  /\\\n"
            + "                                                         \\' / . / /____/..\\\n"
            + "                                                          \\/___/  \\'  '\\  /\n"
            + "  _________      .__    .__                .__                     \\'__'\\/\n"
            + " /   _____/ ____ |  |__ |  | _____ __  _  _|__| ____   ___________\n"
            + " \\_____  \\_/ ___\\|  |  \\|  | \\__  \\\\ \\/ \\/ /  |/    \\_/ __ \\_  __ \\\n"
            + " /        \\  \\___|   Y  \\  |__/ __ \\\\     /|  |   |  \\  ___/|  | \\/\n"
            + "/_______  /\\___  >___|  /____(____  /\\/\\_/ |__|___|  /\\___  >__|\n"
            + "        \\/     \\/     \\/          \\/               \\/     \\/\n";

    String MAIN = "\n" + "# Main Menu\n\n" + "[1] Settings\n" + "[2] Players\n" + "[3] Play\n" + "[0] Exit\n\n";

    String SETTINGS = "\n" + "## Settings\n\n" + "[1] Numbers:\t%3d\n" + "[2] Retries:\t%3d\n" + "[3] Penalty:\t%3d\n"
            + "[4] Level:\t%s\n" + "[0] Back\n\n";

    String PLAYERS = "\n" + "## Players\n\n" + "[1] Add Player\n" + "[2] Show Players\n" + "[3] Remove Player\n"
            + "[0] Back\n\n";

    String PLAY = "\n" + "## Play\n\n" + "To dice again, enter 'retry'.\n" + "To skip the current number, enter 'skip'.\n"
            + "To cancel the game, enter 'cancel'.\n";
}
