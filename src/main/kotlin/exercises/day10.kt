package exercises

import io.loadFileAsLongs

fun main() {
  val input = loadFileAsLongs("day10").map(Long::toInt)

  val builtIn = input.maxOrNull()!! + 3

  println("Part 1: ${countJoltageDifference(input + listOf(0, builtIn))}")
  println("Part 2: ${countArrangements(input + listOf(0, builtIn))}")
}

fun countArrangements(adapters: List<Int>): Long {
 val intermediateCounts = mutableMapOf(0 to 1L).withDefault { 0L }

  adapters.sorted().forEach { adapter ->
    adapters
      .filter { it in (adapter + 1 .. adapter + 3) }
      .forEach { intermediateCounts[it] = intermediateCounts.getValue(it) + intermediateCounts.getValue(adapter) }
  }

  return intermediateCounts.getValue(adapters.maxOrNull() ?: 0)
}

fun countJoltageDifference(adapters: List<Int>): Int = adapters
  .sorted().zipWithNext()
  .map { it.second - it.first }
  .groupBy { it }
  .let { (it[1]?.size ?: 0) * (it[3]?.size ?: 0) }