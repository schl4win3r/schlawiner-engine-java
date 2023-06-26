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

final class Preconditions {

    static void checkPositionIndexes(final int start, final int end, final int size) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (start < 0 || end < start || end > size) {
            throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
        }
    }

    static int checkPositionIndex(final int index, final int size) {
        return checkPositionIndex(index, size, "index");
    }

    static int checkPositionIndex(final int index, final int size, final String desc) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
        }
        return index;
    }

    private static String badPositionIndex(final int index, final int size, final String desc) {
        if (index < 0) {
            return "%s (%s) must not be negative".formatted(desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index > size
            return "%s (%s) must not be greater than size (%s)".formatted(desc, index, size);
        }
    }

    private static String badPositionIndexes(final int start, final int end, final int size) {
        if (start < 0 || start > size) {
            return badPositionIndex(start, size, "start index");
        }
        if (end < 0 || end > size) {
            return badPositionIndex(end, size, "end index");
        }
        // end < start
        return "end index (%s) must not be less than start index (%s)".formatted(end, start);
    }

    private Preconditions() {
    }
}
