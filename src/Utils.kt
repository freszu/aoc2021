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
