package exercises

import util.loadFileAsLongs
import kotlin.system.measureTimeMillis

fun main() {
  val input = loadFileAsLongs("day10").map(Long::toInt)

  val builtIn = input.maxOrNull()!! + 3
  measureTimeMillis {
    println("Part 1: ${countJoltageDifference(input + listOf(0, builtIn))}")
    println("Part 2: ${countArrangements(input + listOf(0, builtIn))}")
  }.let(::println)
}

fun countArrangements(adapters: List<Int>): Long {
  val sorted = adapters.sorted()
  val max = sorted.last()
  val intermediateCounts = LongArray(max + 4) {  0 }
  intermediateCounts[0] = 1

  adapters.sorted().forEach {
    intermediateCounts[it + 1] += intermediateCounts[it]
    intermediateCounts[it + 2] += intermediateCounts[it]
    intermediateCounts[it + 3] += intermediateCounts[it]
  }

  return intermediateCounts[max]
}

fun countJoltageDifference(adapters: List<Int>): Int = adapters
  .sorted().zipWithNext()
  .map { it.second - it.first }
  .groupBy { it }
  .let { (it[1]?.size ?: 0) * (it[3]?.size ?: 0) }