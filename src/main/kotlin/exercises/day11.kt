package exercises

import util.loadFileAsString

fun main() {
  val input = loadFileAsString("day11")

  val game = GameOfLife(input)

  while (game.iterate()) {}

  println(game.state.count{ it == '#' })


}

class GameOfLife(input: String) {
  private val columns = input.indexOfFirst { it == '\n' } + 1
  private val rows = (input.length + 1) / columns

  private var previousState = ""
  var state = input

  fun iterate() : Boolean {
    val newState = state.mapIndexed { index, seat ->
      when(seat) {
        '#' -> if (countVisibleOccupiedSeats(index) >= 5) 'L' else '#'
        'L' -> if (countVisibleOccupiedSeats(index) == 0) '#' else 'L'
        else -> seat
      }
    }.joinToString("")

    previousState = state
    state = newState

    return state != previousState
  }

  private fun countVisibleOccupiedSeats(idx: Int) : Int {
    return (-1 .. 1).map { deltaCol -> (-1 .. 1).map{ deltaRow ->
      occupiedSeatVisibleInDirection(idx, Pair(deltaRow, deltaCol))
    }}.flatten().count { it }
  }

  private fun occupiedSeatVisibleInDirection(idx: Int, direction: Pair<Int, Int>) : Boolean {
    if (direction == Pair(0, 0)) {
      return false
    }
    val (row, col) = getRowColFromIdx(idx) ?: return false
    val (dRow, dCol) = direction

    var offset = 1
    while (true) {
      when (getSeat(row + (dRow * offset), col + (dCol * offset))) {
        '#' -> return true
        '.' -> offset += 1
        else -> return false
      }
    }
  }

  private fun countAdjacentOccupiedSeats(idx: Int) : Int {
    val (row, col) = getRowColFromIdx(idx) ?: error("Invalid index: $idx")

    return (-1 .. 1).map { deltaCol -> (-1 .. 1).map{ deltaRow ->
      if (deltaCol == 0 && deltaRow == 0)
        false
      else getSeat(row + deltaRow, col + deltaCol) == '#'
    }}.flatten().count { it }
  }

  private fun getSeat(row: Int, col: Int) : Char? {
    if (row >= rows || col >= columns || col < 0 || row < 0 || (col == columns - 1 && row == rows - 1)) {
      return null
    }

    return state[getIdxFromRowCol(row, col)]
  }

  private fun getRowColFromIdx(idx: Int) = if (idx <= rows * columns)
    Pair(idx / columns, idx % columns)
  else null

  private fun getIdxFromRowCol(row: Int, col: Int) = col + (row * columns)
}
