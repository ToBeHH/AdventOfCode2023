package util

@Suppress("UNCHECKED_CAST", "unused", "MemberVisibilityCanBePrivate")
class Array2D<T>(val columns: Int, val rows: Int, val array: Array<Array<T>>) {
    val columnIndices: IntRange = 0..<columns
    val rowIndices: IntRange = 0..<rows

    companion object {

        inline operator fun <reified T> invoke() = Array2D(0, 0, Array(0) { emptyArray<T>() })

        inline operator fun <reified T> invoke(xWidth: Int, yWidth: Int) =
            Array2D(xWidth, yWidth, Array(xWidth) { arrayOfNulls<T>(yWidth) })

        inline operator fun <reified T> invoke(xWidth: Int, yWidth: Int, operator: (Int, Int) -> (T)): Array2D<T> {
            val array = Array(yWidth) { y ->
                Array(xWidth) { operator(it, y) }
            }
            return Array2D(xWidth, yWidth, array)
        }
    }

    operator fun get(x: Int, y: Int): T {
        return array[y][x]
    }

    operator fun get(pair: Pair<Int, Int>): T {
        return array[pair.second][pair.first]
    }

    operator fun set(x: Int, y: Int, value: T) {
        array[y][x] = value
    }

    operator fun set(pair: Pair<Int, Int>, value: T) {
        array[pair.second][pair.first] = value
    }

    inline fun forEach(operation: (T) -> Unit) {
        array.forEach { row -> row.forEach { operation.invoke(it) } }
    }

    inline fun forEachIndexed(operation: (x: Int, y: Int, T) -> Unit) {
        array.forEachIndexed { y, p -> p.forEachIndexed { x, t -> operation.invoke(x, y, t) } }
    }

    inline fun forEachIndexed(operation: (coordinates: Pair<Int, Int>, T) -> Unit) {
        array.forEachIndexed { y, p -> p.forEachIndexed { x, t -> operation.invoke(Pair(x, y), t) } }
    }

    inline fun map(operation: (T) -> Unit): Array2D<Unit> {
        return Array2D<Unit>(rows, columns) { x, y -> operation.invoke(array[x][y]) }
    }

    inline fun mapIndexed(operation: (x: Int, y: Int, T) -> Unit): Array2D<Unit> {
        return Array2D<Unit>(rows, columns) { x, y -> operation.invoke(x, y, array[x][y]) }
    }

    inline fun mapIndexed(operation: (coordinates: Pair<Int, Int>, T) -> Unit): Array2D<Unit> {
        return Array2D<Unit>(rows, columns) { x, y -> operation.invoke(Pair(x, y), array[x][y]) }
    }

    inline fun sumOf(operation: (T) -> Int): Int {
        var sum = 0
        array.forEach { row -> row.forEach { sum += operation.invoke(it) } }
        return sum
    }

    inline fun sumOfIndexed(operation: (x: Int, y: Int, T) -> Int): Int {
        var sum = 0
        array.forEachIndexed { x, row -> row.forEachIndexed { y, it -> sum += operation.invoke(x, y, it) } }
        return sum
    }

    fun clone(): Array2D<T> {
        return Array2D<Any?>(rows, columns) { x, y -> array[x][y] } as Array2D<T>
    }

    fun rotateArrayClockwise(): Array2D<T> {
        val rotatedArray = Array2D<Any?>(columns, rows) as Array2D<T>
        for (i in 0..<columns) {
            for (j in rows - 1 downTo 0) {
                rotatedArray[i, rows - j - 1] = array[j][i]
            }
        }
        return rotatedArray
    }

    fun rotateArrayCounterClockwise(): Array2D<T> {
        val rotatedArray = Array2D<Any?>(columns, rows) as Array2D<T>
        for (i in columns - 1 downTo 0) {
            for (j in 0..<rows) {
                rotatedArray[columns - i - 1, j] = array[j][i]
            }
        }
        return rotatedArray
    }

    override fun toString(): String {
        return toString({ it.toString() }, " ")
    }

    fun toString(operation: (T) -> String, separator: String): String {
        val sb = StringBuilder()
        for (y in rowIndices) {
            for (x in columnIndices) {
                sb.append(operation(array[y][x]))
                if (x < (columns - 1)) sb.append(separator)
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Array2D<*>) return false
        if (other.rows != rows || other.columns != columns) return false
        for (i in rowIndices) {
            for (j in columnIndices) {
                if (other[i, j] != array[i][j]) return false
            }
        }
        return true
    }

    override fun hashCode(): Int {
        var result = rows
        result = 31 * result + columns
        result = 31 * result + array.contentDeepHashCode()
        return result
    }
}
