package exercises

import io.loadFileAsLongs

fun main() {
  val input = loadFileAsLongs("day9")

  val target = input.windowed(26).find { find2sum(it.dropLast(1), it.last()) == null }?.last() ?: error("No sum found..")
  println("Part 1: $target")

  var windowSize = 2
  run {
    while (windowSize < input.size) {
      input.windowed(windowSize)
        .find { it.reduce(Long::plus) == target }
        ?.let {
          it.sorted().let { sorted -> println("Part 2: ${sorted.first() + sorted.last()}") }
          return@run
        }
      windowSize++
    }
  }

}