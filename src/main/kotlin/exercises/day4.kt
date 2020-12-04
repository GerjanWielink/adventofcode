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

/**
 * Decided to throw all readability overboard to go full Regex :)
 */
fun advancedValidator(entry: String) = entry.split(":").let { (key, value) ->
  value.matches(when (key) {
    "byr" -> """(19[2-9]\d)|(200[0-2])$""".toRegex() // 1929-2002
    "iyr" -> """^20((1\d)|20)$""".toRegex() // 2010-2020
    "eyr" -> """^20((2\d)|30)$""".toRegex() // 2020-2030
    "hgt" -> """^(1(([5-8]\d)|(9[0-3]))cm)|(((59)|(6\d)|(7[0-6]))in)$""".toRegex() //  150-193cm || 59-76in
    "hcl" -> """^#[\da-f]{6}$""".toRegex()
    "ecl" -> """^(amb|blu|brn|gry|grn|hzl|oth)$""".toRegex()
    "pid" -> """^\d{9}$""".toRegex()
    else -> """(?=a)b""".toRegex() // always fails as it looks for a character which is both an 'a' and 'b'
  })
}
