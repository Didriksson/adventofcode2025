
class Day07 {

    fun part1(input: List<String>): Int {
        val height = input.size
        val width = input.first().length
        val map = input.flatMapIndexed { y, row ->
            row.mapIndexed { x, col -> Pair(x, y) to col }.filter { it.second != '.' }
        }.toMap()
        val toEval = map.filter { it.value == 'S' }.entries.first()
        val splits = calculateFor(toEval.key, map as MutableMap<Pair<Int, Int>, Char>, width, height)

        return splits.size
    }

    private fun calculateFor(coord: Pair<Int, Int>, map: MutableMap<Pair<Int, Int>, Char>, width: Int, height: Int): Set<Pair<Int, Int>> {
        (coord.second..height)
            .forEach { y ->
                val newcoord = Pair(coord.first, y)
                val item = map[Pair(coord.first, y)]
                when (item) {
                    '^' -> {
                        val mapped = setOf(Pair(coord.first - 1, y), Pair(coord.first + 1, y))
                            .filter { it.first in 0..<height && it.second < width && it.second > 0 }
                            .flatMap { calculateFor(it, map, width, height) }
                            .toSet()
                        return mapped + newcoord
                    }
                    '|' -> {
                        return setOf()
                    }
                    else -> {
                        map.putIfAbsent(Pair(coord.first, y), '|')
                    }
                }
            }

        return setOf()
    }

    fun part2(input: List<String>): Long {
        val height = input.size
        val width = input.first().length
        val map = input.flatMapIndexed { y, row ->
            row.mapIndexed { x, col -> Pair(x, y) to col }.filter { it.second != '.' }
        }.toMap()
        val toEval = map.filter { it.value == 'S' }.entries.first()
        val counts = countPathsDag(map, toEval.key, width, height)
        return counts
    }
    private fun countPathsDag(graph: Map<Pair<Int, Int>, Char>, start: Pair<Int, Int>, width: Int, height: Int): Long {
        val counts = mutableMapOf<Pair<Int,Int>, Long>()
        val (startX, startY) = start

        counts[(Pair(startX, startY))] = 1L
        for (y in startY + 1..height) {
            for (x in 0 until width) {
                val notsplitsum = if (graph[Pair(x, y - 1)] != '^') {
                    counts.getOrDefault(Pair(x, y - 1), 0L)
                } else 0

                val splitsum = listOf(Pair(x - 1, y - 1), Pair(x + 1, y - 1))
                    .filter { graph[it] == '^' }.sumOf { counts.getOrDefault(it, 0L) }

                counts[Pair(x,y)] = splitsum + notsplitsum
            }
        }


        return counts.filter { it.key.second == height }.values.sum()
    }

}





