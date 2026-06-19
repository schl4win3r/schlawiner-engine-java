# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Schlawiner is a dice puzzle game engine. Players roll three dice and must combine the dice values (each usable once, optionally multiplied by 10 or 100) using +, -, *, / to reach target numbers between 1 and 100. The player with the smallest total difference wins.

## Build Commands

```bash
# Build and run all tests
./mvnw verify

# Run tests only (skip checkstyle/formatter/license checks)
./mvnw test

# Run a single test class
./mvnw test -Dtest=TermTest

# Run a single test method
./mvnw test -Dtest=TermTest#eval

# Format source code (license headers + eclipse formatter)
./format.sh

# Validate source code (enforcer + checkstyle + license + formatter)
./validate.sh

# Play the game in terminal (requires mvn install first)
./mvnw install && jbang play.java
```

## Build Requirements

- Java 25
- Maven 3.9.9+

## Code Quality

The build enforces WildFly checkstyle rules, Eclipse formatter config (`etc/eclipse-formatter-config.xml`), and Apache 2.0 license headers. Run `./format.sh` before committing to auto-fix formatting and license headers. The `verify` phase checks all of these and fails the build on violations.

## Architecture

All code lives under `io.schlawiner.engine` with four packages:

### `term` - Expression Parser and Evaluator
Binary expression tree supporting `+`, `-`, `*`, `/` with integer-only division. `Term.valueOf(expression)` parses infix notation (via shunting-yard/RPN conversion in `InfixToRPN`) into a tree of `Node` instances (`Term`, `Value`, `Variable`). Terms support named variables with `Assignment`-based evaluation: `term.eval(new Assignment("a", 5))`.

### `algorithm` - Solution Computation
`Algorithm` interface with two implementations that compute optimal solutions for three dice values against a target number. Both try all operator/permutation combinations, each dice value multiplied by 1, 10, or 100:
- `OperationAlgorithm` - brute-force arithmetic using static `Operations` helper methods
- `TermAlgorithm` - uses pre-built `Term` templates with variable assignments

`Solutions` collects all valid results; `bestSolution(Level)` returns a solution matching the difficulty level (EASY returns worst, HARD returns best).

### `game` - Game Flow
`Game` orchestrates the turn loop: iterates `Numbers` (targets) x `Players`, rolls `Dice`, validates human input via `DiceValidator` (ensures each die used exactly once with valid multipliers), computes solutions for computer players. `Settings` is an immutable record controlling number count, retries, penalty, level, and auto-dice.

### `score` - Scoring
`Scoreboard` tracks differences per player per number. `NumberScore` is a number-centric view, `PlayerScore` is a player-centric view. `winners()` returns players with the minimum total difference.

## Conventions

- No external dependencies besides JUnit 5; the engine is a pure Java library
- Accessor methods use field-name style (`player.name()`) not `getName()`
- `Settings` uses immutable `withX()` builder pattern returning new instances
- Division must be exact (no remainders) and divisor must be non-zero; violations throw `TermException`
- `DiceException` for invalid dice usage, `TermException` for parse/eval errors
