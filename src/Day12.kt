fun main() {

    fun String.isUpperCase() = all(Char::isUpperCase)

    fun String.isLowerCase() = all(Char::isLowerCase)

    fun buildGraphFromInput(input: List<String>): Map<String, Set<String>> = input.flatMap {
        val (a, b) = it.split('-')
        listOf(a to b, b to a)
    }
        .groupingBy { it.first }
        .aggregate { _, accumulator: MutableSet<String>?, (_, to), _ ->
            (accumulator ?: mutableSetOf()).apply { add(to) }
        }

    fun List<String>.didVisitAnySmallCaveTwice(): Boolean {
        val lowercases = filter { it.isLowerCase() }
        return lowercases.toSet().size == lowercases.size
    }

    fun findAllPaths(
        graph: Map<String, Set<String>>,
        node: String,
        followedPath: List<String>,
        allowDoubleSingleCaveVisit: Boolean = false
    ): List<List<String>> {
        val path = followedPath + node

        return if (node == "end") {
            listOf(path)
        } else {
            graph.getValue(node)
                .filterNot { it == "start" }
                .filter {
                    it !in path || it.isUpperCase() ||
                            (allowDoubleSingleCaveVisit && path.didVisitAnySmallCaveTwice())
                }
                .fold(emptyList()) { acc, s ->
                    acc + findAllPaths(graph, s, path, allowDoubleSingleCaveVisit)
                }
        }
    }

    fun part1(input: List<String>): Int {
        val graph = buildGraphFromInput(input)

        return findAllPaths(graph, "start", emptyList()).count()
    }

    fun part2(input: List<String>): Int {
        val graph = buildGraphFromInput(input)

        return findAllPaths(graph, "start", emptyList(), true).count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))

}

