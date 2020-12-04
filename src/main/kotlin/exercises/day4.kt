package exercises

import io.loadFileAsString

fun main() {
  val input = loadFileAsString("day4")

  val validPassports = input
    .split("""\r\n\r\n""".toRegex())
    .count { validatePassword(it, ::simpleValidator) }

  println(validPassports)
}

fun validatePassword(passport: String, validate: (String) -> Boolean) = passport
  .split("""[\s(\r\n)]""".toRegex())
  .filter { it.isNotBlank() }
  .count { validate(it) } == 7


fun simpleValidator(entry: String) = entry.matches(
  """^(byr|iyr|eyr|hgt|hcl|ecl|pid):.*$""".toRegex()
)

fun advancedValidator(entry: String) = entry.split(":").let { (key, value) ->
  when (key) {
    "byr" -> value.matches(Regex("""^\d{4}$""")) && value.toInt() in (1920..2002)
    "iyr" -> value.matches(Regex("""^\d{4}$""")) && value.toInt() in (2010..2020)
    "eyr" -> value.matches(Regex("""^\d{4}$""")) && value.toInt() in (2020..2030)
    "hgt" ->
      Regex("""^(\d+)(cm|in)$""").find(value)?.destructured?.let { (height, type) ->
        if (type == "cm") height.toInt() in (150..193) else height.toInt() in (59..76)
      } ?: false
    "hcl" -> value.matches(Regex("""^#[0-9a-f]{6}$"""))
    "ecl" -> value.matches(Regex("""^(amb|blu|brn|gry|grn|hzl|oth)$"""))
    "pid" -> value.matches(Regex("""^\d{9}$"""))
    else -> false // ignoring cid and always setting it to false allows to just check for length 7
  }
}
