package exercises

import io.loadFileAsString

fun main() {
  val input = loadFileAsString("day4")

  val validPassports = input
    .split("""\r\n\r\n""".toRegex())
    .count { validatePassword(it, ::advancedValidator) }

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
    "byr" -> value.matches(Regex("""(19[2-9]\d)|(200[0-2])$""")) // 1929-2002
    "iyr" -> value.matches(Regex("""^20((1\d)|20)$""")) // 2010-2020
    "eyr" -> value.matches(Regex("""^20((2\d)|30)$""")) // 2020-2030
    "hgt" -> value.matches(Regex("""^((1(([5-8]\d)|(9[0-3]))cm)|((59)|(6\d)|(7[0-6]))in)$""")) // 59in-76in || 150cm - 193cm
    "hcl" -> value.matches(Regex("""^#[0-9a-f]{6}$"""))
    "ecl" -> value.matches(Regex("""^(amb|blu|brn|gry|grn|hzl|oth)$"""))
    "pid" -> value.matches(Regex("""^\d{9}$"""))
    else -> false // ignoring cid and always setting it to false allows to just check for length 7
  }
}
