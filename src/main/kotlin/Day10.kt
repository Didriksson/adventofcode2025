import com.microsoft.z3.Context
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status

class Day10 {
    fun part1(input: List<String>): Int {
        val parsed = input.parse()
        val targetReached = parsed
            .map { explorePaths(it) }
        return targetReached.sumOf { it?.seen?.size ?: 9999 }
    }

    private fun explorePaths(initial: Machine): Machine? {
        val queue = ArrayDeque<Machine>()
        queue.add(initial)
        var found: Machine? = null
        while (queue.isNotEmpty() && found == null) {
            val machineToCheck = queue.removeFirst()
            val newMachines = machineToCheck.buttons
                .map { b -> machineToCheck.pressButton(b) }
                .filterNot { it.seen.contains(it.current) }
                .map {
                    val newSeen: Set<List<Boolean>> = buildSet {
                        addAll(machineToCheck.seen)
                        add(machineToCheck.current)
                    }
                    it.copy(seen = newSeen)
                }

            val find = newMachines.find { it.targetReached() }
            if (find != null) {
                found = find
            }
            queue.addAll(newMachines)
        }
        return found
    }

    fun part2(input: List<String>): Int {
        return input.parse().sumOf { z3SolveForMachine(it) }
    }

    // Det här är helt sjukt.
    // Jag fattar 20% men det var min första kontakt med Z3.
    private fun z3SolveForMachine(first: Machine): Int {
        Context().use { ctx ->
            val opt = ctx.mkOptimize()

            val numButtons = first.buttons.size
            val numJoltage = first.joltage.size
            val buttonVectors = first.buttons.map { button ->
                IntArray(numJoltage) { i -> if (i in button) 1 else 0 }
            }

            val variabler = (0 until numButtons).map { ctx.mkIntConst("x_$it") }

            // Måste vara positiva
            variabler.forEach { opt.Add(ctx.mkGe(it, ctx.mkInt(0L))) }

            // 3. Begränsning 2: Mål-ekvationer (Sum(x_i * V_i,j) == T_j)
            (0 until first.joltage.size).forEach { j ->
                val termerForSumma = variabler.mapIndexed { i, xi  ->
                    // Använd den färdigskapade, dimensionskorrekta vektorn:
                    val coE = buttonVectors[i][j] // Nu garanteras buttonVectors[i].size == first.joltage.size
                    ctx.mkMul(xi, ctx.mkInt(coE))
                }

                val summa = ctx.mkAdd(*termerForSumma.toTypedArray())
                val target = ctx.mkInt(first.joltage[j])

                opt.Add(ctx.mkEq(summa, target))
            }

            // Optimera på totala antal steg
            val antalSteg = ctx.mkAdd(*variabler.toTypedArray())
            opt.MkMinimize(antalSteg)

            if(opt.Check() == Status.UNSATISFIABLE){
                throw Error("Men vad i hela fridens namn!")
            }

            val result = opt.model.eval(antalSteg, false) as IntNum
            return result.int

        }
    }
}
private fun List<String>.parse(): List<Machine> {
    val machineLines = map { it.parse() }
    return machineLines
}

private fun String.parse(): Machine {
    val lights = drop(1).takeWhile { it != ']' }.map { it == '#' }
    val buttons = drop(lights.size + 2).takeWhile { it != '{' }.split(" ").filter { it.isNotEmpty() }
        .map { it.replace("(", "").replace(")", "").split(",").map { n -> n.toInt() } }
    val joltage = dropWhile { it != '{' }.drop(1).dropLast(1).split(",").map { it.toInt() }
    return Machine(lights.map { false }, lights, buttons, joltage,  setOf())
}

data class Machine(
    val current: List<Boolean>,
    val target: List<Boolean>,
    val buttons: List<List<Int>>,
    val joltage: List<Int>,
    val seen: Set<List<Boolean>>,
) {

    fun pressButton(button: List<Int>): Machine {
        val newState = current.mapIndexed { idx, state ->
            if (button.contains(idx)) {
                !state
            } else {
                state
            }
        }

        return copy(current = newState)
    }

    fun targetReached(): Boolean {
        return current == target
    }
}