class Day11 {
    fun part1(input: List<String>): Long {
        val parse = input.parse()
        val starterNodes = parse.filter { it.input == "you" }
        val paths = starterNodes.map {
            findPathsFor(
                it,
                parse,
                setOf(),
                mutableMapOf(),
                seenFft = true,
                seenDac = true
            )
        }
        return paths.sum()
    }


    fun part2(input: List<String>): Long {
        val parse = input.parse()
        val starterNodes = parse.filter { it.input == "svr" }
        val paths = starterNodes.map {
            findPathsFor(
                it,
                parse,
                setOf(),
                mutableMapOf(),
                seenFft = false,
                seenDac = false
            )
        }
        return paths.sum()
    }

    private fun findPathsFor(
        current: Deviceoutputs,
        table: List<Deviceoutputs>,
        visited: Set<Int>,
        memo: MemoCache,
        seenFft: Boolean,
        seenDac: Boolean
    ): Long {

        val currentState = State(current.id, seenFft, seenDac)

        if (memo.containsKey(currentState)) {
            return memo[currentState]!!
        }

        if (visited.contains(current.id)) {
            return 0
        }

        val nextVisited = visited + current.id
        var totalResult = 0L

        for (next in current.output) {
            if (next == "out") {
                if (seenDac && seenFft) {
                    totalResult += 1
                }
            } else {
                val nextSeenFft = seenFft || next == "fft"
                val nextSeenDac = seenDac || next == "dac"

                val nextPaths = table.filter { it.input == next }

                val sumOf = nextPaths.sumOf { n ->
                    findPathsFor(n, table, nextVisited, memo, nextSeenFft, nextSeenDac)
                }
                totalResult += sumOf
            }
        }
        memo[currentState] = totalResult
        return totalResult
    }

}

private fun List<String>.parse(): List<Deviceoutputs> {
    val takeWhile = mapIndexed { idx, it ->
        val lines = it.split(" ")
        val input = lines[0].dropLast(1)
        val rest = lines.drop(1)
        Deviceoutputs(idx, input, rest)
    }
    return takeWhile
}

data class Deviceoutputs(val id: Int, val input: String, val output: List<String>)
data class State(val nodeId: Int, val hasFft: Boolean, val hasDac: Boolean)
typealias MemoCache = MutableMap<State, Long>