import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day06Test {

    @Test
    fun example1() {
        val exampleInput = """
            123 328  51 64 
             45 64  387 23 
              6 98  215 314
            *   +   *   +  
        """.trimIndent().lines()
        val day = Day06()
        val result1 = day.part1(exampleInput)
        assertEquals(4277556, result1)
    }

    @Test
    fun example2() {
        val exampleInput = """
            123 328  51 64 
             45 64  387 23 
              6 98  215 314
            *   +   *   +  
        """.trimIndent().lines()
        val day = Day06()
        val result = day.part2(exampleInput)
        assertEquals(3263827, result)
    }


    @Test
    fun runPart1() {
        val input = Path("src/input.txt").readText().trim().lines()
        val part1 = Day06().part1(input)
        println("Result $part1")
        val part2 = Day06().part2(input)
        println("Result $part2")
    }

}