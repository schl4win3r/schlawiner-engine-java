# Schlawiner Engine / Java

Schlawiner is a game of dice where you have to reach numbers between 1 and 100 using the basic arithmetics +, -, * and / in any order. The game is played with three dice. Each dice number can be multiplied with 10 or 100 and must be used exactly once.

| Number | Dice Number                                   | Possible Solution | Difference |
|--------|-----------------------------------------------|-------------------|------------|
| 53     | <span style="font-size: x-large">⚃ ⚅ ⚀</span> | 4 + 60 - 10       | 1          |
| 40     | <span style="font-size: x-large">⚀ ⚁ ⚁</span> | 2 * (20 + 1)      | 2          |
| 22     | <span style="font-size: x-large">⚁ ⚀ ⚂</span> | 30 + 2 - 10       | 0          |
| 96     | <span style="font-size: x-large">⚄ ⚀ ⚄</span> | (500 - 10) / 5    | 2          |
| 42     | <span style="font-size: x-large">⚅ ⚅ ⚅</span> | 6 * 6 + 6         | 0          |

The differences between the numbers and the calculated results are summed up. The player with the smallest difference wins.

Have fun!
