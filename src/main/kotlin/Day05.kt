import kotlin.collections.mutableListOf
import kotlin.math.max

class Day05 {
    fun part1(input: List<String>): Int {
        val ranges = input.takeWhile { it.isNotEmpty() }
                .map { it.split("-").map { rangeparam -> rangeparam.toLong() } }
                .map { it.first() to it.last() }
        val ingredients = input.dropWhile { it.isNotEmpty() }.drop(1)
        val hits = ingredients.filter { isInRange(it, ranges) }
        return hits.size
    }

    private fun isInRange(ingredient: String, ranges: List<Pair<Long, Long>>): Boolean {
        return ranges.any() { it.first <= ingredient.toLong() && ingredient.toLong() <= it.second }
    }

    fun part2(input: List<String>): Long {
        return input.takeWhile { it.isNotEmpty() }
            .map { it.split("-").map { rangeparam -> rangeparam.toLong() } }
            .map { it.first() .. it.last() }
            .sortedBy { it.first }
            .fold(mutableListOf<LongRange>()) { acc, currentRange ->
                if (acc.isEmpty()) {
                    acc.add(currentRange)
                } else {
                    val previousRange = acc.last()
                    val isOverlapping = currentRange.first <= previousRange.last + 1
                    if(isOverlapping) {
                        acc[acc.lastIndex] = previousRange.first..max(previousRange.last, currentRange.last)
                    } else {
                        acc.add(currentRange)
                    }
                }
                acc
            }.fold(0) { acc, range -> acc + range.last - range.first + 1 }

    }


}
