import kotlin.math.abs

class Day09 {
    fun part1(exampleInput: List<String>): Long {
        val parsed = exampleInput.parse()
        val pairs = parsed.flatMap { c1 ->
            parsed.filter { c2 -> c1 != c2 }.map { c2 -> sortera(c1, c2) }
        }.toSet()
        val areas = pairs.map { calculateArea(it) }
        return areas.maxBy { it.second }.second
    }

    private fun calculateArea(it: Pair<Pair<Long, Long>, Pair<Long, Long>>): Pair<Pair<Pair<Long, Long>, Pair<Long, Long>>, Long> {
        val (p1,p2) = it
        val area = (abs(p2.first - p1.first)+1) * (1 + abs(p2.second - p1.second))
        return Pair(it, area)
    }

    private fun sortera(p1: Pair<Long, Long>, p2: Pair<Long,Long>): Pair<Pair<Long,Long>, Pair<Long,Long>> {
        val sortedWith = listOf(p1,p2).sortedWith(compareBy({ it.first }, { it.second }))
        return Pair(sortedWith[0], sortedWith[1])
    }

}

private fun List<String>.parse(): List<Pair<Long, Long>> {
    return this.map { it.split(",") }.map { Pair(it[0].toLong(), it[1].toLong()) }
}
