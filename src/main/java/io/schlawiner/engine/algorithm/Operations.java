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
package io.schlawiner.engine.algorithm;

final class Operations {

    static Solution add(final int a, final int b, final int c) {
        return new Solution("%d + %d + %d".formatted(a, b, c), a + b + c);
    }

    static Solution addDivide1(final int a, final int b, final int c) {
        if ((a + b) % c != 0) {
            return Solution.INVALID;
        }
        return new Solution("(%d + %d) / %d".formatted(a, b, c), (a + b) / c);
    }

    static Solution addDivide2(final int a, final int b, final int c) {
        if (a % (b + c) != 0) {
            return Solution.INVALID;
        }
        return new Solution("%d / (%d + %d)".formatted(a, b, c), a / (b + c));
    }

    static Solution addMultiply(final int a, final int b, final int c) {
        return new Solution("(%d + %d) * %d".formatted(a, b, c), (a + b) * c);
    }

    static Solution addSubtract(final int a, final int b, final int c) {
        return new Solution("%d + %d - %d".formatted(a, b, c), a + b - c);
    }

    static Solution divide(final int a, final int b, final int c) {
        if (a % b != 0 || (a / b) % c != 0) {
            return Solution.INVALID;
        }
        return new Solution("%d / %d / %d".formatted(a, b, c), a / b / c);
    }

    static Solution divideAdd(final int a, final int b, final int c) {
        if (a % b != 0) {
            return Solution.INVALID;
        }
        return new Solution("%d / %d + %d".formatted(a, b, c), a / b + c);
    }

    static Solution divideSubtract1(final int a, final int b, final int c) {
        if (a % b != 0) {
            return Solution.INVALID;
        }
        return new Solution("%d / %d - %d".formatted(a, b, c), a / b - c);
    }

    static Solution divideSubtract2(final int a, final int b, final int c) {
        if (b % c != 0) {
            return Solution.INVALID;
        }
        return new Solution("%d - %d / %d".formatted(a, b, c), a - b / c);
    }

    static Solution multiply(final int a, final int b, final int c) {
        return new Solution("%d * %d * %d".formatted(a, b, c), a * b * c);
    }

    static Solution multiplyAdd(final int a, final int b, final int c) {
        return new Solution("%d * %d + %d".formatted(a, b, c), a * b + c);
    }

    static Solution multiplyDivide(final int a, final int b, final int c) {
        if ((a * b) % c != 0) {
            return Solution.INVALID;
        }
        return new Solution("%d * %d / %d".formatted(a, b, c), a * b / c);
    }

    static Solution multiplySubtract1(final int a, final int b, final int c) {
        return new Solution("%d * %d - %d".formatted(a, b, c), a * b - c);
    }

    static Solution multiplySubtract2(final int a, final int b, final int c) {
        return new Solution("%d - %d * %d".formatted(a, b, c), a - b * c);
    }

    static Solution subtract(final int a, final int b, final int c) {
        return new Solution("%d - %d - %d".formatted(a, b, c), a - b - c);
    }

    static Solution subtractDivide1(final int a, final int b, final int c) {
        if ((a - b) % c != 0) {
            return Solution.INVALID;
        }
        return new Solution("(%d - %d) / %d".formatted(a, b, c), (a - b) / c);
    }

    static Solution subtractDivide2(final int a, final int b, final int c) {
        if (b - c == 0 || a % (b - c) != 0) {
            return Solution.INVALID;
        }
        return new Solution("%d / (%d - %d)".formatted(a, b, c), a / (b - c));
    }

    static Solution subtractMultiply(final int a, final int b, final int c) {
        return new Solution("(%d - %d) * %d".formatted(a, b, c), (a - b) * c);
    }

    private Operations() {
    }
}
