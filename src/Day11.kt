private typealias PowerLevels = List<List<Int>>

fun main() {

    fun tryToFlash(point: Pair<Int, Int>, levels: PowerLevels): PowerLevels {
        val currentPowerLevel = levels.get(point.x, point.y)
        if (currentPowerLevel < 10) return levels

        val afterPointFlash = levels.update(point.x, point.y) { 0 }

        return point.adjacent(afterPointFlash)
            .fold(afterPointFlash) { acc, adjacentPoint ->
                if (acc.get(adjacentPoint.x, adjacentPoint.y) == 0) {
                    acc
                } else {
                    tryToFlash(adjacentPoint, acc.update(adjacentPoint.x, adjacentPoint.y) { it + 1 })
                }
            }
    }

    fun executeOctopusesCycle(energyLevels: List<List<Int>>): List<List<Int>> {
        val raisedEnergyLevels = energyLevels.map2d { it + 1 }
        val afterFlash = raisedEnergyLevels.indices.flatMap { x ->
            raisedEnergyLevels.first().indices.map { y -> x to y }
        }
            .fold(raisedEnergyLevels) { acc, (x, y) ->
                tryToFlash(x to y, acc)
            }
        return afterFlash
    }

    fun part1(input: List<String>): Int {
        var energyLevels = input.map { string -> string.map { it.digitToInt() } }
        var flashes = 0
        repeat(100) { step ->
            val afterFlash = executeOctopusesCycle(energyLevels)
            flashes += afterFlash.sumOf { it.count { it == 0 } }
            energyLevels = afterFlash
        }
        return flashes
    }

    fun part2(input: List<String>): Int {
        var energyLevels = input.map { string -> string.map { it.digitToInt() } }
        var cycles = 0

        while (!energyLevels.all2d { it == 0 }) {
            val afterFlash = executeOctopusesCycle(energyLevels)
            cycles += 1
            energyLevels = afterFlash
        }
        return cycles
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
