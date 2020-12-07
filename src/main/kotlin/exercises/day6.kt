package exercises

import io.loadFileAsString

fun main() {
  val forms : List<String> = loadFileAsString("day6")
    .split("""(\r?\n){2}""".toRegex())

  forms
    .map { it.replace("""[^\w]""".toRegex(), "").toCharArray().toSet().size }
    .reduce(Int::plus).let { println("Part 1: $it") }

  forms
    .map { it.lines().map{ e -> e.toCharArray().toSet() }.reduce{ acc, set -> set.intersect(acc) }.size }
    .reduce{ acc, i -> acc + i}.let { println("Part 2: $it")}
}