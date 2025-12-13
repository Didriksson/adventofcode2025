import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day09Test {

    @Test
    fun example1() {
        val exampleInput = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
        """.trimIndent().lines()
        val day = Day09()
        val result1 = day.part1(exampleInput)
        assertEquals(50, result1)
    }

    @Test
    fun example2() {
        val exampleInput = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
        """.trimIndent().lines()
        val day = Day09()
        val result = day.part2(exampleInput)
        assertEquals(24, result)
    }

    @Test
    fun runPart1and2() {
        val input = Path("src/input.txt").readText().trim().lines()
        val part1 = Day09().part1(input)
        val part2 = Day09().part2(input)
        println("Result $part1")
        println("Result $part2")
    }

}