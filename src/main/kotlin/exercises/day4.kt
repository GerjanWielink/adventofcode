package exercises

import io.loadFileAsString

fun main() {
  val input = loadFileAsString("day4")

  input
    .split("""\r\n\r\n""".toRegex())
    .count {
      it
        .split("""[\s(\r\n)]""".toRegex())
        .count { entry -> advancedValidator(entry) } == 7
    }.let(::println)

  // as oneliner :')
  loadFileAsString("day4").split("""\r\n\r\n""".toRegex()).count { """((byr:((19[2-9]\d)|(200[0-2])))|(iyr:(20((1\d)|20)))|(eyr:(20((2\d)|30)))|(hgt:((1(([5-8]\d)|(9[0-3]))cm)|(((59)|(6\d)|(7[0-6]))in)))|(hcl:#[\da-f]{6})|(ecl:(amb|blu|brn|gry|grn|hzl|oth))|(pid:\d{9}))(\s|(\r\n)|$)""".toRegex().findAll(it).count() == 7 }.let(::println)
}

fun simpleValidator(entry: String) = entry.matches(
  """^(byr|iyr|eyr|hgt|hcl|ecl|pid):.*$""".toRegex()
)

/**
 * Decided to throw all readability overboard to go full Regex :)
 */
fun advancedValidator(entry: String) = listOf(
      """^byr:((19[2-9]\d)|(200[0-2]))$""".toRegex(), // 1929-2002
      """^iyr:(20((1\d)|20))$""".toRegex(), // 2010-2020
      """^eyr:(20((2\d)|30))$""".toRegex(), // 2020-2030
      """^hgt:((1(([5-8]\d)|(9[0-3]))cm)|(((59)|(6\d)|(7[0-6]))in))$""".toRegex(), //  150-193cm || 59-76in
      """^hcl:#[\da-f]{6}$""".toRegex(),
      """^ecl:(amb|blu|brn|gry|grn|hzl|oth)$""".toRegex(),
      """^pid:\d{9}$""".toRegex()
).any { it.matches(entry) }
