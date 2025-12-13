import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day08Test {

    @Test
    fun example1() {
        val exampleInput = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
        """.trimIndent().lines()
        val day = Day08()
        val result1 = day.part1(exampleInput, 10)
        assertEquals(40, result1)
    }

    @Test
    fun example2() {
        val exampleInput = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
        """.trimIndent().lines()
        val day = Day08()
        val result1 = day.part2(exampleInput)
        assertEquals(25272, result1)
    }

    @Test
    fun runPart1and2() {
        val input = Path("src/input.txt").readText().trim().lines()
        val part1 = Day08().part1(input, 1000)
        val part2 = Day08().part2(input)
        println("Result $part1")
        println("Result $part2")
    }

}