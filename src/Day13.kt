import kotlin.math.abs

fun main() {

    fun parseXYs(input: List<String>) =
        input.takeWhile { it.firstOrNull()?.isDigit() ?: false }
            .map {
                val (x, y) = it.split(",")
                x.toInt() to y.toInt()
            }

    fun parseFoldInstructions(input: List<String>) =
        input.takeLastWhile { it.startsWith("fold") }
            .map {
                val (_, along, value) = requireNotNull(
                    Regex("fold along ([yx])=(\\d+)").matchEntire(it)?.groupValues
                )
                along to value.toInt()
            }

    fun List<Pair<String, Int>>.executeOnPaper(xy: List<Pair<Int, Int>>) = fold(xy) { acc, (along, foldCoordinate) ->
        when (along) {
            "x" -> acc.map { if (it.x < foldCoordinate) it else abs(it.x - (foldCoordinate * 2)) to it.y }
            "y" -> acc.map { if (it.y < foldCoordinate) it else it.x to abs(it.y - (foldCoordinate * 2)) }
            else -> error("unexpected fold $along")
        }
            .distinct()
    }

    fun List<Pair<Int, Int>>.print() {
        val width = maxOf { it.x }
        val height = maxOf { it.y }

        val printMatrix = Array(size = height + 1) {
            Array(size = width + 1) { ' ' }
        }

        forEach { printMatrix[it.y][it.x] = '■' }

        printMatrix.forEach { x ->
            x.forEach { print(it) }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        val xy = parseXYs(input)
        val folds = parseFoldInstructions(input)

        return folds.take(1).executeOnPaper(xy).count()
    }

    fun part2(input: List<String>) {
        val xy = parseXYs(input)
        val folds = parseFoldInstructions(input)

        folds.executeOnPaper(xy).print()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)

    val input = readInput("Day13")
    println(part1(input))
    part2(input)
}
