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
package io.schlawiner.engine.term;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import static io.schlawiner.engine.term.TermBuilder.Order.RIGHT_LEFT;

public final class TermParser {

    private static final Set<Character> OPS = new HashSet<>();
    static {
        OPS.add('(');
        OPS.add(')');
        OPS.add('+');
        OPS.add('-');
        OPS.add('*');
        OPS.add('/');
    }

    public static Term parse(final String expression) {
        if (expression == null || expression.trim().length() == 0) {
            throw new ArithmeticException("Empty term");
        }

        final String[] tokens = split(expression);
        final String[] rpn = infixToRPN(tokens);
        final TermBuilder termBuilder = new TermBuilder(RIGHT_LEFT);
        for (int i = rpn.length - 1; i >= 0; i--) {
            final String token = rpn[i];
            if (isOperator(token)) {
                termBuilder.op(Operator.toOperator(token));
            } else {
                // should be a number
                try {
                    termBuilder.val(Integer.parseInt(token));
                } catch (final NumberFormatException e) {
                    throw new ArithmeticException(token + " is not a number");
                }
            }
        }

        Term term = termBuilder.build();
        if (!term.isComplete()) {
            throw new ArithmeticException(String.format("Invalid term: '%s'"));
        }
        return term;
    }

    private static String[] split(final String expression) {
        StringBuilder number = new StringBuilder();
        final List<String> tokens = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            final char c = expression.charAt(i);
            if (OPS.contains(c) || Character.isWhitespace(c)) {
                if (number.length() > 0) {
                    tokens.add(number.toString());
                    number = new StringBuilder();
                }
                if (OPS.contains(c)) {
                    tokens.add(String.valueOf(c));
                }
            } else {
                number.append(c);
            }
        }
        if (number.length() > 0) {
            tokens.add(number.toString());
        }
        return tokens.toArray(new String[0]);
    }

    private static String[] infixToRPN(final String[] tokens) {
        final ArrayList<String> out = new ArrayList<>();
        final Stack<String> stack = new Stack<>();
        for (final String token : tokens) {
            if (isOperator(token)) {
                while (!stack.empty() && isOperator(stack.peek())) {
                    if (cmpPrecedence(token, stack.peek()) <= 0) {
                        out.add(stack.pop());
                        continue;
                    }
                    break;
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.empty() && !stack.peek().equals("(")) {
                    out.add(stack.pop());
                }
                stack.pop();
            } else {
                // should be a number
                try {
                    Integer.valueOf(token);
                    out.add(token);
                } catch (final NumberFormatException e) {
                    throw new ArithmeticException(token + " is not a number");
                }
            }
        }
        while (!stack.empty()) {
            out.add(stack.pop());
        }
        return out.toArray(new String[0]);
    }

    private static boolean isOperator(final String token) {
        final Operator op = Operator.toOperator(token);
        return op != null;
    }

    private static int cmpPrecedence(final String token1, final String token2) {
        if (!isOperator(token1) || !isOperator(token2)) {
            throw new ArithmeticException("Invalid tokens: " + token1 + " " + token2);
        }
        // noinspection ConstantConditions
        return Operator.toOperator(token1).precedence() - Operator.toOperator(token2).precedence();
    }

    private TermParser() {
    }
}
