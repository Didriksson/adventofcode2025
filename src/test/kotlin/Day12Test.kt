import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day12Test {

    @Test
    fun example1() {
        val exampleInput = """
            0:
            ###
            ##.
            ##.

            1:
            ###
            ##.
            .##

            2:
            .##
            ###
            ##.

            3:
            ##.
            ###
            ##.

            4:
            ###
            #..
            ###

            5:
            ###
            .#.
            ###

            4x4: 0 0 0 0 2 0
            12x5: 1 0 1 0 2 2
            12x5: 1 0 1 0 3 2
        """.trimIndent().lines()
        val day = Day12()
        val result1 = day.part1(exampleInput)
        assertEquals(2, result1)
    }

    @Test
    fun runPart1and2() {
        val input = Path("src/input.txt").readText().trim().lines()
        val part1 = Day12().part1(input)
        println("Result $part1")
    }
}