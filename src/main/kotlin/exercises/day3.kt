package exercises

import io.loadFileAsStrings

fun main () {
  val input = loadFileAsStrings("day3")

  println("Part 1: ${countBottomWalk(Pair(0,0), input, 0, Pair(3, 1))}")

  val result = listOf(
    Pair(1, 1),
    Pair(3, 1),
    Pair(5, 1),
    Pair(7, 1),
    Pair(1, 2)
  )
    .map { countBottomWalk(Pair(0,0), input, 0, it) }
    .reduce(Long::times)

  println("Part 2: $result")
}

fun countBottomWalk(coordinates: Pair<Int, Int>, board: List<String>, encounteredTrees: Long, slope: Pair<Int, Int>) : Long {
  val (x, y) = coordinates

  if (y >= board.size) {
    return encounteredTrees
  }

  val treesOnStep = if(board[y][x % board[y].length] == '#') 1 else 0

  return countBottomWalk(
    coordinates = Pair(x + slope.first, y + slope.second),
    board = board,
    encounteredTrees = encounteredTrees + treesOnStep,
    slope = slope
  )
}