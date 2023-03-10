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

final class Value implements Node {

    private final int value;
    private Node parent;

    Value(final int value) {
        this.value = value;
    }

    int value() {
        return value;
    }

    @Override
    public Node parent() {
        return parent;
    }

    @Override
    public void parent(final Node parent) {
        this.parent = parent;
    }

    @Override
    public Node left() {
        return null;
    }

    @Override
    public void left(final Node left) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node right() {
        return null;
    }

    @Override
    public void right(final Node right) {
        throw new UnsupportedOperationException();
    }
}
