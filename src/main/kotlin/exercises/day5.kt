package exercises

import io.loadFileAsStrings
// 832, 517
fun main () {
  val seatIds = loadFileAsStrings("day5")
    .map { it.map { c -> if (c in setOf('B', 'R')) '1' else '0' }.joinToString("").toInt(2) }

  // part 1
  seatIds.maxOrNull()?.let { println("Part 1: $it") }

  // part 2
  seatIds.sorted().zipWithNext().find { it.second - it.first != 1 }?.let { println("Part 2: ${it.first + 1}") }

}