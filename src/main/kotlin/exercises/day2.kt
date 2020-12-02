package exercises

import io.loadFileAsStrings

fun main() {
  val input = loadFileAsStrings("day2")

  println(input.count { isValidPasswordFirst(it) })
  println(input.count { isValidPasswordSecond(it) })
}

fun isValidPasswordFirst(line: String) : Boolean {
  val (minStr, maxStr, char, password) = "^(\\d+)-(\\d+)\\s*([a-z]):\\s([a-z]*)".toRegex()
    .find(line)!!
    .destructured

  val occurrences = password.count { it.toString() == char}

  return  minStr.toInt() <= occurrences && occurrences <= maxStr.toInt()
}

fun isValidPasswordSecond(line: String) : Boolean {
  val (pos1, pos2, char, password) = "^(\\d+)-(\\d+)\\s*([a-z]):\\s([a-z]*)".toRegex()
    .find(line)!!
    .destructured

  return listOf(
    password[pos1.toInt() - 1].toString() == char,
    password[pos2.toInt() - 1].toString() == char,
  ).count{ it } == 1
}