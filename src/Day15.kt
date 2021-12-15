import java.util.*
import kotlin.system.measureTimeMillis

fun main() {
    fun dijkstraShortestPath(
        riskLevelMatrix: List<List<Int>>,
        start: Pair<Int, Int> = Pair(0, 0),
        end: Pair<Int, Int> = Pair(riskLevelMatrix.first().size - 1, riskLevelMatrix.size - 1)
    ): Int {
        val toBeEvaluated = PriorityQueue<Pair<Position, Int>>(compareBy { (_, distance) -> distance })
        toBeEvaluated.add(start to 0)

        val visited = mutableSetOf<Position>()

        while (toBeEvaluated.isNotEmpty()) {
            val (position, totalRisk) = toBeEvaluated.poll()

            if (position !in visited) {
                if (position == end) return totalRisk

                visited.add(position)

                position.neighborsXY(riskLevelMatrix)
                    .forEach { toBeEvaluated.offer(it to riskLevelMatrix.get(it.x, it.y) + totalRisk) }
            }
        }
        error("didn't reach end")
    }

    fun part1(input: List<String>): Int {
        val riskLevelMatrix = input.map { it.toCharArray().map(Char::digitToInt) }

        return dijkstraShortestPath(riskLevelMatrix)
    }

    /**
     * This would be actually better with get calculating new risks on the fly inside shortest path
     * method... but i am sleepy and tired 😂 so there is just lazy huge matrix copy
     */
    fun List<List<Int>>.inflate(times: Int): List<List<Int>> {
        val sourceXsize = first().size
        val sourceYsize = size
        return List(sourceXsize * times) { ny ->
            List(sourceYsize * times) { nx ->
                val risk = get(nx % sourceXsize, ny % sourceYsize)
                ((risk + nx / sourceXsize + ny / sourceYsize) % 9).let { if (it == 0) 9 else it }
            }
        }
    }

    fun part2(input: List<String>): Int {
        val riskLevelMatrix = input.map { it.toCharArray().map(Char::digitToInt) }
        return dijkstraShortestPath(riskLevelMatrix.inflate(5))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("Day15")
    println(part1(input))

    measureTimeMillis {
        println(part2(input))
    }.also { println("time $it ms") }
}


