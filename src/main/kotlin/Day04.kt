class Day04 {
    fun part1(exampleInput: List<String>): Int {
        val diagram = parseDiagram(exampleInput)
        val filter = diagram.filter { checkAdjacent(it, diagram) }
        return filter.size
    }


    fun part2(input: List<String>): Int {
        val diagram = parseDiagram(input)
        val result = checkTilNoMoreRemovable(diagram)
        return diagram.size - result.size;
    }

    private fun checkTilNoMoreRemovable(diagram: Map<Pair<Int, Int>, Char>): Map<Pair<Int, Int>, Char> {
        val initialSize = diagram.size
        val removed = diagram.filter { !checkAdjacent(it, diagram) }
        return if(removed.size == initialSize) {
            removed
        } else {
            checkTilNoMoreRemovable(removed)
        }
    }

    private fun checkAdjacent(
        point: Map.Entry<Pair<Int, Int>, Char>,
        diagram: Map<Pair<Int, Int>, Char>
    ): Boolean {
        val point = point.key
        val toCheck = listOf(
            Pair(-1, -1), Pair(0, -1), Pair(1, -1),
            Pair(-1, 0),                               Pair(1, 0),
            Pair( -1, 1), Pair(0,  1), Pair(1, 1))
            .map { p -> Pair(p.first + point.first, p.second + point.second) }

        val hits = toCheck.filter { isHit(it, diagram) }

        return hits.size < 4
    }

    private fun isHit(it: Pair<Int, Int>, diagram: Map<Pair<Int, Int>, Char>): Boolean {
        return diagram.contains(it)
    }

    private fun parseDiagram(exampleInput: List<String>): Map<Pair<Int, Int>, Char> = exampleInput
        .flatMapIndexed { y, row ->
            row.mapIndexed { x, col ->
                Pair(x, y) to col
            }
        }.filter { it.second != '.' }.toMap()


}
