fun main() {
    fun part1(input: List<String>): Int {
        val polymerTemplate = input.first()
        val pairInsertionRules = input.drop(2).associate {
            val (a, b) = it.split(" -> ")
            a to b
        }

        val after10 = (0 until 10).fold(polymerTemplate) { acc, _ ->
            acc.fold("") { acc, b ->
                val a = acc.lastOrNull()
                if (a == null) {
                    "$b"
                } else {
                    val string = "$a$b"
                    val insertion = pairInsertionRules[string]
                    if (insertion == null) {
                        acc + b
                    } else {
                        acc + "$insertion$b"
                    }
                }
            }
        }

        val charCounts = after10.groupingBy { it }.eachCount()

        return charCounts.maxOf { it.value } - charCounts.minOf { it.value }
    }

    fun part2(input: List<String>): Long {
        val polymerTemplate = input.first()
        val pairInsertionRules = input.drop(2).associate {
            val (a, b) = it.split(" -> ")
            a to b
        }

        val singleCount = polymerTemplate.groupingBy { "$it" }.eachCount()
            .mapValues { it.value.toLong() }// int is not enough later
            .toMutableMap()
        val pairsCount = polymerTemplate.zipWithNext { a, b -> "$a$b" }
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() } //ints is not enough later
            .toMutableMap()

        repeat(40) {
            pairsCount.filter { (_, count) -> count > 0 }
                .forEach { (pair, count) ->
                    val insertion = pairInsertionRules[pair]
                    if (insertion != null) {
                        pairsCount.merge(pair.first() + insertion, count, Long::plus)
                        pairsCount.merge(insertion + pair.last(), count, Long::plus)
                        pairsCount.merge(pair, count, Long::minus)

                        singleCount.merge(insertion, count, Long::plus)
                    }

                }
        }

        return singleCount.maxOf { it.value } - singleCount.minOf { it.value }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
