package exercises

import io.loadFileAsStrings

fun main() {
  val bags = loadFileAsStrings("day7")
    .map(::getBagsMap)
    .toMap()

  bags.keys.count { canContainShinyGold(it, bags) }.let { println("Part 1: $it")}
  countContainedBags("shiny gold", bags).let { println("Part 2: $it")}

}

fun countContainedBags(color: String, bagsMap: Map<String, Map<String, Int>>) : Long {
  return (bagsMap[color]?.map { it.value +  it.value * countContainedBags(it.key, bagsMap) }?.fold(0, Long::plus) ?: 0L)
}

fun canContainShinyGold(color: String, bagsMap: Map<String, Map<String, Int>>): Boolean {
  if (bagsMap[color]?.keys?.contains("shiny gold") == true) {
    return true
  }

  return bagsMap[color]?.keys?.any { canContainShinyGold(it, bagsMap) } ?: false
}


fun getBagsMap(line: String): Pair<String, Map<String, Int>> {
  val (color, contents) = """^(\w+ \w+) bags contain (.*)$""".toRegex().find(line)?.destructured
    ?: error("invalid line: [$line]")

  return Pair(color, parseBagContents(contents))
}

fun parseBagContents(contents: String): Map<String, Int> {
  if (contents.trim() == "no other bags.") {
    return emptyMap()
  }

  return contents.split(",")
    .map {
      val (amount, color) = """^(\d) (\w+ \w+) bags?\.?""".toRegex().find(it.trim())?.destructured
        ?: error("Invalid bag encountered [$it]")

      Pair(color, amount.toInt())
    }.toMap()
}
