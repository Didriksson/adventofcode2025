import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day05Test {

    @Test
    fun example1() {
        val exampleInput = """
            3-5
            10-14
            16-20
            14-18
            
            1
            5
            8
            11
            17
            32
        """.trimIndent().lines()
        val day = Day05()
        val result1 = day.part1(exampleInput)
        val result2 = day.part2(exampleInput)
        assertEquals(3, result1)
        assertEquals(14, result2)
    }


    @Test
    fun runPart() {
        val input = Path("src/input.txt").readText().trim().lines()
        val part1 = Day05().part1(input)
        println("Result $part1")
        val part2 = Day05().part2(input)
        println("Result $part2")
    }

}