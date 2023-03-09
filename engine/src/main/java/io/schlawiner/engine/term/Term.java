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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public final class Term implements Node {

    public static Term valueOf(final String expression) throws TermException {
        return TermParser.parse(expression);
    }

    private final Operator operator;
    private Node parent;
    private Node left;
    private Node right;
    boolean hasVariables;

    Term(final Operator operator) {
        this.operator = operator;
        this.hasVariables = false;
    }

    public int eval(final Assignment... assignments) {
        if (hasVariables && assignments.length == 0) {
            throw new TermException(String.format("Unable to eval term. No assignments for %s", getVariables()));
        }
        return new EvalIterator().eval(this, Assignment.byName(assignments));
    }

    public String print(final Assignment... assignments) {
        return new PrintIterator().print(this, Assignment.byName(assignments));
    }

    boolean isComplete() {
        return left != null && right != null;
    }

    public int[] getValues() {
        return new GetValuesIterator().values(this);
    }

    public List<Operator> getOperators() {
        return new GetOperatorsIterator().operators(this);
    }

    public List<Variable> getVariables() {
        return new GetVariablesIterator().variables(this);
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
        return left;
    }

    @Override
    public void setLeft(final Node left) {
        this.left = left;
        this.left.setParent(this);
    }

    @Override
    public Node getRight() {
        return right;
    }

    @Override
    public void setRight(final Node right) {
        this.right = right;
        this.right.setParent(this);
    }

    // ------------------------------------------------------ iterators

    static class EvalIterator {

        int eval(final Node node, final Map<String, Integer> assignments) {
            Stack<Integer> stack = new Stack<>();
            postOrder(node, stack, assignments);
            return stack.pop();
        }

        private void postOrder(final Node node, final Stack<Integer> stack, final Map<String, Integer> assignments) {
            if (node instanceof Term t) {
                postOrder(node.getLeft(), stack, assignments);
                postOrder(node.getRight(), stack, assignments);
                int right = stack.pop();
                int left = stack.pop();
                int result = switch (t.operator) {
                    case PLUS -> left + right;
                    case MINUS -> left - right;
                    case TIMES -> left * right;
                    case DIVIDED -> {
                        if (right == 0 || left % right != 0) {
                            throw new TermException("Illegal division: " + left + " / " + right);
                        }
                        yield left / right;
                    }
                };
                stack.push(result);
            } else if (node instanceof Variable v) {
                if (!assignments.containsKey(v.name)) {
                    throw new TermException(String.format("Unable to eval term. Missing assignment %s", v.name));
                }
                stack.push(assignments.get(v.name));
            } else if (node instanceof Value v) {
                stack.push(v.value);
            }
        }
    }

    static class PrintIterator {

        String print(final Node node, final Map<String, Integer> assignments) {
            StringBuilder builder = new StringBuilder();
            inOrder(node, assignments, builder);
            return builder.toString();
        }

        private void inOrder(final Node node, final Map<String, Integer> assignments, final StringBuilder builder) {
            if (node != null) {
                inOrder(node.getLeft(), assignments, builder);
                if (node instanceof Variable || node instanceof Value) {
                    boolean bracket = needsBracket(node);
                    if (bracket && node == node.getParent().getLeft()) {
                        builder.append("(");
                    }
                    if (node instanceof Variable var) {
                        if (assignments.containsKey(var.name)) {
                            builder.append(assignments.get(var.name));
                        } else {
                            builder.append(var.name);
                        }
                    } else {
                        builder.append(node);
                    }
                    if (bracket && node == node.getParent().getRight()) {
                        builder.append(")");
                    }
                } else if (node instanceof Term e) {
                    builder.append(" ").append(e.operator).append(" ");
                }
                inOrder(node.getRight(), assignments, builder);
            }
        }

        private boolean needsBracket(final Node node) {
            if (node.getParent() instanceof Term parent && node.getParent().getParent() instanceof Term) {
                Term grandparent = (Term) parent.getParent();
                return parent.operator.precedence() < grandparent.operator.precedence();
            }
            return false;
        }
    }

    static class GetValuesIterator {

        int[] values(final Term term) {
            List<Integer> numbers = new ArrayList<>();
            inOrder(term, numbers);
            return numbers.stream().mapToInt(Integer::intValue).toArray();
        }

        private void inOrder(final Node node, final List<Integer> numbers) {
            if (node != null) {
                inOrder(node.getLeft(), numbers);
                if (node instanceof Value v) {
                    numbers.add(v.value);
                }
                inOrder(node.getRight(), numbers);
            }
        }
    }

    static class GetVariablesIterator {

        List<Variable> variables(final Term term) {
            List<Variable> variables = new ArrayList<>();
            inOrder(term, variables);
            return variables;
        }

        private void inOrder(final Node node, final List<Variable> variables) {
            if (node != null) {
                inOrder(node.getLeft(), variables);
                if (node instanceof Variable v) {
                    variables.add(v);
                }
                inOrder(node.getRight(), variables);
            }
        }
    }

    static class GetOperatorsIterator {
        List<Operator> operators(final Term term) {
            List<Operator> operators = new ArrayList<>();
            inOrder(term, operators);
            return operators;
        }

        private void inOrder(final Node node, final List<Operator> operators) {
            if (node != null) {
                inOrder(node.getLeft(), operators);
                if (node instanceof Term t) {
                    operators.add(t.operator);
                }
                inOrder(node.getRight(), operators);
            }
        }
    }
}
