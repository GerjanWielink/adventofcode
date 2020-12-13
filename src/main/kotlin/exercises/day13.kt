package exercises

import util.loadFileAsStrings
import kotlin.system.measureTimeMillis

fun main () {
  val input = loadFileAsStrings("day13")

//  for (i in (0 .. 200)) {
//    println("${i * 13}\t: ${(i * 13) % 41}")
//  }

  measureTimeMillis {
    println(part2(input))
  }.let{ println(it)}
}

fun part1(input: List<String>) : Int {
  val firstTime = input[0].toInt()
  val inService = input[1].split("""(,(x,)*)""".toRegex()).map { it.toInt() }

  for (time in (firstTime .. firstTime + (inService.maxOrNull() ?: 0))) {
    val viableBus = inService.find { time % it == 0 }

    if (viableBus != null) {
      return viableBus * (time - firstTime)
    }
  }

  error("Time not found")
}

fun part2(input: List<String>) : Long {
  val busList = input[1].split(",").mapIndexed { index, bus ->
    Pair(index, bus.toLongOrNull() ?: 0L)
  }.filter { it.second != 0L }

  var nextBus = 1
  var step = busList.first().second
  var time = busList.first().second
  while (nextBus < busList.size) {
    val (busIndex, bus) = busList[nextBus]
    if ((time + busIndex.toLong()) % bus == 0L) {
      step = lcm(step, bus)
      nextBus += 1
    }

    time += step
  }

  return time - step
}

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b