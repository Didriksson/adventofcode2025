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
        // val junctions = runForConnections(coords, listOf(), mutableListOf(),10)
        val map = coords.flatMap { coord ->
            coords.filter { it != coord }
                .map { Triple(coord, it, straightLineDistance(it, coord)) }
                }
            .map { sortera(it) }
            .sortedBy { it.third }
            .distinct()
        val junctions: MutableList<MutableSet<Triple<Int, Int, Int>>> = mutableListOf()
        map
            .take(connections)
            .map { Pair(it.first, it.second) }
//            .filterNot { inSameJunction(it, junctions) }
            .map { addToJunctions(it, junctions) }
        return junctions.sortedBy { it.size }.reversed().take(3).fold(1) { acc, triples -> acc * triples.size }
    }

    private fun inSameJunction(
        pair: Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>,
        junctions: List<MutableSet<Triple<Int, Int, Int>>>
    ): Boolean {
        val any = junctions.any { it.contains(pair.first) && it.contains(pair.second) }
        println("$pair: $any")
        return any
    }

    private fun sortera(attSortera: Triple<Triple<Int, Int, Int>, Triple<Int, Int, Int>, Double>): Triple<Triple<Int, Int, Int>, Triple<Int, Int, Int>, Double> {
        val sortedWith = listOf(attSortera.first, attSortera.second).sortedWith(compareBy({ it.first }, { it.second }))
        return Triple(sortedWith[0], sortedWith[1], attSortera.third)
    }

    private fun runForConnections(coords: List<Triple<Int, Int, Int>>, connected: List<Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>>, junctions: MutableList<MutableSet<Triple<Int, Int, Int>>>, numberOfConnectionsRem: Int): List<Any> {
        if(numberOfConnectionsRem == 0){
            return junctions
        }

        val pair = findClosestPairNotIn(coords, connected)
        addToJunctions(pair, junctions)
        return runForConnections(coords, connected + pair, junctions, numberOfConnectionsRem -1 )
    }

    private fun addToJunctions(
        pair: Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>,
        junctions: MutableList<MutableSet<Triple<Int, Int, Int>>>
    ) {
        val (p1, p2) = pair
        val j1 = junctions.find { it.contains(p1) }
        val j2 = junctions.find { it.contains(p2) }

        when {
            j1 != null && j1 == j2 -> {
                j1.add(p1); j1.add(p2)
            }
            j1 != null && j2 != null -> {
                j1.addAll(j2)     // Slå ihop innehållet från j2 till j1
                junctions.remove(j2) // Ta bort det tomma klustret (j2) från listan
            }
            j1 != null -> j1.add(p2)
            j2 != null -> j2.add(p1)
            // Fall 5: Skapa nytt kluster
            else -> junctions.add(mutableSetOf(p1, p2))
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





