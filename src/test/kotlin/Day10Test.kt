import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day10Test {

    @Test
    fun example1() {
        val exampleInput = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """.trimIndent().lines()
        val day = Day10()
        val result1 = day.part1(exampleInput)
        assertEquals(7, result1)
    }

    @Test
    fun example2() {
        val exampleInput = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """.trimIndent().lines()
        val day = Day10()
        val result = day.part2(exampleInput)
        assertEquals(33, result)
    }

    @Test
    fun runPart1and2() {
        val input = Path("src/input.txt").readText().trim().lines()
        val part1 = Day10().part1(input)
        val part2 = Day10().part2(input)
        println("Result $part1")
        println("Result $part2")
    }
}