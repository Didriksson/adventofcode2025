class Day02 {

    fun part1(input: String): Long {
        fun predikatPartA(number: Long): Boolean {
            val (first, second) = number.toString().splitInMiddle()
            return first == second
        }

        return input.split(",").flatMap { getInvalidForRange(it, ::predikatPartA) }.sum()
    }

    fun part2(input: String): Long {
        fun predikatPartB(number: Long): Boolean {
            return number.toString().matches("""\b(\d+)\1+\b""".toRegex())
        }

        return input.split(",").flatMap { getInvalidForRange(it, ::predikatPartB) }.sum()
    }

    private fun getInvalidForRange(idrange: String, predicate: (Long) -> Boolean): List<Long> {
        val parts = idrange.split("-")
        val start = parts[0].toLong()
        val end = parts[1].toLong()

        return (start..end).filter { predicate(it) }
    }

    private fun String.splitInMiddle(): Pair<String, String> {
        val midIndex = this.length / 2
        val firstHalf = this.substring(0, midIndex)
        val secondHalf = this.substring(midIndex)
        return Pair(firstHalf, secondHalf)
    }
}