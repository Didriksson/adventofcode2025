class Day11 {
    fun part1(input: List<String>): Int {
        val parse = input.parse()
        val starterNodes = parse.filter { it.input == "you" }
        val paths = starterNodes.map { findPathsFor(it, parse, true,true) }
        return paths.sum()
    }

    private fun findPathsFor(current: Deviceoutputs, table: List<Deviceoutputs>, seenFft: Boolean, seenDac: Boolean): Int {
        if(table.isEmpty()) return 1

        val nextTable = table.filter { it != current }
        return current.output.sumOf { next ->
            if (next == "out") {
                if(seenDac && seenFft)
                    1
                else
                    0
            } else {
                val seenFft = seenFft || next == "fft"
                val seenDac = seenDac || next == "dac"
                val nextPaths = nextTable.filter { it.input == next }
                nextPaths.sumOf { n -> findPathsFor(n, nextTable, seenFft, seenDac) }
            }
        }
    }

    fun part2(input: List<String>): Int {
        val parse = input.parse()
        val starterNodes = parse.filter { it.input == "svr" }
        val paths = starterNodes.map { findPathsFor(it, parse, seenDac = false, seenFft = false) }
        return paths.sum()
    }

}

private fun List<String>.parse(): List<Deviceoutputs> {
    val takeWhile = map {
        val lines = it.split(" ")
        val input = lines[0].dropLast(1)
        val rest = lines.drop(1)
        Deviceoutputs(input, rest)
    }
    return takeWhile
}

data class Deviceoutputs(val input: String, val output: List<String>)
