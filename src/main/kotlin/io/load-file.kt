package io

import Main

fun loadFileAsLongs(fileName: String) : List<Long> = Main::class.java
    .getResource(fileName)
    .readText()
    .split("\r\n")
    .filter { it.matches("\\d+".toRegex())}
    .map { it.toLong() }