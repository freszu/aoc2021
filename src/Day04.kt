fun main() {

    tailrec fun bingoWin(numbers: List<Int>, boards: List<BingoBoard>): Pair<BingoBoard, Int> {
        val currentNumber = numbers.first()
        val checkedBoards = boards.map { it.checkNumber(currentNumber) }
        return checkedBoards.firstOrNull(BingoBoard::isWinning)?.let { it to currentNumber }
            ?: bingoWin(numbers.drop(1), checkedBoards)
    }

    fun part1(input: List<String>): Int {
        val (winingBoard, winingNumber) = bingoWin(
            getWinningNumbers(input),
            createBingoBoards(input)
        )

        return winingBoard.score() * winingNumber
    }

    tailrec fun bingoLastToWin(numbers: List<Int>, boards: List<BingoBoard>): Pair<BingoBoard, Int> {
        val currentNumber = numbers.first()
        val checkedBoards = boards.map { it.checkNumber(currentNumber) }
        return if (checkedBoards.singleOrNull()?.isWinning() == true) {
            checkedBoards.single() to currentNumber
        } else {
            bingoLastToWin(numbers.drop(1), checkedBoards.filterNot(BingoBoard::isWinning))
        }
    }

    fun part2(input: List<String>): Int {
        val (loosingBoard, loosingNumber) = bingoLastToWin(
            getWinningNumbers(input),
            createBingoBoards(input)
        )

        return loosingBoard.score() * loosingNumber
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

sealed class BingoSpace(val score: Int) {
    data class Number(val value: Int) : BingoSpace(value)
    object Hit : BingoSpace(0)
}

private typealias BingoBoard = List<List<BingoSpace>>

private fun getWinningNumbers(rawInput: List<String>) = rawInput.first().split(',').map { it.toInt() }

private fun createBingoBoards(rawInput: List<String>) = rawInput.drop(1).chunked(6) { boardChunks ->
    boardChunks.drop(1) // empty line after each
        .map { row ->
            row.chunked(3)// each bingo space takes 3 chars
                .map { BingoSpace.Number(it.filterNot { char -> char == ' ' }.toInt()) }
        }
}

private fun BingoBoard.checkNumber(number: Int): List<List<BingoSpace>> = map { row ->
    row.map { space ->
        if (space is BingoSpace.Number && space.value == number) BingoSpace.Hit else space
    }
}

private fun BingoBoard.isWinning(): Boolean {
    forEach { row -> if (row.all { it is BingoSpace.Hit }) return true }
    transposeMatrix().forEach { row -> if (row.all { it is BingoSpace.Hit }) return true }
    return false
}

private fun BingoBoard.score() = fold(0) { rowsAcc, rows ->
    rowsAcc + rows.fold(0) { columnAcc, bingoSpace -> columnAcc + bingoSpace.score }
}
