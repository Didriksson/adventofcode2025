import kotlin.collections.listOf
import kotlin.math.pow
import kotlin.math.sqrt

private fun List<String>.parse(): List<Triple<Int, Int, Int>> {
    return map { it.split(",") }
        .map { items -> items.map { a -> a.toInt() } }
        .map { koords -> Triple(koords[0], koords[1], koords[2]) }
}

class Day08 {

    fun part1(input: List<String>, connections: Int): Int {
        val coords = input.parse()
        val junctions = runForConnections(coords, listOf(), mutableListOf(),10)

        return 0
    }

    private fun runForConnections(coords: List<Triple<Int, Int, Int>>, connected: List<Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>>, junctions: MutableList<MutableSet<Triple<Int, Int, Int>>>, numberOfConnectionsRem: Int): List<Any> {
        if(numberOfConnectionsRem == 0){
            return junctions
        }

        val pair = findClosestPairNotIn(coords, connected)
        addToJunctions(pair, junctions)
        return runForConnections(coords, connected + pair, junctions, numberOfConnectionsRem -1 )
    }

    private fun addToJunctions(pair: Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>, junctions: MutableList<MutableSet<Triple<Int, Int, Int>>>) {
        val found = junctions.find { it.contains(pair.first) || it.contains(pair.second) }

        if (found != null) {
            found.add(pair.first)
            found.add(pair.second)
        } else {
            junctions.add(mutableSetOf(pair.first, pair.second))
        }
    }

    private fun findClosestPairNotIn(coords: List<Triple<Int, Int, Int>>, connected: List<Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>>): Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>> {
        val map = coords.map { coord ->
                    coords.filter { it != coord }
                    .map { Triple(coord, it, straightLineDistance(it, coord)) }
                    .filterNot { checkInList(it.first, it.second, connected) }
                    .minBy { it.third }
                }
        val minBy = map.minBy { it.third }
        return Pair(minBy.first, minBy.second)
    }

    private fun checkInList(
        p1: Triple<Int, Int, Int>,
        p2: Triple<Int, Int, Int>,
        tocheck: List<Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>>
    ): Boolean {
        return tocheck.contains(Pair(p1, p2)) || tocheck.contains(Pair(p2, p1))
    }

    private fun straightLineDistance(c1: Triple<Int, Int, Int>, c2: Triple<Int, Int, Int>): Double {
        val deltaSum = ((c2.first.toDouble() - c1.first.toDouble()).pow(2) +
                        (c2.second.toDouble() - c1.second.toDouble()).pow(2) +
                        (c2.third.toDouble() - c1.third.toDouble()).pow(2))

        return sqrt(deltaSum)
    }


}





