import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day11Test {

    @Test
    fun example1() {
        val exampleInput = """
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out
        """.trimIndent().lines()
        val day = Day11()
        val result1 = day.part1(exampleInput)
        assertEquals(5, result1)
    }

    @Test
    fun example2() {
        val exampleInput = """
            svr: aaa bbb
            aaa: fft
            fft: ccc
            bbb: tty
            tty: ccc
            ccc: ddd eee
            ddd: hub
            hub: fff
            eee: dac
            dac: fff
            fff: ggg hhh
            ggg: out
            hhh: out
        """.trimIndent().lines()
        val day = Day11()
        val result = day.part2(exampleInput)
        assertEquals(2, result)
    }

    @Test
    fun runPart1and2() {
        val input = Path("src/input.txt").readText().trim().lines()
        val part1 = Day11().part1(input)
        println("Result $part1")
        val part2 = Day11().part2(input)
        println("Result $part2")
    }
}