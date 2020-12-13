package exercises

import util.RepeatingList
import util.loadFileAsStrings
import kotlin.math.absoluteValue

data class Position(var lat: Int = 0, var lon: Int = 0, var direction: Char = 'E') {

  private val directions = RepeatingList(listOf('N', 'E', 'S', 'W'))

  fun apply(instruction: String) {
    val dir = instruction.first()
    val steps = instruction.substring(1).toInt()

    when (dir) {
      'N' -> lat += steps
      'E' -> lon += steps
      'S' -> lat -= steps
      'W' -> lon -= steps
      'R', 'L' -> turn(dir, steps)
      'F' -> apply(instruction.replace('F', direction))
      else -> error("Invalid instruction [$instruction]")
    }
  }

  private fun turn(turn: Char, degrees: Int) = when (turn) {
    'L' -> direction = directions[directions.indexOf(direction) - (degrees / 90)]
    'R' -> direction = directions[directions.indexOf(direction) + (degrees / 90)]
    else -> error("Turn [$turn] not in [L, R].")
  }
}

data class WayPointPosition(val position: Position, var waypoint: Position) {

  fun apply(instruction: String) {
    val dir = instruction.first()
    val steps = instruction.substring(1).toInt()

    when (dir) {
      'N','E', 'S', 'W' -> waypoint.apply(instruction)
      'R', 'L' -> turnWaypoint(dir, steps)
      'F' -> moveTowardsWaypoint(steps)
      else -> error("Invalid instruction [$instruction]")
    }
  }

  private fun turnWaypoint(dir: Char, degrees: Int) {
    val effectiveDegrees = if (dir == 'R') degrees else 360 - degrees

    waypoint = when (effectiveDegrees) {
      90 -> Position(-waypoint.lon, waypoint.lat)
      180 -> Position(-waypoint.lat, -waypoint.lon)
      270 -> Position(waypoint.lon, -waypoint.lat)
      else -> error("Should not be in input")
    }
  }

  private fun moveTowardsWaypoint(steps: Int) {
    position.apply("N${waypoint.lat * steps}")
    position.apply("E${waypoint.lon * steps}")
  }
}


fun main() {
  val instructions = loadFileAsStrings("day12")

  val ship = Position(0, 0, 'E')
  val wayPointShip = WayPointPosition(Position(0, 0), Position(1, 10))

  instructions.forEach {
    ship.apply(it)
    wayPointShip.apply(it)
  }

  println("Part 1: ${ship.lat.absoluteValue + ship.lon.absoluteValue}")
  println("Part 2: ${wayPointShip.position.lat.absoluteValue + wayPointShip.position.lon.absoluteValue}")
}

