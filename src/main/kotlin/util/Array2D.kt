package util

@Suppress("UNCHECKED_CAST")
class Array2D<T>(val rows: Int, val columns: Int, val array: Array<Array<T>>) {
    companion object {

        inline operator fun <reified T> invoke() = Array2D(0, 0, Array(0) { emptyArray<T>() })

        inline operator fun <reified T> invoke(xWidth: Int, yWidth: Int) =
            Array2D(xWidth, yWidth, Array(xWidth) { arrayOfNulls<T>(yWidth) })

        inline operator fun <reified T> invoke(xWidth: Int, yWidth: Int, operator: (Int, Int) -> (T)): Array2D<T> {
            val array = Array(xWidth) { x ->
                Array(yWidth) { operator(x, it) }
            }
            return Array2D(xWidth, yWidth, array)
        }
    }

    operator fun get(x: Int, y: Int): T {
        return array[x][y]
    }

    operator fun set(x: Int, y: Int, value: T) {
        array[x][y] = value
    }

    fun rotateArrayClockwise(): Array2D<T> {
        val rotatedArray = Array2D<Any?>(columns, rows) { x, y -> array[x][y] } as Array2D<T>
        for (i in 0..<columns) {
            for (j in rows - 1 downTo 0) {
                rotatedArray[i, rows - j - 1] = array[j][i]
            }
        }
        return rotatedArray
    }

    fun rotateArrayCounterClockwise(): Array2D<T> {
        val rotatedArray = Array2D<Any?>(columns, rows) { x, y -> array[x][y] } as Array2D<T>
        for (i in columns - 1 downTo 0) {
            for (j in 0..<rows) {
                rotatedArray[columns - i - 1, j] = array[j][i]
            }
        }
        return rotatedArray
    }

    inline fun forEach(operation: (T) -> Unit) {
        array.forEach { row -> row.forEach { operation.invoke(it) } }
    }

    inline fun forEachIndexed(operation: (x: Int, y: Int, T) -> Unit) {
        array.forEachIndexed { x, p -> p.forEachIndexed { y, t -> operation.invoke(x, y, t) } }
    }

    fun clone(): Array2D<T> {
        return Array2D<Any?>(rows, columns) { x, y -> array[x][y] } as Array2D<T>
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in 0..<rows) {
            for (j in 0..<columns) {
                sb.append(array[i][j])
                if (j < (columns - 1)) sb.append(" ")
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}
