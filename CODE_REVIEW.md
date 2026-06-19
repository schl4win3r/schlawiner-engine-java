# Code Review — Schlawiner Engine

**Date:** 2026-06-19
**Scope:** Full codebase review (`io.schlawiner.engine.*`)
**Java version:** 25

---

## CRITICAL

### 1. `Solutions.bestSolution(Level)` is a TODO stub

**File:** `src/main/java/io/schlawiner/engine/algorithm/Solutions.java:50-53`

```java
public Solution bestSolution(final Level level) {
    // TODO Choose bestSolution based on level
    return bestSolution();
}
```

`Level` (EASY/MEDIUM/HARD) is accepted from users via `Settings` and passed to `Game.solve()`, but the level is completely ignored. Computer players always play at maximum strength. This is a functional bug — the feature doesn't work.

**Fix:** Track all valid solutions (not just the best), then select based on level: EASY returns worst, MEDIUM returns median, HARD returns best.

---

### 2. `Dice` record exposes mutable array

**File:** `src/main/java/io/schlawiner/engine/game/Dice.java:20`

The record `Dice(int[] numbers)` exposes its internal array directly. Callers can corrupt game state:

```java
dice.numbers()[0] = 99; // silently corrupts the dice
```

**Fix:** Add defensive copies in the compact constructor and accessor:

```java
public record Dice(int[] numbers) {
    public Dice { numbers = numbers.clone(); }
    @Override public int[] numbers() { return numbers.clone(); }
}
```

---

### 3. `PlayerScoreTest` is entirely commented out

**File:** `src/test/java/io/schlawiner/engine/score/PlayerScoreTest.java:39-61`

Both test methods (`newInstance`, `scores`) are empty shells with all assertions commented out. `PlayerScore` has zero test coverage.

**Fix:** Uncomment and update the assertions to match the current API.

---

## HIGH

### 4. `differentDiceNumbers()` has wrong semantics

**File:** `src/main/java/io/schlawiner/engine/algorithm/AbstractAlgorithm.java:67-69`

```java
boolean differentDiceNumbers(final int a, final int b, final int c) {
    return a != b || a != c;
}
```

This returns `true` when *any two* values differ (e.g., `(5, 5, 3)` → `true`). The method name implies *all three* are different. If the intent is "not all same", rename to `notAllSame()`. If the intent is "all different", fix the logic to `a != b && a != c && b != c`. Either way, the current name/behavior mismatch will confuse maintainers and may produce duplicate solutions when two dice are equal.

---

### 5. Legacy `Stack` usage throughout

**Files:**
- `src/main/java/io/schlawiner/engine/term/InfixToRPN.java:70`
- `src/main/java/io/schlawiner/engine/term/Term.java:109`
- `src/main/java/io/schlawiner/engine/term/TermBuilder.java:23`

`java.util.Stack` is a legacy synchronized class. Replace with `ArrayDeque`:

```java
Deque<String> stack = new ArrayDeque<>();
// push → push/addFirst, pop → pop/removeFirst, peek → peek/peekFirst, empty → isEmpty
```

---

### 6. `InfixToRPN.OPS` uses mutable `HashSet` with static initializer block

**File:** `src/main/java/io/schlawiner/engine/term/InfixToRPN.java:26-34`

```java
private static final Set<Character> OPS = new HashSet<>();
static {
    OPS.add('(');
    OPS.add(')');
    // ...
}
```

**Fix:** Replace with:

```java
private static final Set<Character> OPS = Set.of('(', ')', '+', '-', '*', '/');
```

---

### 7. `TermAlgorithm.ABC` and `PERMUTATIONS` are mutable static lists

**File:** `src/main/java/io/schlawiner/engine/algorithm/TermAlgorithm.java:27-28`

These `ArrayList`s are populated in a static block but remain mutable. Any code could accidentally call `.add()` or `.clear()`.

**Fix:** Wrap with `Collections.unmodifiableList()` at the end of the static block, or restructure to use `List.of()`.

---

### 8. `Operator` enum: verbose per-constant `toString()` overrides

**File:** `src/main/java/io/schlawiner/engine/term/Operator.java:20-46`

Each enum constant overrides `toString()` individually (4 anonymous inner classes). Use a field instead:

```java
public enum Operator {
    PLUS(0, "+"), MINUS(0, "-"), TIMES(5, "*"), DIVIDED(5, "/");

    private final int precedence;
    private final String symbol;

    Operator(int precedence, String symbol) {
        this.precedence = precedence;
        this.symbol = symbol;
    }

    @Override public String toString() { return symbol; }
    public int precedence() { return precedence; }
}
```

---

### 9. `Operator.toOperator()` returns `null` on no match

**File:** `src/main/java/io/schlawiner/engine/term/Operator.java:58-69`

Returning `null` forces callers to null-check. This is a parse operation where invalid input should be explicit.

**Fix:** Return `Optional<Operator>`:

```java
public static Optional<Operator> toOperator(String token) {
    if (token == null) return Optional.empty();
    return Optional.ofNullable(switch (token) {
        case "+" -> PLUS; case "-" -> MINUS;
        case "*" -> TIMES; case "/" -> DIVIDED;
        default -> null;
    });
}
```

Note: this requires updating `isOperator()` and callers in `InfixToRPN`.

---

### 10. `Numbers.get()` returns magic value `-1` on invalid index

**File:** `src/main/java/io/schlawiner/engine/game/Numbers.java:83-88`

```java
public int get(final int index) {
    if (index > -1 && index < numbers.length) {
        return numbers[index];
    }
    return -1;
}
```

Silent failure. `-1` is indistinguishable from a valid return in some contexts.

**Fix:** Throw `IndexOutOfBoundsException` to fail fast.

---

### 11. `PlayerScore.score(int)` returns `null` on invalid index

**File:** `src/main/java/io/schlawiner/engine/score/PlayerScore.java:108-112`

Returns `null` on out-of-bounds, but all other score lookups return `Score.EMPTY`. Inconsistent — callers may NPE.

**Fix:** Return `Score.EMPTY` instead of `null`, or throw.

---

## MEDIUM

### 12. `util` package is a Guava port that JDK 25 makes unnecessary

**Files:** Entire `src/main/java/io/schlawiner/engine/util/` package (~150 lines across 5 files)

- `Iterators.forArray()` → `List.of(array).iterator()`
- `Iterators.cycle()` → keep (no JDK equivalent), but inline into `Players`
- `Preconditions` → `Objects.checkIndex()`, `Objects.checkFromToIndex()` (JDK 9+)
- `UnmodifiableIterator`, `UnmodifiableListIterator`, `AbstractIndexedListIterator` → all exist to support the above two methods

**Recommendation:** Inline `cycle()` into `Players`, replace `forArray()` usages with `List.of()`, delete the rest.

---

### 13. `Node` sealed interface exposes setter methods publicly

**File:** `src/main/java/io/schlawiner/engine/term/Node.java:23,27,31`

The sealed interface defines `void parent(Node)`, `void left(Node)`, `void right(Node)` as part of the public API. This is needed during tree construction by `TermBuilder`, but allows external code to mutate a parsed tree after the fact.

**Fix:** Consider splitting into a package-private mutable builder interface and a public read-only interface, or at minimum make the setters package-private by moving them off the interface and onto the concrete classes.

---

### 14. `Player` has a public mutable `retries` setter

**File:** `src/main/java/io/schlawiner/engine/game/Player.java:78-80`

```java
public void retries(int retries) {
    this.retries = retries;
}
```

`Game` already manages retries through `retry()`. The public setter breaks encapsulation — anyone can reset retries arbitrarily.

**Fix:** Make `retries(int)` package-private.

---

### 15. `OperationAlgorithm.computePermutation()` is 136 lines of repetitive code

**File:** `src/main/java/io/schlawiner/engine/algorithm/OperationAlgorithm.java:29-165`

The pattern `solutions.add(Operations.xxx(a, b, c)); if (differentDiceNumbers) { ... }` is repeated 18 times. This is the largest DRY violation in the codebase.

**Fix:** Consider a data-driven approach — a list of operation method references paired with their permutation sets.

---

### 16. Swallowed exceptions in `TermAlgorithm`

**File:** `src/main/java/io/schlawiner/engine/algorithm/TermAlgorithm.java:153-154, 159-161`

```java
try {
    solutions.add(new Solution(term.print(assignments), term.eval(assignments)));
} catch (TermException ignore) {
}
```

`TermException` is caught and silently ignored. This is intentional (invalid divisions like `5 / 3` are expected and skipped), but a brief comment explaining why would prevent future confusion.

---

### 17. `Scoreboard` returns mutable list copies

**File:** `src/main/java/io/schlawiner/engine/score/Scoreboard.java:74-82`

`numberScores()` returns `Arrays.asList()` (structurally immutable but element-mutable), `playerScores()` returns a new `ArrayList` (fully mutable).

**Fix:** Use `List.of()` for true immutability.

---

### 18. `Numbers` uses `Integer[]` instead of `int[]`

**File:** `src/main/java/io/schlawiner/engine/game/Numbers.java:26`

The `Integer[]` boxing exists only to support `Iterators.forArray()`. With the util package removed (finding #12), switch to `int[]` with a custom iterator or `IntStream`.

---

## LOW

### 19. `new Random()` on every call

**Files:**
- `src/main/java/io/schlawiner/engine/game/Dice.java:26`
- `src/main/java/io/schlawiner/engine/game/Numbers.java:36`

Creates a new `Random` instance on every invocation. Use `ThreadLocalRandom.current()` for better performance and seed distribution:

```java
int[] numbers = { 1 + ThreadLocalRandom.current().nextInt(6), ... };
```

---

### 20. `Numbers.next()` throws raw `ArrayIndexOutOfBoundsException`

**File:** `src/main/java/io/schlawiner/engine/game/Numbers.java:64`

Should throw `NoSuchElementException` (standard iterator contract) with a descriptive message.

---

### 21. `Scoreboard.winners()` could use streams

**File:** `src/main/java/io/schlawiner/engine/score/Scoreboard.java:91-104`

The two-pass min-then-filter loop works but is verbose for Java 25:

```java
public List<Player> winners() {
    int min = playerSums.values().stream().mapToInt(Integer::intValue).min().orElse(0);
    return playerSums.entrySet().stream()
        .filter(e -> e.getValue() == min)
        .map(Map.Entry::getKey)
        .toList();
}
```

---

### 22. Missing `@Override` on `Dice.toString()`

**File:** `src/main/java/io/schlawiner/engine/game/Dice.java:36`

The `toString()` method lacks the `@Override` annotation.

---

## Test Coverage Summary

| Package | Test Files | Notes |
|---------|-----------|-------|
| `term` | 4 test classes | Good coverage of parsing, evaluation, and edge cases |
| `algorithm` | 4 test classes | Good; `Operations` only tested indirectly via algorithm tests |
| `game` | 5 test classes | Reasonable; no edge case tests for `retry()` exhaustion |
| `score` | 3 test classes | **`PlayerScoreTest` is entirely commented out — 0% coverage** |
| `util` | 0 test classes | No tests (recommend deleting the package instead) |

---

## Summary

| Severity | Count | Key themes |
|----------|-------|------------|
| CRITICAL | 3 | Non-functional Level feature, mutable array exposure, dead tests |
| HIGH | 8 | Wrong method semantics, legacy collections, null returns, mutable statics |
| MEDIUM | 7 | Unnecessary util package, DRY violations, encapsulation gaps |
| LOW | 4 | ThreadLocalRandom, streams, annotations |

**Highest-impact items:** The non-functional `Level` feature (#1) and the `differentDiceNumbers()` semantics mismatch (#4) both affect correctness of game behavior.
