fun main() {

    fun mostCommonBitInRow(column: Int, rows: List<Int>): Int {
        val indexBitSum = rows.sumOf { it.getBit(column) }
        return if (indexBitSum >= rows.count() - indexBitSum) 1 else 0
    }

    fun leastCommonBitInRow(column: Int, rows: List<Int>) = mostCommonBitInRow(column, rows) xor 1

    fun part1(input: List<String>): Int {
        val bitsCount = input.first().length
        val inputBits = input.map { it.toInt(2) }

        val mostCommonBitsBin = (bitsCount - 1 downTo 0).map { index ->
            mostCommonBitInRow(index, inputBits)
        }.reduce { acc, i -> (acc shl 1) or i }

        val leasCommonBitsBin = mostCommonBitsBin xor ((1 shl bitsCount) - 1)/*1s bit mask*/
        return (mostCommonBitsBin * leasCommonBitsBin)
    }

    tailrec fun ratingCalculation(row: Int, rows: List<Int>, leastCommon: Boolean = false): Int {
        val bit = if (leastCommon) leastCommonBitInRow(row, rows) else mostCommonBitInRow(row, rows)
        val filteredRows = rows.filter { it.getBit(row) == bit }
        return filteredRows.singleOrNull() ?: ratingCalculation(row - 1, filteredRows, leastCommon)
    }

    fun part2(input: List<String>): Int {
        val bitsCount = input.first().length
        val inputBits = input.map { it.toInt(2) }

        val o2rating = ratingCalculation(bitsCount - 1, inputBits)
        val co2rating = ratingCalculation(bitsCount - 1, inputBits, leastCommon = true)
        return (o2rating * co2rating)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun Int.getBit(position: Int) = (this shr position) and 1

