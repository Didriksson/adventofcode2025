import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day04Test {

    @Test
    fun example1() {
        val exampleInput = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
        """.trimIndent().lines()
        val day = Day04()
        val result1 = day.part1(exampleInput)
        assertEquals(13, result1)
    }

    @Test
    fun example2() {
        val exampleInput = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
        """.trimIndent().lines()
        val day = Day04()
        val result1 = day.part2(exampleInput)
        assertEquals(43, result1)
    }

    @Test
    fun runPart() {
        val input = Path("src/input.txt").readText().trim().lines()
        val part1 = Day04().part1(input)
        val part2 = Day04().part2(input)
        println("Result $part1")
        println("Result $part2")
    }

}