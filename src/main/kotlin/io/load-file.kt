package io

import Main

fun loadFileAsStrings(fileName: String) : List<String> = Main::class.java
  .getResource(fileName)
  .readText()
  .split("\r\n")

fun loadFileAsLongs(fileName: String) : List<Long> = loadFileAsStrings(fileName)
  .filter { it.matches("\\d+".toRegex())}
  .map { it.toLong() }
