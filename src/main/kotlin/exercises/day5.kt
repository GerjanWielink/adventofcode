package exercises

import io.loadFileAsStrings

fun main () {
  loadFileAsStrings("day5")
//    listOf("BFFFBBFRRR", "FFFBBBFRRR", "BBFFBBFRLL")
    .map {
      8 * it.slice(0..6).replace('F', '0').replace('B', '1').toInt(2) +
      it.slice(7..9).replace('R', '1').replace('L', '0').toInt(2)
    }
    .let { findMissing(it) }
    .let(::println)

}

fun findMissing (list: List<Int>) : Int? {
  ((list.minOrNull() ?: 0).. (list.maxOrNull() ?: Int.MAX_VALUE)).forEach {
    if(!list.contains(it)) {
      return it
    }
  }

  return null
}

// (1, 1) => 9
// (1, 2) => 10
// (1, 8) => 16
// (2, 1) => 17