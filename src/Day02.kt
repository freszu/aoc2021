fun main() {
    fun part1(input: List<String>): Int {
        val (x, y) = input.fold(0 to 0) { (x, y), command ->
            val value = command.last().digitToInt()
            when {
                command.startsWith("forward") -> (x + value) to y
                command.startsWith("down") -> x to (y + value)
                command.startsWith("up") -> x to (y - value)
                else -> error("unexpected command $command")
            }
        }

        return x * y
    }

    fun part2(input: List<String>): Int {
        val (x, y, aim) = input.fold(Triple(0, 0, 0)) { (x, y, aim), command ->
            val value = command.last().digitToInt()
            when {
                command.startsWith("forward") -> Triple(x + value, y + (aim * value), aim)
                command.startsWith("down") -> Triple(x, y, aim + value)
                command.startsWith("up") -> Triple(x, y, aim - value)
                else -> error("unexpected command $command")
            }
        }

        return x * y
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
