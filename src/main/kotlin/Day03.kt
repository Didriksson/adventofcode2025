import kotlin.text.mapIndexed

class Day03 {

    fun part1(input: List<String>): Long {
        val batteries = input.map { findSuperBattery( 2, it) }
        return batteries.sum()
    }

    fun part2(input: List<String>): Long {
        val batteries = input.map { findSuperBattery( 12, it) }
        return batteries.sum()
    }

    private fun findHighest(batteryarray: String): Pair<Int, Long> {
        val maxBy = batteryarray.mapIndexed { index, battery -> Pair(index, battery.digitToInt().toLong()) }.maxBy { it.second }
        return maxBy
    }

    private fun findSuperBattery(size: Int, batteryarray: String): Long {
        return if(size == 1){
            findHighest(batteryarray).second
        } else{
            val highest = findHighest(batteryarray.dropLast(size-1))
            return (highest.second.toString() + findSuperBattery(size-1, batteryarray.drop(highest.first+1))).toLong()
        }

    }

}