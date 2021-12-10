fun main() {
    val openingClosingMap = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

    fun findIllegalCharactersInLines(input: List<String>) = input.map { inputCode ->
        //println("Checking: $inputCode")
        val openingCharsQue = mutableListOf<Char>()
        val illegalCharacters = mutableListOf<Char>()
        inputCode.forEach {
            if (openingClosingMap.containsKey(it)) {
                openingCharsQue.add(it)
            } else {
                val opening = openingCharsQue.removeLast()
                if (openingClosingMap[opening] != it) {
                  //println("Expected `${openingClosingMap[opening]}` but found `$it` instead.")
                    illegalCharacters.add(it)
                }
            }
        }
        illegalCharacters.toList()
    }

    fun part1(input: List<String>): Int {
        val scoringMap = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

        val illegalChars = findIllegalCharactersInLines(input)

        return illegalChars.mapNotNull { it.firstOrNull() }.sumOf { scoringMap.getValue(it) }
    }

    fun part2(input: List<String>): Long {
        val scoringMap = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

        val linesToAutocomplete = input.zip(findIllegalCharactersInLines(input))
            .filter { (_, illegalChars) -> illegalChars.isEmpty() }
            .map { (legalLines, _) -> legalLines }

        // this is a bit lazy 🤷‍ - but has less complexity
        val autoCompletions = linesToAutocomplete
            .map { inputCode ->
                val toComplete = inputCode.fold(emptyList<Char>()) { acc, c ->
                    if (openingClosingMap.containsKey(c)) acc + c else acc.dropLast(1)
                }

                toComplete.foldRight(emptyList<Char>()) { c, acc ->
                    acc + openingClosingMap.getValue(c)
                }
            }

        val sortedScores = autoCompletions.map {
            it.fold(0L) { acc, c -> acc * 5 + scoringMap.getValue(c) }
        }
            .sorted()
        return sortedScores[(autoCompletions.size - 1) / 2]

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("day10")
     println(part1(input))
     println(part2(input))
}
