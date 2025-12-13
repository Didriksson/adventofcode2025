import kotlin.math.pow
import kotlin.math.sqrt

private fun List<String>.parse(): List<Junctionbox> {
    return map { it.split(",") }
        .map { items -> items.map { a -> a.toInt() } }
        .map { koords -> Triple(koords[0], koords[1], koords[2]) }
}

typealias Junctionbox = Triple<Int,Int,Int>

class Day08 {

    fun part1(input: List<String>, connections: Int): Int {
        val coords = input.parse()
        val nodes = calculateNodes(coords)
        val junctions: MutableList<MutableSet<Junctionbox>> = mutableListOf()
        nodes
            .take(connections)
            .map { Pair(it.first, it.second) }
            .map { addToJunctions(it, junctions) }
        return junctions.sortedBy { it.size }.reversed().take(3).fold(1) { acc, triples -> acc * triples.size }
    }

    private fun calculateNodes(coords: List<Junctionbox>): List<Triple<Junctionbox, Junctionbox, Double>> {
        val map = coords.flatMap { coord ->
            coords.filter { it != coord }
                .map { Triple(coord, it, straightLineDistance(it, coord)) }
        }
            .map { sortera(it) }
            .sortedBy { it.third }
            .distinct()
        return map
    }

    private fun sortera(attSortera: Triple<Junctionbox, Junctionbox, Double>): Triple<Junctionbox, Junctionbox, Double> {
        val sortedWith = listOf(attSortera.first, attSortera.second).sortedWith(compareBy({ it.first }, { it.second }))
        return Triple(sortedWith[0], sortedWith[1], attSortera.third)
    }

    private fun addToJunctions(
        pair: Pair<Junctionbox, Junctionbox>,
        junctions: MutableList<MutableSet<Junctionbox>>
    ) {
        val (p1, p2) = pair
        val j1 = junctions.find { it.contains(p1) }
        val j2 = junctions.find { it.contains(p2) }
        when {
            j1 != null && j1 == j2 -> {
                j1.add(p1); j1.add(p2)
            }
            j1 != null && j2 != null -> {
                j1.addAll(j2)
                junctions.remove(j2)
            }
            j1 != null -> j1.add(p2)
            j2 != null -> j2.add(p1)
            else -> junctions.add(mutableSetOf(p1, p2))
        }
    }



    private fun straightLineDistance(c1: Junctionbox, c2: Junctionbox): Double {
        val deltaSum = ((c2.first.toDouble() - c1.first.toDouble()).pow(2) +
                        (c2.second.toDouble() - c1.second.toDouble()).pow(2) +
                        (c2.third.toDouble() - c1.third.toDouble()).pow(2))

        return sqrt(deltaSum)
    }

    fun part2(input: List<String>): Long {
        val coords = input.parse()
        val nodes = calculateNodes(coords)
        val junctions: MutableList<MutableSet<Junctionbox>> = mutableListOf()
        coords.forEach { junctions.add(mutableSetOf(it)) }
        val foundPairs = nodes.firstNotNullOf { (first, second) ->
            val pair = Pair(first, second)
            addToJunctions(pair, junctions)

            if (junctions.size == 1) {
                pair
            } else {
                null
            }
        }
        return foundPairs.first.first.toLong() * foundPairs.second.first.toLong()

    }


}





