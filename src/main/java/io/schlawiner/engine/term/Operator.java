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

/** Arithmetic operators supported in term expressions, with their precedence for correct evaluation order. */
public enum Operator {

    PLUS(0) {
        @Override
        public String toString() {
            return "+";
        }
    },

    MINUS(0) {
        @Override
        public String toString() {
            return "-";
        }
    },

    TIMES(5) {
        @Override
        public String toString() {
            return "*";
        }
    },

    DIVIDED(5) {
        @Override
        public String toString() {
            return "/";
        }
    };

    private final int precedence;

    Operator(final int precedence) {
        this.precedence = precedence;
    }

    /** Returns {@code true} if the token is one of {@code +}, {@code -}, {@code *}, or {@code /}. */
    static boolean isOperator(final String token) {
        return toOperator(token) != null;
    }

    /** Converts a token string to an {@link Operator}, or returns {@code null} if the token is not a valid operator. */
    public static Operator toOperator(final String token) {
        if (token != null) {
            return switch (token) {
                case "+" -> PLUS;
                case "-" -> MINUS;
                case "*" -> TIMES;
                case "/" -> DIVIDED;
                default -> null;
            };
        }
        return null;
    }

    /**
     * Returns the precedence level. Higher values bind tighter ({@code *} and {@code /} have higher precedence than {@code +}
     * and {@code -}).
     */
    public int precedence() {
        return this.precedence;
    }
}
