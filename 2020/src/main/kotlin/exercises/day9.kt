package exercises

import util.loadFileAsLongs

fun main() {
  val input = loadFileAsLongs("day9")

  findBuggedNumber(input)?.let {
    println("Part 1: $it")

    println("Part 2: ${findEncryptionWeakness(input, it)}")
  }
}

fun findBuggedNumber(input: List<Long>, preamble: Int = 25) =
  input.windowed(preamble + 1).find { find2sum(it.dropLast(1), it.last()) == null }?.last()

fun findEncryptionWeakness(input: List<Long>, buggedNumber: Long, windowSize: Int = 2): Long =
  input.windowed(windowSize).find { it.reduce(Long::plus) == buggedNumber }
    ?.let { window ->
      window.sorted().let { it.first() + it.last() }
    }
    ?: findEncryptionWeakness(input, buggedNumber, windowSize + 1)