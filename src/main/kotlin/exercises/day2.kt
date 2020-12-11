package exercises

import util.loadFileAsStrings

fun main() {
  val input = loadFileAsStrings("day2")

  println(input.count { isValidPasswordFirst(it) })
  println(input.count { isValidPasswordSecond(it) })
}

fun isValidPasswordFirst(line: String) : Boolean {
  val (minStr, maxStr, char, password) = unpackPasswordLine(line)

  return  password.count { it.toString() == char} in (minStr.toInt() .. maxStr.toInt())
}


fun isValidPasswordSecond(line: String) : Boolean {
  val (pos1, pos2, char, password) = unpackPasswordLine(line)

  return (password[pos1.toInt() - 1].toString() == char) != (password[pos2.toInt() - 1].toString() == char)
}

fun unpackPasswordLine(line: String) : List<String> = """^(\d+)-(\d+) ([a-z]): ([a-z]+)$"""
  .toRegex()
  .find(line)
  ?.destructured
  ?.toList() ?: error("Invalid line encountered: $line")