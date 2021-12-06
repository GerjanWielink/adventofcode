package exercises

import kotlin.system.measureTimeMillis

fun main() {
  val lastSpoken : MutableMap<Int, Int> = mutableMapOf(
    0 to 1,
    8 to 2,
    15 to 3,
    2 to 4,
    12 to 5,
    1 to 6
  )

  var last = 4
  var turn = 7
  measureTimeMillis {
    while (turn < 30000000) {
      val next = lastSpoken[last]?.let { turn - it } ?: 0
      lastSpoken[last] = turn

      turn += 1
      last = next
    }
  }.let{
    println("$turn: $last")
    println("$it ms")
  }

}