/**
 * Main method to read the file and give the result
 */
fun main() {
    // read file sample.txt
    val lines = object {}.javaClass.getResourceAsStream("/day03input.txt")?.bufferedReader()?.readLines()
    var sumPart1 = 0
    var sumPart2 = 0
    if (lines != null) {
        // sum up results of all lines
        sumPart1 = findNumbers(lines).sum()
        sumPart2 = findGears(lines).sum()
    }
    println("Part 1: $sumPart1")
    println("Part 2: $sumPart2")
}

fun findNumbers(lines: List<String>): List<Int> {
    val result = mutableListOf<Int>()

    val touchedNumbers: Array<BooleanArray> = Array(lines.size) { BooleanArray(lines[0].length) }

    for (lineIndex in lines.indices) {
        val symbolIndices = findSymbols(lines[lineIndex])
        for (symbolIndex in symbolIndices) {
            val temp = findNumbersAdjacentToSymbol(lines, symbolIndex, lineIndex, touchedNumbers)
            result.addAll(temp)
        }
    }

    return result
}

fun findGears(lines: List<String>): List<Int> {
    val result = mutableListOf<Int>()

    val touchedNumbers: Array<BooleanArray> = Array(lines.size) { BooleanArray(lines[0].length) }

    for (lineIndex in lines.indices) {
        val symbolIndices = findGearsSymbols(lines[lineIndex])
        for (symbolIndex in symbolIndices) {
            val gearNumbers = findNumbersAdjacentToSymbol(lines, symbolIndex, lineIndex, touchedNumbers)
            if (gearNumbers.size == 2) {
                result.add(gearNumbers[0] * gearNumbers[1])
            }
        }
    }

    return result
}

private fun findNumbersAdjacentToSymbol(
    lines: List<String>,
    symbolIndex: Int,
    lineIndex: Int,
    touchedNumbers: Array<BooleanArray>
): MutableList<Int> {
    val result = mutableListOf<Int>()
    val maxLineLength = lines[lineIndex].length - 1
    val maxNumberOfLines = lines.size - 1

    // check horizontally
    if (symbolIndex > 0 && lines[lineIndex][symbolIndex - 1].isDigit()) {
        result.add(extractNumber(lines, lineIndex, symbolIndex - 1, touchedNumbers))
    }
    if (symbolIndex < maxLineLength && lines[lineIndex][symbolIndex + 1].isDigit()) {
        result.add(extractNumber(lines, lineIndex, symbolIndex + 1, touchedNumbers))
    }
    // check vertically
    if (lineIndex > 0 && lines[lineIndex - 1][symbolIndex].isDigit()) {
        result.add(extractNumber(lines, lineIndex - 1, symbolIndex, touchedNumbers))
    }
    if (lineIndex < maxNumberOfLines && lines[lineIndex + 1][symbolIndex].isDigit()) {
        result.add(extractNumber(lines, lineIndex + 1, symbolIndex, touchedNumbers))
    }
    // check diagonally
    if (lineIndex > 0 && symbolIndex > 0 && lines[lineIndex - 1][symbolIndex - 1].isDigit()) {
        result.add(extractNumber(lines, lineIndex - 1, symbolIndex - 1, touchedNumbers))
    }
    if (lineIndex > 0 && symbolIndex < maxLineLength && lines[lineIndex - 1][symbolIndex + 1].isDigit()) {
        result.add(extractNumber(lines, lineIndex - 1, symbolIndex + 1, touchedNumbers))
    }
    if (lineIndex < maxNumberOfLines && symbolIndex > 0 && lines[lineIndex + 1][symbolIndex - 1].isDigit()) {
        result.add(extractNumber(lines, lineIndex + 1, symbolIndex - 1, touchedNumbers))
    }
    if (lineIndex < maxNumberOfLines && symbolIndex < maxLineLength && lines[lineIndex + 1][symbolIndex + 1].isDigit()) {
        result.add(extractNumber(lines, lineIndex + 1, symbolIndex + 1, touchedNumbers))
    }

    // we might have touched a number twice and therefore have received a 0 in the list
    // remove all 0s
    result.removeAll { it == 0 }

    return result
}

/**
 * Find all the symbols in a line
 *
 * @param line the line to check
 * @return a list of all the indexes of the symbols

 */
fun findSymbols(line: String): List<Int> {
    val result = mutableListOf<Int>()
    line.forEachIndexed { index, c ->
        if (!(c.isDigit() || c == '.')) {
            result.add(index)
        }
    }
    return result
}

/**
 * Find only the gears in a line
 * @param line the line to check
 * @return a list of all the indexes of the gears
 */
fun findGearsSymbols(line: String): List<Int> {
    val result = mutableListOf<Int>()
    line.forEachIndexed { index, c ->
        if (c == '*') {
            result.add(index)
        }
    }
    return result
}

/**
 * Extract the number from the line
 *
 * @param lines list of all lines
 * @param lineIndex the index of the line
 * @param index the index of the symbol
 * @param touchedNumbers the list of touched numbers in the lines
 * @return the number from the line
 */
fun extractNumber(lines: List<String>, lineIndex: Int, index: Int, touchedNumbers: Array<BooleanArray>): Int {
    var result = ""
    var i = index
    while ((i >= 0) && lines[lineIndex][i].isDigit() && !touchedNumbers[lineIndex][i]) {
        result = lines[lineIndex][i] + result
        touchedNumbers[lineIndex][i] = true
        i--
    }
    i = index + 1
    while ((i < lines[lineIndex].length) && lines[lineIndex][i].isDigit() && !touchedNumbers[lineIndex][i]) {
        result += lines[lineIndex][i]
        touchedNumbers[lineIndex][i] = true
        i++
    }
    return if (result.isEmpty()) {
        0
    } else {
        result.toInt()
    }
}