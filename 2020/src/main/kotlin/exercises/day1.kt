package exercises

import util.loadFileAsLongs
import kotlin.system.measureTimeMillis

/**
 * Took some inspiration from https://coderbyte.com/algorithm/two-sum-problem
 * for the find2sum.
 */
fun main() {
  val inputNumbers = loadFileAsLongs("day1")

  val time2sum = measureTimeMillis {
    val result = find2sum(inputNumbers, 2020)
    if (result != null) println("${result.first} * ${result.second} = ${result.first * result.second}")
    else println("Not found...")
  }

  println("Took $time2sum ms")

  val time3sum = measureTimeMillis {
    val result = find3sum(inputNumbers, 2020)
    if (result != null) println("${result.first} * ${result.second} * ${result.third} = ${result.first * result.second * result.third}")
    else println("Not found...")
  }

  println("Took $time3sum ms")

}

fun find2sum(inputNumbers: List<Long>, target: Long): Pair<Long, Long>? {
  val encounteredNumbers = HashSet<Long>()

  inputNumbers.forEach {
    if (encounteredNumbers.contains(target - it)) {
      return Pair(it, target - it)
    }
    encounteredNumbers.add(it)
  }
  return null
}

fun find3sum(inputNumbers: List<Long>, target: Long): Triple<Long, Long, Long>? {
  // we'll need at least 3 numbers
  if (inputNumbers.size < 3) return null

  // fixing one number we'll need to find a 2 sum of the remainder
  val result = find2sum(inputNumbers.drop(1), target - inputNumbers.first())

  // if a result is found return it...
  if (result != null) return Triple(inputNumbers.first(), result.first, result.second)

  // if no result is found there is no combination possible with the first element of the input
  return find3sum(inputNumbers.drop(1), target)
}
