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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.schlawiner.engine.term.Term;

public final class DiceValidator {

    private static final int[] MULTIPLIERS = new int[] { 1, 10, 100 };
    private static final Pattern NUMBERS = Pattern.compile("\\d+");

    static void validate(final Dice dice, final Term term) throws DiceException {
        int[] values = term.getValues();
        if (values.length < dice.numbers().length) {
            throw new DiceException("The term contains not all dice numbers.");
        } else if (values.length > dice.numbers().length) {
            throw new DiceException("The term contains more numbers than diced.");
        } else {
            boolean[] used = internalUsed(dice, values);
            for (boolean b : used) {
                if (!b) {
                    throw new DiceException("You have not used all the dice numbers.");
                }
            }
        }
    }

    /**
     * Count used numbers of a probably invalid expression (not yet parsed term).
     */
    static boolean[] used(final Dice dice, final String expression) {
        int[] termNumbers = extractTermNumbers(expression);
        return internalUsed(dice, termNumbers);
    }

    private static boolean[] internalUsed(final Dice dice, final int[] termNumbers) {
        boolean[] used = new boolean[dice.numbers().length];

        number: for (int termNumber : termNumbers) {
            for (int j = 0; j < dice.numbers().length; j++) {
                if (!used[j]) {
                    for (int multiplier : MULTIPLIERS) {
                        used[j] = termNumber == dice.numbers()[j] * multiplier;
                        if (used[j]) {
                            continue number;
                        }
                    }
                }
            }
        }
        return used;
    }

    private static int[] extractTermNumbers(final String expression) {
        if (expression == null || expression.trim().length() == 0) {
            return new int[0];
        }

        Matcher matcher = NUMBERS.matcher(expression);
        List<Integer> numbers = new ArrayList<>();
        while (matcher.find()) {
            String number = matcher.group();
            try {
                numbers.add(Integer.valueOf(number));
            } catch (NumberFormatException e) {
                throw new DiceException("Invalid number %s".formatted(number));
            }
        }
        int index = 0;
        int[] result = new int[numbers.size()];
        for (Integer number : numbers) {
            result[index] = number;
            index++;
        }
        return result;
    }

    private DiceValidator() {
    }
}
