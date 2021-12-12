import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.map
import org.jetbrains.kotlinx.multik.ndarray.operations.mapMultiIndexed

fun main() {
    /**
     * these might be out of bounds!
     * */
    fun Pair<Int, Int>.adjacent() = listOf(
        x - 1 to y, x + 1 to y, x to y - 1, x to y + 1,
        x - 1 to y - 1, x + 1 to y - 1, x + 1 to y - 1, x + 1 to y + 1
    )

    fun executeFlashes(levels: NDArray<Int, D2>) {
        levels.mapMultiIndexed { (x, y), powerLevel ->
            if (powerLevel)
        }
    }

    fun part1(input: List<String>): Int {
        val energyLevels = input.map { string -> string.map { it.digitToInt() } }.toNDArray()

        repeat(100) {
            val increasedLevels = energyLevels.map { it + 1 }

        }
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 0)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
