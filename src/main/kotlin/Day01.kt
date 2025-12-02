package se.lantmateriet.digitalansokan


typealias Dial = Int

class Day01 {


    fun part1(input: List<String>, start: Int): Int {
        return input.fold(Pair(0, start))
        { acc, line -> performA(line, acc) }.first
    }

    fun part2(input: List<String>, start: Int): Int {
        return input.fold(Pair(0, start))
        { acc, line -> performB(line, acc) }.first
    }

    fun turn(instruction: String, dial: Dial): Pair<Int, Int> {
        val direction = instruction.take(1)
        val steps = instruction.drop(1).toInt()

        val lapses = steps / 100
        val rest = steps.mod(100)


        val newDial = if (direction == "L") {
            dial - rest
        } else {
            dial + rest
        }

        return if (dial == 0 || newDial in 1..99){
            Pair(newDial, lapses)
        } else {
            Pair(newDial, lapses + 1)
        }
    }

    fun performA(instruction: String, acc: Pair<Int, Dial>): Pair<Int, Dial> {
        val (zeros, dialposition) = acc
        val (position, _) = turn(instruction, dialposition)
        val newposition = position.mod(100)
        val newZeroes = if (newposition == 0) zeros + 1 else zeros
        return Pair(newZeroes, newposition)
    }

    fun performB(instruction: String, acc: Pair<Int, Dial>): Pair<Int, Dial> {
        val (zeros, dialposition) = acc
        val (newposition, passes) = turn(instruction, dialposition)
        val moddedposition = newposition.mod(100)
        return Pair(zeros + passes, moddedposition)
    }

}