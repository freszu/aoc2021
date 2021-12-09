fun main() {

    /**
     * these might be out of bounds!
     * */
    fun Pair<Int, Int>.neighbours() = listOf(x - 1 to y, x + 1 to y, x to y - 1, x to y + 1)

    fun createHeightMap(rawInput: List<String>): List<List<Int>> =
        rawInput.map { it.toCharArray().map { it.digitToInt() } }

    fun findLowPoints(heightMap: List<List<Int>>) = buildList<Pair<Int, Int>> {
        heightMap.forEachIndexed { x, ints ->
            ints.forEachIndexed { y, height ->
                val lowestNeighbour = (x to y).neighbours().mapNotNull { (nx, ny) ->
                    heightMap.getOrNull(nx)?.getOrNull(ny)
                }
                    .minOf { it }

                if (height < lowestNeighbour) add(x to y)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val heightMap = createHeightMap(input)
        val lowPoints = findLowPoints(heightMap)

        return lowPoints.sumOf { (x, y) -> heightMap[x][y] + 1 }
    }

    fun calculateBasinSize(point: Pair<Int, Int>, heightMap: List<List<Int>>): Int {
        val visited = mutableSetOf(point)
        val queue = mutableListOf(point)
        while (queue.isNotEmpty()) {
            val newNeighbors = queue.removeFirst()
                .neighbours()
                .filterNot { visited.contains(it) }
                .filter { (x, y) ->
                    val pointHeight = heightMap.getOrNull(x)?.getOrNull(y)
                    pointHeight != null && pointHeight < 9
                }
            visited.addAll(newNeighbors)
            queue.addAll(newNeighbors)
        }
        return visited.size
    }

    fun part2(input: List<String>): Int {
        val heightMap = createHeightMap(input)
        return findLowPoints(heightMap)
            .map { calculateBasinSize(it, heightMap) }
            .sortedDescending()
            .take(3)
            .reduce(Int::times)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
