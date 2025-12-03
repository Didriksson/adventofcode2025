import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day03Test {

    @Test
    fun example1() {
        val exampleInput = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
        """.trimIndent().lines()
        val day = Day03()
        val result1 = day.part1(exampleInput)
        assertEquals(357, result1)
    }

    @Test
    fun example1_part2() {
        val exampleInput = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
        """.trimIndent().lines()
        val day = Day03()
        val result2 = day.part2(exampleInput)
        assertEquals(3121910778619, result2)
    }

    @Test
    fun runPart1() {
        val input = Path("src/input.txt").readText().trim().lines()
        val part1 = Day03().part1(input)
        val part2 = Day03().part2(input)
        println("Result $part1")
        println("Result $part2")
    }

}