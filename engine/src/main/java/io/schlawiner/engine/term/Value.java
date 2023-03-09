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

    final int value;
    private Node parent;

    Value(final int value) {
        this.value = value;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(final Node parent) {
        this.parent = parent;
    }

    @Override
    public Node getLeft() {
        return null;
    }

    @Override
    public void setLeft(final Node left) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node getRight() {
        return null;
    }

    @Override
    public void setRight(final Node right) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
