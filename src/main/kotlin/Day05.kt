import java.math.BigInteger

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day05("day05input.txt").run()
}

class Day05(fileName: String) : BaseDay(fileName) {
    override fun runPart1(): BigInteger {
        val almanac = readAlmanac()
        return readAlmanac().seeds.minOfOrNull { almanac.mapInput(it) }!!
    }

    override fun runPart2(): BigInteger {
        val almanac = readAlmanac()
        var i = 0
        val minimums = mutableListOf<BigInteger>()
        println("Checking ${almanac.seeds.size / 2} pairs - calculation will take a while")
        almanac.seeds.chunked(2).parallelStream().forEach { seedPair ->
            minimums.add(calculateMin(++i, seedPair, almanac))
        }

        return minimums.min()
    }

    private fun calculateMin(
        index: Int,
        seedPair: List<BigInteger>,
        almanac: Almanac
    ): BigInteger {
        var min: BigInteger = 0.toBigInteger()
        println("Pair $index starting")

        var seed = seedPair[0]
        while (seed <= (seedPair[0] + seedPair[1])) {
            val location = almanac.mapInput(seed)
            if (min == 0.toBigInteger() || location < min) {
                min = location
            }
            seed += 1.toBigInteger()
            //val percentage = (seed - seedPair[0]) * 100.toBigInteger() / (seedPair[1])
            //print("Pair ${index} - ${percentage}%\r")
        }
        println("Pair $index done")
        return min
    }

    fun readAlmanac(): Almanac {
        return Almanac.from(lines)
    }

    data class Almanac(val seeds: List<BigInteger>, val mappings: List<Mapping>) {
        fun mapInput(i: BigInteger): BigInteger {
            var result = i
            for (mapping in mappings) {
                result = mapping.mapInput(result)
            }
            return result
        }

        companion object Factory {
            fun from(lines: List<String>): Almanac {
                val seedLineElements = lines[0].split(" ")
                val seeds = seedLineElements.subList(1, seedLineElements.size).map { it.toBigInteger() }

                val mappings = mutableListOf<Mapping>()

                var startMapping = 2
                var endMapping = 3
                while (endMapping < lines.size) {
                    if (lines[endMapping].trim() == "") {
                        mappings += Mapping.from(lines.subList(startMapping, endMapping))
                        startMapping = endMapping + 1
                    }
                    endMapping += 1
                }
                mappings += Mapping.from(lines.subList(startMapping, endMapping))

                return Almanac(seeds, mappings)
            }
        }
    }

    data class Mapping(val from: String, val to: String, val maps: List<GameMap>) {
        fun mapInput(i: BigInteger): BigInteger {
            val map = maps.find { it.sourceRangeStart <= i && it.sourceRangeStart + it.rangeLength > i }
            return if (map != null) {
                map.destinationRangeStart + (i - map.sourceRangeStart)
            } else {
                i
            }
        }

        companion object Factory {
            fun from(lines: List<String>): Mapping {
                val name = lines[0].split(" ")[0]
                var i = 1
                val maps = mutableListOf<GameMap>()
                while (i < lines.size) {
                    maps += GameMap.from(lines[i])
                    i += 1
                }

                return Mapping(name.split("-")[0], name.split("-")[2], maps)
            }
        }
    }

    data class GameMap(
        val destinationRangeStart: BigInteger,
        val sourceRangeStart: BigInteger,
        val rangeLength: BigInteger
    ) {
        companion object Factory {
            fun from(line1: String): GameMap {
                return GameMap(
                    line1.split(" ")[0].toBigInteger(),
                    line1.split(" ")[1].toBigInteger(),
                    line1.split(" ")[2].toBigInteger()
                )
            }
        }
    }
}
