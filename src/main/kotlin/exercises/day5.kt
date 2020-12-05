package exercises

import io.loadFileAsStrings

fun main () {
  val seatIds = loadFileAsStrings("day5")
    .map {
      8 * it.slice(0..6).replace('F', '0').replace('B', '1').toInt(2) +
      it.slice(7..9).replace('R', '1').replace('L', '0').toInt(2)
    }

  // part 1
  seatIds
    .maxOrNull()
    ?.let { println("Part 1: $it") }

  // part 2
  seatIds
    .sorted().zipWithNext()
    .find { it.second - it.first != 1 }
    ?.let { println("Part 2: ${it.first + 1}") }

}