fun main() {
    data class Entry(val uniqueSignalPatterns: List<Set<Char>>, val fourDigitOutputValue: List<Set<Char>>)

    fun parseInput(rawInput: List<String>) = rawInput
        .map {
            val (uniqueSignalPatterns, fourDigitOutputValues) = it.split(" | ")
            Entry(
                uniqueSignalPatterns.split(' ').map(String::toSet),
                fourDigitOutputValues.split(' ').map(String::toSet)
            )
        }

    fun part1(input: List<String>): Int {
        val parsed = parseInput(input)

        return parsed.flatMap { it.fourDigitOutputValue }
            .count {
                when (it.count()) {
                    2/*1*/, 3/*7*/, 4/*4*/, 7/*8*/ -> true
                    else -> false
                }
            }
    }

    fun part2(input: List<String>): Int {
        val parsed = parseInput(input)

        return parsed.map { entry ->
            val numbersTranslationArray = figureNumbers(entry.uniqueSignalPatterns)
            entry.fourDigitOutputValue.map { numbersTranslationArray.indexOf(it) }
                .reduce { acc, i -> acc * 10 + i }
        }
            .sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))

}

private fun figureNumbers(uniqueSignalPatterns: List<Set<Char>>): Array<Set<Char>> {
    uniqueSignalPatterns.toMutableList().let { mutablePatterns ->
        val d1 = mutablePatterns.removeAndGetFirst { it.count() == 2 }
        val d7 = mutablePatterns.removeAndGetFirst { it.count() == 3 }
        val d4 = mutablePatterns.removeAndGetFirst { it.count() == 4 }
        val d8 = mutablePatterns.removeAndGetFirst { it.count() == 7 }

        val length5 = mutablePatterns.removeAndGet { it.count() == 5 }.toMutableList()
        val length6 = mutablePatterns.removeAndGet { it.count() == 6 }.toMutableList()

        val d9 = length6.removeAndGetFirst { it.containsAll(d4) }
        val d6 = length6.removeAndGetFirst { it.containsAll(d9 - d1) }
        val d0 = length6.single()

        val d3 = length5.removeAndGetFirst { it.containsAll(d1) }
        val d5 = length5.removeAndGetFirst { it.containsAll(d4 - d1) }
        val d2 = length5.single()

        return arrayOf(d0, d1, d2, d3, d4, d5, d6, d7, d8, d9)
    }

}

inline fun <T> MutableList<T>.removeAndGetFirst(predicate: (T) -> Boolean): T {
    val value = first(predicate)
    remove(value)
    return value
}

inline fun <T> MutableList<T>.removeAndGet(predicate: (T) -> Boolean): List<T> {
    val value = filter(predicate)
    removeAll(value)
    return value
}
