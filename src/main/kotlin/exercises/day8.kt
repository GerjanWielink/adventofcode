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


enum class Instruction {
  acc, jmp, nop
}

class Program(
  private val instructions: List<String>
) {
  var accumulator = 0
  private var pointer = 0
  private val encounteredPointers = HashSet<Int>()


  /**
   * Returns exit code
   * - 0: Gracefull execution
   * - 1: Loop detected
   * - 2: Outofbounds instruction
   */
  fun run(): Int {
    if (pointer == instructions.size) return 0
    if (encounteredPointers.contains(pointer)) return 1
    if (pointer > instructions.size) return 2

    encounteredPointers.add(pointer)

    val (instruction, value) = parseInstruction(instructions[pointer])
    when (instruction) {
      Instruction.acc -> {
        accumulator += value
        pointer += 1
      }
      Instruction.jmp -> pointer += value
      Instruction.nop -> pointer += 1
    }

    return run()
  }

  private fun parseInstruction(line: String): Pair<Instruction, Int> =
    line.split(" ").let { Pair(Instruction.valueOf(it[0]), it[1].toInt()) }
}