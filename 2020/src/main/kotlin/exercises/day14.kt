package exercises

import util.loadFileAsStrings

fun main() {
  val input = loadFileAsStrings("day14")

  val interpreter = Interpreter()

  input.forEach {
    interpreter.readInstructions(it, true)
    interpreter.readInstructions(it, false)
  }

  println(interpreter.simpleMemory.values.reduce(Long::plus))
  println(interpreter.advancedMemory.values.reduce(Long::plus))
}

class Interpreter {
  val simpleMemory: MutableMap<Long, Long> = mutableMapOf()
  val advancedMemory: MutableMap<Long, Long> = mutableMapOf()

  private var mask: String = "X".repeat(36)
  private var masks: List<String> = emptyList()

  private val maskRegex = """^mask = ([10X]{36})$""".toRegex()
  private val memoryRegex = """^mem\[(\d+)\] = (\d+)$""".toRegex()

  fun readInstructions(instruction: String, simple: Boolean = true) {
    when {
      maskRegex.matches(instruction) -> if (simple) handleSimpleMask(instruction) else handleAdvancedMask(instruction)
      memoryRegex.matches(instruction) -> if (simple) handleSimpleMemory(instruction) else handleAdvancedMemory(instruction)
      else -> error("Invalid line: $instruction")
    }
  }

  private fun handleSimpleMask(instruction: String) {
    instruction.split(" = ")[1].let {
      mask = it
    }
  }

  private fun handleAdvancedMask(instruction: String) {
    instruction.split(" = ")[1].let {
      masks = getMasks(it)
    }
  }

  private fun handleSimpleMemory(instruction: String) {
    val (location, value) = memoryRegex.find(instruction)?.destructured ?: error("Contract breached :(")

    simpleMemory[location.toLong()] = value.toLong().applySimpleMask(mask)
  }

  private fun handleAdvancedMemory(instruction: String) {
    val (location, value) = memoryRegex.find(instruction)?.destructured ?: error("Contract breached :(")

    masks.map { location.toLong().applyAdvancedMask(it) }.forEach {
      advancedMemory[it] = value.toLong()
    }
  }

  private fun Long.toPaddedBinary(length: Int) = "${"0".repeat(length)}${toString(2)}".takeLast(36)

  private fun getMasks(mask: String) : List<String> {
    if (!mask.contains("X")) {
      return listOf(mask)
    }

    return listOf(mask.replaceFirst('X', '+'), mask.replaceFirst('X', '-')).map { getMasks(it) }.flatten()
  }

  private fun Long.applyAdvancedMask(mask: String): Long = toPaddedBinary(mask.length)
    .mapIndexed { index, bit ->
      when(mask[index]) {
        '1' -> '1'
        '0' -> bit
        '-' -> '0'
        '+' -> '1'
        else -> error("invalid mask: [$mask]")
      }
    }.joinToString ("").toLong(2)

  private fun Long.applySimpleMask(mask: String): Long = toPaddedBinary(mask.length)
    .mapIndexed { index, bit ->
      when (mask[index]) {
        'X' -> bit
        else -> mask[index]
      }
    }.joinToString("").toLong(2)

}