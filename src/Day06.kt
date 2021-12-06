fun main() {

    fun fishStatesCountsSequence(defaultCountState: List<Long>) = generateSequence(defaultCountState) { counts ->
        List(9) { index ->
            when (index) {
                6 -> counts[0] + counts[7]
                8 -> counts[0]
                else -> counts[index + 1]
            }
        }
    }

    fun part1(input: List<String>): Long {
        val fishList = input.first().split(',').map(String::toInt)
        val statesXcount = List(9) { index ->
            fishList.count { it == index }.toLong()
        }
        return fishStatesCountsSequence(statesXcount).elementAt(80).sum()
    }

    fun part2(input: List<String>): Long {
        val fishList = input.first().split(',').map(String::toInt)
        val statesXcount = List(9) { index ->
            fishList.count { it == index }.toLong()
        }
        return fishStatesCountsSequence(statesXcount).elementAt(256).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

private fun part1_INITIAL(input: List<String>): Int {
    data class LanternFish(val timer: Int) {
        fun dayPass(): List<LanternFish> = if (timer == 0) {
            listOf(LanternFish(6), LanternFish(8))
        } else {
            listOf(this.copy(timer = timer - 1))
        }
    }

    val fishList = input.first().split(',').map(String::toInt).map(::LanternFish)

    val fishySequence = generateSequence(fishList) { it.flatMap(LanternFish::dayPass) }

    return fishySequence.elementAt(80).count()
}
