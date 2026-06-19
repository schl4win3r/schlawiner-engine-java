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

/**
 * Strategy for computing solutions given three dice values and a target number. Each dice value can be multiplied by 1, 10, or
 * 100. All operator and permutation combinations are tried.
 *
 * @see OperationAlgorithm
 * @see TermAlgorithm
 */
public interface Algorithm {

    /** Computes all valid solutions for the three dice values {@code a}, {@code b}, {@code c} and the given target number. */
    Solutions compute(int a, int b, int c, int target);

    /** Returns a human-readable name for this algorithm. */
    String name();
}
