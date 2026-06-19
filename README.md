# Schlawiner Engine

Schlawiner is a dice puzzle game engine written in Java. Players roll three dice and must combine the dice values using `+`, `-`, `*`, and `/` to reach target numbers between 1 and 100. Each die value can be multiplied by 10 or 100 and must be used exactly once. The differences between the target numbers and the calculated results are summed up. The player with the smallest total difference wins.

## Examples

| Target | Dice  | Solution        | Difference |
|--------|-------|-----------------|------------|
| 53     | 4 6 1 | 4 + 60 - 10     | 1          |
| 40     | 2 2 1 | 2 * (20 + 1)    | 2          |
| 22     | 3 2 1 | 30 + 2 - 10     | 0          |
| 96     | 5 1 5 | (500 - 10) / 5  | 2          |
| 42     | 6 6 6 | 6 * 6 + 6       | 0          |

## Requirements

- Java 25
- Maven 3.9.9+

## Build

```shell
# Build and run all tests (includes checkstyle, formatting, and license checks)
./mvnw verify

# Run tests only
./mvnw test

# Run a single test class
./mvnw test -Dtest=TermTest

# Run a single test method
./mvnw test -Dtest=TermTest#eval

# Format source code (license headers + Eclipse formatter)
./format.sh

# Validate source code (enforcer + checkstyle + license + formatter)
./validate.sh
```

## Play

To play Schlawiner in the terminal, use the provided [JBang](https://www.jbang.dev) script:

```shell
./mvnw install
jbang play.java
```

## Architecture

All code lives under `io.schlawiner.engine` with four packages:

### `term`

Binary expression tree for parsing and evaluating arithmetic expressions. `Term.valueOf(expression)` parses infix notation (via shunting-yard / RPN conversion) into a tree of `Node` instances (`Term`, `Value`, `Variable`). Terms support named variables resolved at evaluation time via `Assignment`. Division must be exact (no remainder).

### `algorithm`

Computes optimal solutions for three dice values against a target number. The `Algorithm` interface has two implementations — `OperationAlgorithm` (brute-force static operations) and `TermAlgorithm` (pre-built term templates with variable assignments). Both try all operator and permutation combinations across multipliers (1, 10, 100).

### `game`

Orchestrates the turn loop: iterates target numbers x players, rolls dice, validates human input via `DiceValidator`, and computes solutions for computer players. `Settings` is an immutable record controlling game parameters. `Room` manages pre-game player gathering.

### `score`

Tracks differences per player per number. `Scoreboard` provides two complementary views: `NumberScore` (number-centric) and `PlayerScore` (player-centric). `winners()` returns players with the minimum total difference.

## License

[Apache License 2.0](LICENSE)
