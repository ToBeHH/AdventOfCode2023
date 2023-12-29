import org.junit.jupiter.api.Assertions.*
import util.Point3D

class Day22Test {

    @org.junit.jupiter.api.Test
    fun testReadData() {
        val d = Day22("day22sample.txt")
        val bricks = d.getBricks()
        assertEquals(7, bricks.size)
        assertTrue(bricks[0].isOnZLevel(1))
        assertFalse(bricks[0].isOnZLevel(2))

        assertTrue(bricks[0].isOverlapping(bricks[1].minusZ(1)))
        assertFalse(bricks[1].isOverlapping(bricks[2].minusZ(1)))
        assertTrue(bricks[2].isOverlapping(bricks[3].minusZ(1)))
        //assertTrue(bricks[5].isOverlapping(bricks[6].minusZ(1)))

        val bricksAfterGravity = d.letBricksFallDown(bricks)
        assertEquals(6, d.getMaxZLevel(bricksAfterGravity))
        assertEquals(
            listOf(
                Day22.Brick(Point3D(1, 0, 1), Point3D(1, 2, 1)),
                Day22.Brick(Point3D(0, 0, 2), Point3D(2, 0, 2)),
                Day22.Brick(Point3D(0, 2, 2), Point3D(2, 2, 2)),
                Day22.Brick(Point3D(0, 0, 3), Point3D(0, 2, 3)),
                Day22.Brick(Point3D(2, 0, 3), Point3D(2, 2, 3)),
                Day22.Brick(Point3D(0, 1, 4), Point3D(2, 1, 4)),
                Day22.Brick(Point3D(1, 1, 5), Point3D(1, 1, 6)),
            ), bricksAfterGravity
        )
    }

    @org.junit.jupiter.api.Test
    fun testConvertNumberToLetter() {
        val d = Day22("day22sample.txt")
        assertEquals("A", d.convertNumberToLetter(0))
        assertEquals("Z", d.convertNumberToLetter(25))
        assertEquals("AA", d.convertNumberToLetter(26))
    }

    @org.junit.jupiter.api.Test
    fun testPart1() {
        val d = Day22("day22sample.txt")
        assertEquals(5, d.runPart1())
    }

    @org.junit.jupiter.api.Test
    fun testPart2() {
        val d = Day22("day22sample.txt")

        assertEquals(7, d.runPart2())
    }

}