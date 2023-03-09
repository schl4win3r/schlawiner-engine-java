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

public class OperationAlgorithm extends AbstractAlgorithm {

    public OperationAlgorithm() {
        super("Algorithm based on static operations");
    }

    OperationAlgorithm(final int allowedDifference) {
        super("Algorithm based on static operations", allowedDifference);
    }

    @Override
    protected void computePermutation(final int a, final int b, final int c, final int target, final Solutions solutions) {
        // a + b + c
        solutions.add(Operations.add(a, b, c));

        // a - b - c
        solutions.add(Operations.subtract(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.subtract(b, a, c));
            solutions.add(Operations.subtract(c, a, b));
        }

        // a * b * c
        solutions.add(Operations.multiply(a, b, c));

        // a / b / c
        solutions.add(Operations.divide(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.divide(b, a, c));
            solutions.add(Operations.divide(c, a, b));
        }

        // a + b - c
        solutions.add(Operations.addSubtract(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.addSubtract(a, c, b));
            solutions.add(Operations.addSubtract(b, c, a));
        }

        // a * b / c
        solutions.add(Operations.multiplyDivide(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.multiplyDivide(a, c, b));
            solutions.add(Operations.multiplyDivide(b, c, a));
        }

        // a * b + c
        solutions.add(Operations.multiplyAdd(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.multiplyAdd(a, c, b));
            solutions.add(Operations.multiplyAdd(b, c, a));
        }

        // (a + b) * c
        solutions.add(Operations.addMultiply(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.addMultiply(a, c, b));
            solutions.add(Operations.addMultiply(b, c, a));
        }

        // a * b - c
        solutions.add(Operations.multiplySubtract1(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.multiplySubtract1(a, c, b));
            solutions.add(Operations.multiplySubtract1(b, c, a));
        }

        // a - b * c
        solutions.add(Operations.multiplySubtract2(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.multiplySubtract2(b, a, c));
            solutions.add(Operations.multiplySubtract2(c, a, b));
        }

        // (a - b) * c
        solutions.add(Operations.subtractMultiply(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.subtractMultiply(b, a, c));
            solutions.add(Operations.subtractMultiply(a, c, b));
            solutions.add(Operations.subtractMultiply(c, a, b));
            solutions.add(Operations.subtractMultiply(b, c, a));
            solutions.add(Operations.subtractMultiply(c, b, a));
        }

        // a / b + c
        solutions.add(Operations.divideAdd(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.divideAdd(b, a, c));
            solutions.add(Operations.divideAdd(a, c, b));
            solutions.add(Operations.divideAdd(c, a, b));
            solutions.add(Operations.divideAdd(b, c, a));
            solutions.add(Operations.divideAdd(c, b, a));
        }

        // (a + b) / c
        solutions.add(Operations.addDivide1(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.addDivide1(a, c, b));
            solutions.add(Operations.addDivide1(b, c, a));
        }

        // a / (b + c)
        solutions.add(Operations.addDivide2(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.addDivide2(b, a, c));
            solutions.add(Operations.addDivide2(c, a, b));
        }

        // a / b - c
        solutions.add(Operations.divideSubtract1(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.divideSubtract1(a, c, b));
            solutions.add(Operations.divideSubtract1(b, a, a));
            solutions.add(Operations.divideSubtract1(b, c, a));
            solutions.add(Operations.divideSubtract1(c, a, b));
            solutions.add(Operations.divideSubtract1(c, b, a));
        }

        // a - b / c
        solutions.add(Operations.divideSubtract2(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.divideSubtract2(a, c, b));
            solutions.add(Operations.divideSubtract2(b, a, a));
            solutions.add(Operations.divideSubtract2(b, c, a));
            solutions.add(Operations.divideSubtract2(c, a, b));
            solutions.add(Operations.divideSubtract2(c, b, a));
        }

        // (a - b) / c
        solutions.add(Operations.subtractDivide1(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.subtractDivide1(a, c, b));
            solutions.add(Operations.subtractDivide1(b, a, a));
            solutions.add(Operations.subtractDivide1(b, c, a));
            solutions.add(Operations.subtractDivide1(c, a, b));
            solutions.add(Operations.subtractDivide1(c, b, a));
        }

        // a / (b - c)
        solutions.add(Operations.subtractDivide2(a, b, c));
        if (!sameDiceNumbers(a, b, c)) {
            solutions.add(Operations.subtractDivide2(a, c, b));
            solutions.add(Operations.subtractDivide2(b, a, a));
            solutions.add(Operations.subtractDivide2(b, c, a));
            solutions.add(Operations.subtractDivide2(c, a, b));
            solutions.add(Operations.subtractDivide2(c, b, a));
        }
    }
}
