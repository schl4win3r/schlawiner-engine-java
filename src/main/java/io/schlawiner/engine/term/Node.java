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

/**
 * Node in a binary expression tree. Implementations are {@link Term} (operator node), {@link Value} (integer literal), and
 * {@link Variable} (named placeholder resolved via {@link Assignment}).
 */
public sealed interface Node permits Term, Variable, Value {

    /** Returns the parent node or {@code null} for the root. */
    Node parent();

    /** Sets the parent node. Used during tree construction only. */
    void parent(Node parent);

    /** Returns the left child or {@code null} for leaf nodes. */
    Node left();

    /** Sets the left child. Used during tree construction only. */
    void left(Node left);

    /** Returns the right child or {@code null} for leaf nodes. */
    Node right();

    /** Sets the right child. Used during tree construction only. */
    void right(Node right);
}
