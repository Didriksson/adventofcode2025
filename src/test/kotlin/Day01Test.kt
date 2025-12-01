import org.junit.jupiter.api.Assertions.*
import se.lantmateriet.digitalansokan.Day01
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day01Test {

    @Test
    fun example1() {
        val exampleInput = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
        """.trimIndent().split("\n");

        val day1 = Day01();
        val result1 = day1.part1(exampleInput, 50);
        val result2 = day1.part2(exampleInput, 50);
        assertEquals(3, result1)
        assertEquals(6, result2)
    }

    @Test
    fun example_1000() {
        val exampleInput = """
            L1000
            R1000
            L100
            R100
        """.trimIndent().split("\n");

        val day1 = Day01();
        val result = day1.part2(exampleInput, 0);
        assertEquals(22, result)
    }

    @Test
    fun runPart1() {
        val input = Path("src/input1.txt").readText().trim().lines()
        val part1 = Day01().part1(input, 50)
        val part2 = Day01().part2(input, 50)
        println("Result $part1");
        println("Result $part2");
    }
}