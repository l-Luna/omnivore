# *omnivore*

Opus Magnum puzzle & solution file parser & writer in Java, mostly based on [omsp](https://github.com/F43nd1r/omsp/) and [omsim-rs](https://github.com/l-Luna/omsim-rs/) (in Kotlin and Rust respectively).

## usage

add it as a dependency:
```kt
implementation("io.github.l-luna:omnivore:0.3")
```

read a puzzle or solution file, or create them from scratch
```java
Puzzle puzzle = Puzzle.fromFile("./coolPuzzle.puzzle");
Solution solution = Solution.fromBytes(new byte[]{ 4, 0, 0, 0, /* ... */ });
Puzzle internal = Puzzle.fromResource(MyClass.class, "bundled/with/the/jar.puzzle");
Puzzle fromBeyond = Puzzle.fromInputStream(/* you probably want to buffer it */);
Solution fromScratch = new Solution("coolPuzzle", "New Solution 0");
```
(note that all four methods are available for `Puzzle` and `Solution`)

and then write them down again
```java
byte[] bytes = puzzle.toBytes();
solution.toFile("./rightHere.solution");
fromBeyond.toOutputStream(/* still want to buffer this one */);
```
(again, all three are available for `Puzzle` and `Solution`)

## general data types
alongside representing puzzles and solutions, omnivore also provides types like `HexIndex`, `Molecule`, `AtomType`, `Part`... for use in Opus Magnum-related programs.