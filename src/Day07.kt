import kotlin.math.abs

fun main() {
    fun parseCrabsPositions(rawInput: List<String>) = rawInput.single().split(",").map(String::toInt)

    fun part1(input: List<String>): Int {
        val crabPositions = parseCrabsPositions(input)
        return (crabPositions.minOf { it }..crabPositions.maxOf { it })
            .map { position -> crabPositions.sumOf { abs(it - position) } }
            .minOf { it }
    }

    fun part2(input: List<String>): Int {
        val crabPositions = parseCrabsPositions(input)
        return (crabPositions.minOf { it }..crabPositions.maxOf { it })
            .map { position ->
                crabPositions.sumOf {
                    val absDiff = abs(it - position)
                    (absDiff * (absDiff + 1)) / 2
                }
            }
            .minOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
