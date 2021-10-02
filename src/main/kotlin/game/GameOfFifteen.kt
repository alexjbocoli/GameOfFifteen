package game

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int>(4)

    override fun initialize() {
        val cellList = board.getAllCells().toMutableList()
        val initialPermutation = initializer.initialPermutation
        for (i in 0 until cellList.size - 1) {
            board[cellList[i]] = initialPermutation[i]
        }
    }

    override fun canMove(): Boolean {
        return board.any { it == null }
    }

    override fun hasWon(): Boolean {
        return board.getCurrentValues().take(15) == (1..15).toList()
    }

    override fun processMove(direction: Direction) {
        board.moveValues(direction)
    }

    override fun get(i: Int, j: Int): Int? {
        return board.run { get(getCell(i, j)) }
    }
}

/*
 * Updates the values of the two adjacent cells on the board
 */
fun GameBoard<Int>.updateBoard(nullCell: Cell, cellToMove: Cell) {
    val valueToMove = get(cellToMove)
    set(nullCell, valueToMove)
    set(cellToMove, null)
}

/*
 * Moves the cells on the board according to the requested directions
 */
fun GameBoard<Int>.moveValues(direction: Direction) {
    val nullCell = find { it == null }
    val row = nullCell?.i
    val col = nullCell?.j
    when (direction) {
        Direction.UP -> {
            val cellToMove = row?.plus(1)?.let { i -> col?.let { j -> getCell(i, j) } }
            if (nullCell != null && cellToMove != null) this.updateBoard(nullCell, cellToMove)
        }
        Direction.DOWN -> {
            val cellToMove = row?.minus(1)?.let { i -> col?.let { j -> getCell(i, j) } }
            if (nullCell != null && cellToMove != null) this.updateBoard(nullCell, cellToMove)
        }
        Direction.RIGHT -> {
            val cellToMove = row?.let { i -> col?.minus(1)?.let { j -> getCell(i, j) } }
            if (nullCell != null && cellToMove != null) this.updateBoard(nullCell, cellToMove)
        }
        Direction.LEFT -> {
            val cellToMove = row?.let { i -> col?.plus(1)?.let { j -> getCell(i, j) } }
            if (nullCell != null && cellToMove != null) this.updateBoard(nullCell, cellToMove)
        }
    }
}

/*
 * Get all the current values on the board
 */
fun GameBoard<Int>.getCurrentValues(): List<Int?> {
    val currentValues = ArrayList<Int?>()
    this.getAllCells().forEach { currentValues.add(this[it]) }
    return currentValues
}