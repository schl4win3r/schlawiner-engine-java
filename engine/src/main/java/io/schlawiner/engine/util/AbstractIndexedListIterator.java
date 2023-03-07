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
package io.schlawiner.engine.util;

import java.util.NoSuchElementException;

abstract class AbstractIndexedListIterator<E> extends UnmodifiableListIterator<E> {

    private final int size;
    private int position;

    /** Returns the element with the specified index. This method is called by {@link #next()}. */
    protected abstract E get(int index);

    /**
     * Constructs an iterator across a sequence of the given size with the given initial position. That is, the first call to
     * {@link #nextIndex()} will return {@code position}, and the first call to {@link #next()} will return the element at that
     * index, if available. Calls to {@link #previous()} can retrieve the preceding {@code position} elements.
     *
     * @throws IndexOutOfBoundsException if {@code position} is negative or is greater than {@code
     *                                   size}
     * @throws IllegalArgumentException if {@code size} is negative
     */
    AbstractIndexedListIterator(final int size, final int position) {
        Preconditions.checkPositionIndex(position, size);
        this.size = size;
        this.position = position;
    }

    @Override
    public final boolean hasNext() {
        return position < size;
    }

    @Override
    public final E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return get(position++);
    }

    @Override
    public final int nextIndex() {
        return position;
    }

    @Override
    public final boolean hasPrevious() {
        return position > 0;
    }

    @Override
    public final E previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        return get(--position);
    }

    @Override
    public final int previousIndex() {
        return position - 1;
    }
}
