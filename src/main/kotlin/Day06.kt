import kotlin.collections.fold
import kotlin.collections.mutableListOf


class Day06 {
    fun part1(input: List<String>): Long {
        val parsed = input
            .map { it.split(" ").filter { splitted -> splitted.isNotEmpty() } }
            .transpose()
        return parsed.sumOf { cephalopodMaths(it) }
    }

    private fun cephalopodMaths(numbers: MutableList<String>): Long {
        val operationRaw = numbers.removeLast()
        val operationFunction = when (operationRaw){
            "+" -> { a: Long, b: Long -> a + b }
            "*" -> { a: Long, b: Long -> a * b }
            else -> throw NotImplementedError("Not implemented: $operationRaw")
        }
        return numbers.map { it.trim().toLong() }.reduce { acc, next -> operationFunction(acc, next) }
    }

    fun part2(input: List<String>): Long {
        val parsed = transposeItemrow(input as MutableList<String>)
        return parsed.sumOf { cephalopodMaths(it as MutableList<String>) }
    }

    private fun transposeItemrow(strings: MutableList<String>): List<List<String>> {
        val operations = strings.removeLast().split(" ").filterNot { it.isEmpty() }
        val folded = strings.fold(mutableListOf<String>()) { acc, next ->
            next.reversed().mapIndexed { idx, item ->
                if (acc.size <= idx) {
                    acc.add(item.toString())
                } else {
                    acc[idx] = acc[idx] + item
                }
            }
            acc
        }
        val perOperation = parseOperationColumn(folded)
        return operations.reversed().mapIndexed { idx, op ->  perOperation[idx] +op }
    }

    private fun parseOperationColumnRec(toParse: List<String>, parsed: MutableList<MutableList<String>>): MutableList<MutableList<String>> {
        return if(toParse.isEmpty()){
            parsed
        } else{
            val takeWhile = toParse.takeWhile { it.trim().isNotBlank() }
            parsed.add(takeWhile as MutableList<String>)
            parseOperationColumnRec(toParse.drop(takeWhile.size+1), parsed)
        }


    }

    private fun parseOperationColumn(folded: MutableList<String>): MutableList<MutableList<String>> {
        return parseOperationColumnRec(folded, mutableListOf())
    }
}

private fun List<List<String>>.transpose(): MutableList<MutableList<String>> {
    return fold(mutableListOf()) { acc, next ->
        next.mapIndexed { idx, item ->
            if (acc.size <= idx) {
                acc.add(mutableListOf(item))
            } else {
                acc[idx].add(item)
            }
        }
        acc
    }
}

