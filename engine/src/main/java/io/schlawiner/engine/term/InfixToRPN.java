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

final class InfixToRPN {

    private static final Set<Character> OPS = new HashSet<>();
    static {
        OPS.add('(');
        OPS.add(')');
        OPS.add('+');
        OPS.add('-');
        OPS.add('*');
        OPS.add('/');
    }

    static String[] infixToRPN(final String expression) {
        if (expression == null || expression.trim().length() == 0) {
            return new String[0];
        }

        String[] tokens = split(expression);
        return convert(tokens);
    }

    private static String[] split(final String expression) {
        StringBuilder number = new StringBuilder();
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
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

    private static String[] convert(final String[] tokens) {
        ArrayList<String> out = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for (String token : tokens) {
            if (Operator.isOperator(token)) {
                while (!stack.empty() && Operator.isOperator(stack.peek())) {
                    if (precedenceDifference(token, stack.peek()) <= 0) {
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
                out.add(token);
            }
        }
        while (!stack.empty()) {
            out.add(stack.pop());
        }
        return out.toArray(new String[0]);
    }

    private static int precedenceDifference(final String token1, final String token2) {
        if (!Operator.isOperator(token1) || !Operator.isOperator(token2)) {
            throw new TermException("Invalid tokens: " + token1 + ",. " + token2);
        }
        return Operator.toOperator(token1).precedence() - Operator.toOperator(token2).precedence();
    }

    private InfixToRPN() {
    }
}
