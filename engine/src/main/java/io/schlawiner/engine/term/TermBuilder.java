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

import java.util.Stack;

public class TermBuilder {

    private Term current;
    private int namingIndex;
    private final Stack<Term> terms;

    public TermBuilder() {
        this.namingIndex = 0;
        this.terms = new Stack<>();
    }

    public TermBuilder op(final Operator op) {
        final Term e = new Term(op);
        if (!terms.isEmpty()) {
            add(terms.peek(), e);
        }
        terms.push(e);
        return this;
    }

    public TermBuilder var(final String name) {
        return assign(name, Variable.DEFAULT_VALUE);
    }

    TermBuilder val(final int value) {
        return assign(Variable.DEFAULT_NAME + (namingIndex++), value);
    }

    private TermBuilder assign(final String name, final int value) {
        if (terms.isEmpty()) {
            throw new IllegalStateException(
                    "No expression available when calling TermBuilder.assign(" + name + ", " + value + ")");
        }

        final Variable v = new Variable(name, value);
        Term e = terms.peek();
        add(e, v);
        while (e != null && e.isComplete()) {
            current = terms.pop();
            e = terms.isEmpty() ? null : terms.peek();
        }
        return this;
    }

    private void add(final Term parent, final Node child) {
        if (parent.getLeft() == null) {
            parent.setLeft(child);
        } else if (parent.getRight() == null) {
            parent.setRight(child);
        }
    }

    public Term build() {
        if (!terms.isEmpty()) {
            throw new IllegalStateException("Unbalanced calls");
        }
        return current;
    }
}
