import org.junit.jupiter.api.Assertions.assertEquals

class Day05Test {

    @org.junit.jupiter.api.Test
    fun testReadAlmanac() {
        // extract numbers in same line
        val d = Day05("day05sample.txt")
        val almanac = d.readAlmanac()
        assertEquals(listOf(79, 14, 55, 13), almanac.seeds)
        assertEquals(7, almanac.mappings.size)
        assertEquals(2, almanac.mappings[0].maps.size)
        assertEquals(Day05.GameMap(50.toBigInteger(), 98.toBigInteger(), 2.toBigInteger()), almanac.mappings[0].maps[0])
    }

    @org.junit.jupiter.api.Test
    fun testMapping() {
        val d = Day05("day05sample.txt")
        val almanac = d.readAlmanac()
        assertEquals(81.toBigInteger(), almanac.mappings[0].mapInput(79.toBigInteger()))
        assertEquals(14.toBigInteger(), almanac.mappings[0].mapInput(14.toBigInteger()))
        assertEquals(57.toBigInteger(), almanac.mappings[0].mapInput(55.toBigInteger()))
        assertEquals(13.toBigInteger(), almanac.mappings[0].mapInput(13.toBigInteger()))
    }
}