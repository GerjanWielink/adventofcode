package util

import Main

fun loadFileAsString(fileName: String) : String = Main::class.java
  .getResource(fileName)
  .readText()

fun loadFileAsStrings(fileName: String) : List<String> = loadFileAsString(fileName)
  .split("\r\n")

fun loadFileAsLongs(fileName: String) : List<Long> = loadFileAsStrings(fileName)
  .filter { it.matches("\\d+".toRegex())}
  .map { it.toLong() }
