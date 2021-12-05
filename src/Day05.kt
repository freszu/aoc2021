fun main() {

    fun parseLines(rawInput: List<String>) = rawInput.map {
        val (x1, y1, x2, y2) = requireNotNull(
            Regex("""(\d+),(\d+) -> (\d+),(\d+)""").matchEntire(it)
        ).groupValues.drop(1).map(String::toInt)

        (x1 to y1) to (x2 to y2)
    }

    fun part1(input: List<String>): Int {
        val diagramMap = parseLines(input)
            .fold(emptyMap<Pair<Int, Int>, Int>()) { drawnPoints, (p1, p2) ->
                drawnPoints.toMutableMap().apply {
                    when {
                        p1.x == p2.x -> (p1.y toward p2.y).map { y -> Pair(p1.x, y) }
                        p1.y == p2.y -> (p1.x toward p2.x).map { x -> Pair(x, p1.y) }
                        else -> null // ignore diagonals
                    }?.forEach { merge(it, 1, Int::plus) }
                }
            }

        return diagramMap.count { (_, pointHitCount) -> pointHitCount >= 2 }
    }

    fun part2(input: List<String>): Int {
        val diagramMap = parseLines(input)
            .fold(emptyMap<Pair<Int, Int>, Int>()) { drawnPoints, (p1, p2) ->
                drawnPoints.toMutableMap().apply {
                    when {
                        p1.x == p2.x -> (p1.y toward p2.y).map { y -> Pair(p1.x, y) }
                        p1.y == p2.y -> (p1.x toward p2.x).map { x -> Pair(x, p1.y) }
                        else -> (p1.x toward p2.x).zip(p1.y toward p2.y)
                    }.forEach { merge(it, 1, Int::plus) }
                }
            }

        return diagramMap.count { (_, pointHitCount) -> pointHitCount >= 2 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))

}
