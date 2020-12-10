package exercises

import io.loadFileAsLongs

fun main() {
  val input = loadFileAsLongs("day10").map(Long::toInt)

  val builtIn = input.maxOrNull()!! + 3

  println("Part 1: ${countJoltageDifference(input + listOf(0, builtIn))}")
  println("Part 2: ${countArrangements(input + listOf(0, builtIn))}")
}

fun countArrangements(adapters: List<Int>): Long {
 val map : MutableMap<Int, Long> = mutableMapOf(0 to 1)

  adapters.sorted().forEach { adapter ->
    adapters
      .filter { it in (adapter + 1 .. adapter + 3) }
      .forEach { map[it] = (map[it] ?: 0L) + (map[adapter] ?: 0L) }
  }

  return map[adapters.maxOrNull() ?: 0] ?: 0L
}

fun countJoltageDifference(adapters: List<Int>): Int = adapters
  .sorted().zipWithNext()
  .map { it.second - it.first }
  .groupBy { it }
  .let { (it[1]?.size ?: 0) * (it[3]?.size ?: 0) }