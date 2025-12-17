class Day12 {
    fun part1(input: List<String>): Int {
        val parse = input.parse()
        return parse.regions.count { checkForRegion(it, parse.shapes) }
    }
    val memo = mutableSetOf<State>()

    data class State(
        val area: String,
        val shapes: List<Int>
    )
    fun checkForRegion(region: Region, shapes: List<Shape>): Boolean {
        memo.clear()
        val shapesToPlace = mutableListOf<Shape>()
        region.shapeidx.forEachIndexed { index, count ->
            repeat(count) { shapesToPlace.add(shapes[index]) }
        }
        shapesToPlace.sortByDescending { it.points.size }
        return canFit(region, shapesToPlace)
    }
    fun canFit(region: Region, shapes: List<Shape>): Boolean {
        if (shapes.isEmpty()) return true

        val state = State(region.areaHash(), shapesSignature(shapes))
        if (!memo.add(state)) return false

        val remainingAreaNeeded = shapes.sumOf { it.points.size }
        val currentEmptyCells = region.area.sumOf { row -> row.count { !it } }
        if (remainingAreaNeeded > currentEmptyCells) return false

        val shape = shapes.first()

        val remainingShapes = shapes.drop(1)

        for (nextRegion in allFitInArea(shape, region)) {
            if (canFit(nextRegion, remainingShapes)) {
                return true
            }
        }

        return false
    }



    private fun allFitInArea(shape: Shape, region: Region): Sequence<Region> {
        val variants = shape.getAllVariants()

        return variants.asSequence().flatMap { variant ->
            sequence {
                for (y in 0 until region.height) {
                    for (x in 0 until region.width) {
                        if (region.tryPlace(x, y, variant)) {
                            yield(region.place(x, y, variant))
                        }
                    }
                }
            }
        }
    }

    private fun List<String>.parse(): ShapesAndRegions {
        val shapesRaw = takeWhile { !it.contains("x") }
        val regionsRaw = drop(shapesRaw.size)

        fun parseShapeUntilEmpty(input: List<String>): List<Shape> {
            if (input.isEmpty()) {
                return listOf()
            } else {
                val toParse = input.takeWhile { it != "" }
                val rest = input.drop(toParse.size + 1)
                return listOf(parseShape(toParse)) + parseShapeUntilEmpty(rest)
            }
        }

        val shapes = parseShapeUntilEmpty(shapesRaw)
        val regions = regionsRaw.map { parseRegion(it) }
        return ShapesAndRegions(shapes, regions)
    }

    private fun parseRegion(it: String): Region {
        val split = it.replace(":", "").split(" ")
        val (x, y) = split[0].split("x")
        val idx = split.drop(1).map { it.toInt() }
        return Region(x.toInt(), y.toInt(), idx)

    }

    private fun parseShape(shapeRaw: List<String>): Shape {
        val coords =
            shapeRaw.drop(1).mapIndexed { y, row -> row.mapIndexed { x, it -> if (it == '#') Pair(x, y) else null } }
                .flatMap { it }.filterNotNull()
        return Shape(coords.toSet())
    }


    data class ShapesAndRegions(val shapes: List<Shape>, val regions: List<Region>)

    data class Shape(val points: Set<Pair<Int, Int>>) {

        fun rotated(): Shape {
            return Shape(points.map { Pair(-it.second, it.first) }.toSet().normalize())
        }

        fun flipped(): Shape {
            return Shape(points.map { Pair(-it.first, it.second) }.toSet().normalize())
        }

        private fun Set<Pair<Int, Int>>.normalize(): Set<Pair<Int, Int>> {
            val minX = this.minOf { it.first }
            val minY = this.minOf { it.second }
            return this.map { Pair(it.first - minX, it.second - minY) }.toSet()
        }

        fun getAllVariants(): MutableSet<Shape> {
            val variants = mutableSetOf<Shape>()
            var current = this

            repeat(4) {
                variants.add(current)
                current = current.rotated()
            }

            current = this.flipped()
            repeat(4) {
                variants.add(current)
                current = current.rotated()
            }

            return variants
        }
    }
    fun shapesSignature(shapes: List<Shape>): List<Int> =
        shapes
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedBy { entry ->
                entry.key.points
                    .sortedWith(compareBy({ it.second }, { it.first }))
                    .joinToString(";") { "${it.first},${it.second}" }
            }
            .map { it.value }

    data class Region(
        val width: Int,
        val height: Int,
        val shapeidx: List<Int>,
        val area: List<BooleanArray> = List(height) { BooleanArray(width) }
    ) {
        fun tryPlace(x: Int, y: Int, shape: Shape): Boolean {
            return shape.points.all { p ->
                val tx = x + p.first
                val ty = y + p.second
                tx in 0 until width && ty in 0 until height && !area[ty][tx]
            }
        }

        fun place(x: Int, y: Int, shape: Shape): Region {
            val newArea = area.map { it.copyOf() }
            shape.points.forEach { p ->
                newArea[y + p.second][x + p.first] = true
            }
            return this.copy(area = newArea)
        }

        fun areaHash(): String =
            buildString(width * height) {
                for (row in area) {
                    for (cell in row) {
                        append(if (cell) '1' else '0')
                    }
                }
            }


    }


}
