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

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Iterators {

    private enum EmptyModifiableIterator implements Iterator<Object> {
        INSTANCE;

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class ArrayItr<T> extends AbstractIndexedListIterator<T> {

        static final UnmodifiableListIterator<Object> EMPTY = new ArrayItr<>(new Object[0], 0, 0, 0);

        private final T[] array;
        private final int offset;

        ArrayItr(final T[] array, final int offset, final int length, final int index) {
            super(length, index);
            this.array = array;
            this.offset = offset;
        }

        @Override
        protected T get(final int index) {
            return array[offset + index];
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> UnmodifiableListIterator<T> emptyListIterator() {
        return (UnmodifiableListIterator<T>) ArrayItr.EMPTY;
    }

    @SuppressWarnings("unchecked")
    private static <T> Iterator<T> emptyModifiableIterator() {
        return (Iterator<T>) EmptyModifiableIterator.INSTANCE;
    }

    public static <T> Iterator<T> cycle(final Iterable<T> iterable) {
        return new Iterator<>() {
            Iterator<T> iterator = emptyModifiableIterator();

            @Override
            public boolean hasNext() {
                /*
                 * Don't store a new Iterator until we know the user can't remove() the last returned element anymore.
                 * Otherwise, when we remove from the old iterator, we may be invalidating the new one. The result is a
                 * ConcurrentModificationException or other bad behavior.
                 *
                 * (If we decide that we really, really hate allocating two Iterators per cycle instead of one, we can
                 * optimistically store the new Iterator and then be willing to throw it out if the user calls remove().)
                 */
                return iterator.hasNext() || iterable.iterator().hasNext();
            }

            @Override
            public T next() {
                if (!iterator.hasNext()) {
                    iterator = iterable.iterator();
                    if (!iterator.hasNext()) {
                        throw new NoSuchElementException();
                    }
                }
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }

    @SafeVarargs
    public static <T> UnmodifiableIterator<T> forArray(final T... array) {
        return forArray(array, 0, array.length, 0);
    }

    private static <T> UnmodifiableListIterator<T> forArray(final T[] array, final int offset, final int length,
            final int index) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }
        final int end = offset + length;

        // Technically we should give a slightly more descriptive error on overflow
        Preconditions.checkPositionIndexes(offset, end, array.length);
        Preconditions.checkPositionIndex(index, length);
        if (length == 0) {
            return emptyListIterator();
        }
        return new ArrayItr<T>(array, offset, length, index);
    }

    private Iterators() {
    }
}
