package exercises

import util.loadFileAsStrings


fun main() {
  val instructions = loadFileAsStrings("day8")

  println("Part 1: ${Program(instructions).run().second}")

  run {
    instructions.withIndex().forEach { (idx, instruction) ->
      if (instruction.startsWith("acc")) return@forEach

      val modifiedInstructions = instructions.toMutableList()
      modifiedInstructions[idx] = flipInstruction(instruction)

      Program(modifiedInstructions).run().let { (exitCode, value) ->
        if(exitCode == 0) {
          println("Part 2: $value")
          return@run
        }
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

  private var acc = 0
  private var pointer = 0
  private val encounteredPointers = HashSet<Int>()


  /**
   * Returns exit code
   * - 0: Graceful execution
   * - 1: Out of bounds instruction
   * - 2: Loop detected
   */
  fun run(): Pair<Int, Int> {
    if (pointer == instructions.size) return Pair(0, acc)
    if (pointer > instructions.size) return Pair(1, acc)
    if (encounteredPointers.contains(pointer)) return Pair(2, acc)

    encounteredPointers.add(pointer)

    val (instruction, value) = parseInstruction(instructions[pointer])
    when (instruction) {
      "acc" -> {
        acc += value
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