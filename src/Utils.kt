import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun <T> List<List<T>>.transposeMatrix() = List(this.first().size) { i -> List(this.size) { j -> this[j][i] } }

/**
 * .. but doesn't care about order and figures it by itself
 */
infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

val Pair<Int, Int>.x: Int
    get() = this.first

val Pair<Int, Int>.y: Int
    get() = this.second

inline fun <T, R> List<List<T>>.map2d(transform: (T) -> R) = this.map { it.map(transform) }

inline fun <T, R> List<List<T>>.map2dIndexed(transform: (x: Int, y: Int, T) -> R) = mapIndexed { y, rows ->
    rows.mapIndexed { x, t -> transform(x, y, t) }
}

inline fun <T> List<List<T>>.update(x: Int, y: Int, transform: (T) -> T) = map2dIndexed { ind1M, ind2M, t ->
    if (x == ind1M && y == ind2M) transform(t) else t
}

fun <T> List<List<T>>.get(x: Int, y: Int) = this[y][x]

inline fun <T> List<List<T>>.all2d(predicate: (T) -> Boolean): Boolean = all { it.all(predicate) }

fun <T> List<List<T>>.nicePrint() = joinToString("\n")

/**
 * All adjacent including diagonals
 */
fun <T> Pair<Int, Int>.adjacent(inMatrix: List<List<T>>) = listOf(
    x - 1 to y, x + 1 to y, x to y - 1, x to y + 1,
    x - 1 to y - 1, x + 1 to y - 1, x - 1 to y + 1, x + 1 to y + 1
).filter { (x, y) ->
    x in inMatrix.indices && y in inMatrix.first().indices
}
