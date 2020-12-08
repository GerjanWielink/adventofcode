package exercises

import io.loadFileAsStrings

fun main() {
  val instructions = loadFileAsStrings("day8")

  Program(instructions).let {
    it.run()
    println("Part 1: ${it.accumulator}")
  }

  instructions.withIndex().forEach { (idx, instruction) ->
    val list = instructions.toMutableList()
    list[idx] = flipInstruction(instruction)

    Program(list).let {
      if (it.run() == 0) {
        println("Part 2: ${it.accumulator}")
        return@forEach
      }
    }
  }
}

fun flipInstruction(instruction: String) = instruction
  .replace("nop", "JMP")
  .replace("jmp", "NOP")
  .toLowerCase()


class Program(
  private val instructions: List<String>
) {
  var accumulator = 0
  private var pointer = 0
  private val encounteredPointers = HashSet<Int>()


  /**
   * Returns exit code
   * - 0: Graceful execution
   * - 1: Out of bounds instruction
   * - 2: Loop detected
   */
  fun run(): Int {
    if (pointer == instructions.size) return 0
    if (pointer > instructions.size) return 1
    if (encounteredPointers.contains(pointer)) return 2

    encounteredPointers.add(pointer)

    val (instruction, value) = parseInstruction(instructions[pointer])
    when (instruction) {
      "acc" -> {
        accumulator += value
        pointer += 1
      }
      "jmp" -> pointer += value
      "nop" -> pointer += 1
      else -> throw Exception("Invalid instruction: [$instruction]")
    }

    return run()
  }

  private fun parseInstruction(line: String): Pair<String, Int> =
    line.split(" ").let { Pair(it[0], it[1].toInt()) }
}