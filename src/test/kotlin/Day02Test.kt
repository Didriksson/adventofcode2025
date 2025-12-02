import org.junit.jupiter.api.Assertions.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.Test

class Day02Test {

    @Test
    fun example1() {
        val exampleInput = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
        val day = Day02()
        val result1 = day.part1(exampleInput)
        assertEquals(1227775554, result1)
    }

    @Test
    fun example2() {
        val exampleInput = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
        val day = Day02()
        val result1 = day.part2(exampleInput)
        assertEquals(4174379265, result1)
    }

    @Test
    fun runPart1() {
        val input = Path("src/input.txt").readText()
        val part1 = Day02().part1(input)
        val part2 = Day02().part2(input)
        println("Result $part1")
        println("Result $part2")
    }

}