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

class TermBuilder {

    private final String expression;
    private final Stack<Term> terms;
    private boolean variablesAdded;
    private Term current;

    TermBuilder(final String expression) {
        this.expression = expression;
        this.terms = new Stack<>();
        this.variablesAdded = false;
    }

    TermBuilder op(final Operator op) {
        final Term e = new Term(op);
        if (!terms.isEmpty()) {
            add(terms.peek(), e);
        }
        terms.push(e);
        return this;
    }

    TermBuilder var(final String name) {
        variablesAdded = true;
        return assign(new Variable(name));
    }

    TermBuilder val(final int value) {
        return assign(new Value(value));
    }

    private TermBuilder assign(Node node) {
        if (terms.isEmpty()) {
            throw new TermException(String.format("Invalid term: '%s'", expression));
        }

        Term e = terms.peek();
        add(e, node);
        while (e != null && e.isComplete()) {
            current = terms.pop();
            e = terms.isEmpty() ? null : terms.peek();
        }
        return this;
    }

    private void add(final Term parent, final Node child) {
        // fill in right then left, order is important here!
        if (parent.getRight() == null) {
            parent.setRight(child);
        } else if (parent.getLeft() == null) {
            parent.setLeft(child);
        }
    }

    Term build() {
        if (!terms.isEmpty()) {
            throw new TermException(String.format("Invalid term: '%s'", expression));
        }
        current.hasVariables = variablesAdded;
        return current;
    }
}
