package tictactoe2

import kotlin.math.abs

/**
 * project: https://hyperskill.org/projects/123/stages/658/implement
 * solutions:  * project: https://hyperskill.org/projects/123/stages/658/implement#solutions
 * The `TicTacToeStep` class represents a Tic-Tac-Toe board.
 * It accepts an array of strings representing the rows of the board, with a default setup.
 *
 * @param lines An array of strings representing the Tic-Tac-Toe board rows.
 *              The default is a 3x3 grid with "X", "O", "_" or " ".
 */

class CoordinatesOutsideException: Exception()
class CellIsOccupiedException: Exception()

class TicTacToeStep(var inputString: String) {
    private var board3x3: MutableList<MutableList<Char>>

    init {
        require(inputString.length == 9) { "Input string must be exactly 9 characters long." }

        board3x3 = mutableListOf()
        for (i in 0 until 3) {
            val row = mutableListOf<Char>()
            for (j in 0 until 3) {
                row.add(inputString[i * 3 + j])
            }
            board3x3.add(row)
        }



    }

    override fun toString(): String {
        val result = StringBuilder()
        result.append("---------\n")
        for (i in board3x3.indices) {
            result.append("|")
            for (j in board3x3[i].indices) {
                result.append(" ${board3x3[i][j]}")
            }
            result.append(" |\n")
        }
        result.append("---------")
        return result.toString()
    }

    fun analysePlayerWinningsHorizontally(gamer: Char): Int {
        var wins = 0
        for (i in 0 until 3) {
            var count = 0
            for (j in 0 until 3) {
                if (board3x3[i][j] == gamer) {
                    ++count
                }
            }
            if (count == 3) {
                ++wins
            }
        }
        return wins
    }

    fun analysePlayerWinningsVertically(gamer: Char): Int {
        var wins = 0
        for (i in 0 until 3) {
            var count = 0
            for (j in 0 until 3) {
                if (board3x3[j][i] == gamer) {
                    ++count
                }
            }
            if (count == 3) {
                ++wins
            }
        }
        return wins
    }

    fun analysePlayerWinningsDiagonally(gamer: Char): Int {
        var wins = 0
        if (board3x3[0][0] == gamer && board3x3[1][1] == gamer && board3x3[2][2] == gamer) {
            ++wins
        }
        if (board3x3[0][2] == gamer && board3x3[1][1] == gamer && board3x3[2][0] == gamer) {
            ++wins
        }
        return wins
    }

    fun analyze(): String {
        var result: String = "Draw"
        var x = 0
        var o = 0
        var n = 0
        var gamerWins: MutableMap<Char, Int> = mutableMapOf('X' to 0, 'O' to 0)
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val ch = board3x3[i][j]
                when (ch) {
                    'X' -> ++x
                    'O' -> ++o
                    '_', ' ' -> ++n
                    else -> throw Error("Illegal input character: $ch")
                }
            }
        }
        if (abs(x - o) > 1) {
            return "Impossible"
        }
        for (gamer in listOf('X', 'O')) {
            var wins = 0
            wins += analysePlayerWinningsHorizontally(gamer)
            wins += analysePlayerWinningsVertically(gamer)
            wins += analysePlayerWinningsDiagonally(gamer)
            gamerWins[gamer] = wins
        }
        if (gamerWins['X']!! > 1 && gamerWins['O']!! > 1 || gamerWins['X']!! == 1 && gamerWins['O']!! == 1) {
            return "Impossible"
        }
        for (gamer in listOf('X', 'O')) {
            if (gamerWins[gamer] == 1) {
                return "$gamer wins"
            }
        }
        if (n != 0) {
            return "Game not finished"
        }
        return result
    }

    fun gameMove(user: Char) {
        require(user == 'X' || user == 'O') {"gameMove(user = 'X|O')"}
        var run = true
        while (run) {
            try {
                val (x, y) = readln().split(" ").map { it.toInt() }
                if (x !in 1..3 || y !in 1..3) {throw CoordinatesOutsideException()}
                if (board3x3[x-1][y-1] !in " _" ) {throw CellIsOccupiedException()}
                board3x3[x-1][y-1] = user
                run = false
            }
            catch (e: NumberFormatException) {
                println("You should enter numbers!")
            }
            catch (e: CoordinatesOutsideException) {
                println("Coordinates should be from 1 to 3!")
            }
            catch (e: CellIsOccupiedException) {
                println("This cell is occupied!")
            }
            catch (e: Exception) {
                println("Unknown error: ${e.message}")
            }
        }
    }

    fun display() {
        println(this)
    }
}

fun main() {
    val ticTacToe = TicTacToeStep(readln())
    ticTacToe.display()
    ticTacToe.gameMove(user = 'X')
    ticTacToe.display()
}