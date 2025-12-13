import java.awt.geom.Path2D
import java.awt.geom.Rectangle2D
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

    fun part2(exampleInput: List<String>): Long {
        val polygon = exampleInput.parse().toSet()
        val pairs = polygon.flatMap { c1 ->
            polygon.filter { c2 -> c1 != c2 }.map { c2 -> sortera(c1, c2) }
        }.toSet()


        val path = Path2D.Double()
        path.moveTo(polygon.first().first.toDouble(), polygon.first().second.toDouble())
        polygon.drop(1).forEach { pp -> path.lineTo(pp.first.toDouble(), pp.second.toDouble()) }
        path.closePath()

        val areas = pairs
            .map { calculateArea(it) }
            .filter{allPointsInpolygon(it.first, path) }

        return areas.maxBy { it.second }.second
    }

    private fun allPointsInpolygon(it: Pair<Pair<Long, Long>, Pair<Long, Long>>, path: Path2D.Double): Boolean {
        val (coord1, coord2) = it
        val minX = minOf(coord1.first, coord2.first)
        val maxX = maxOf(coord1.first, coord2.first)
        val minY = minOf(coord1.second, coord2.second)
        val maxY = maxOf(coord1.second, coord2.second)
        val width = maxX - minX
        val height = maxY - minY
        val rectangle = Rectangle2D.Double(minX.toDouble(), minY.toDouble(), width.toDouble(), height.toDouble())

        return path.contains(rectangle)

    }


}

private fun List<String>.parse(): List<Pair<Long, Long>> {
    return this.map { it.split(",") }.map { Pair(it[0].toLong(), it[1].toLong()) }
}
