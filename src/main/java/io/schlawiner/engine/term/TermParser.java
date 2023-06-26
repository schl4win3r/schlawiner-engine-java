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

final class TermParser {

    static Term parse(final String expression) {
        if (expression == null || expression.trim().length() == 0) {
            throw new TermException("Empty term");
        }

        String[] rpn = InfixToRPN.infixToRPN(expression);
        if (rpn.length == 0) {
            throw new TermException("Invalid term: '%s'".formatted(expression));
        }

        TermBuilder termBuilder = new TermBuilder(expression);
        for (int i = rpn.length - 1; i >= 0; i--) {
            String token = rpn[i];
            if (Operator.isOperator(token)) {
                termBuilder.op(Operator.toOperator(token));
            } else {
                try {
                    termBuilder.val(Integer.parseInt(token));
                } catch (NumberFormatException e) {
                    termBuilder.var(token);
                }
            }
        }

        Term term = termBuilder.build();
        if (!term.complete()) {
            throw new TermException("Invalid term: '%s'".formatted(expression));
        }
        return term;
    }

    private TermParser() {
    }
}
