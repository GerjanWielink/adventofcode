package exercises

import io.loadFileAsStrings

fun main() {
  val bags = loadFileAsStrings("day7")
    .map(::getBagsMap)
    .toMap()

  bags.keys.count { canContainShinyGold(it, bags) }.let(::println)
  countContainedBags("shiny gold", bags).let(::println)

}

fun countContainedBags(color: String, bagsMap: Map<String, Map<String, Int>>) : Long {
  if (!bagsMap.containsKey(color) || bagsMap[color]?.keys?.size == 0) {
    return 0
  }

  return (bagsMap[color]?.values?.reduce(Int::plus)?.toLong() ?: 0L) + (bagsMap[color]?.map { it.value * countContainedBags(it.key, bagsMap) }?.reduce(Long::plus) ?: 0L)
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
