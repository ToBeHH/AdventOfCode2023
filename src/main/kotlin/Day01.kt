/**
 * Main method to read the file and give the result
 */
fun main(args: Array<String>) {
    // read file sample.txt
    val fileName = "/day01input.txt"
    val lines = object {}.javaClass.getResourceAsStream(fileName)?.bufferedReader()?.readLines()
    var sumPart1 = 0
    var sumPart2 = 0
    if (lines != null) {
        for (line in lines) {
            sumPart1 += getInts(line)
            sumPart2 += getIntsWithText(line)
        }
    }
    println("Part 1: $sumPart1")
    println("Part 2: $sumPart2")
}

/**
 * Get from the line the first digit and the last digit
 * and form a two digit number
 *
 * @param line the line to get the digits
 * @return the two digit number
 */
fun getInts(line: String): Int {
    var firstDigit = 0
    for (i in 0..line.length - 1) {
        if (line[i].isDigit()) {
            firstDigit = line[i].toString().toInt()
            break
        }
    }
    var lastDigit = 0
    for (i in line.length - 1 downTo 0) {
        if (line[i].isDigit()) {
            lastDigit = line[i].toString().toInt()
            break
        }
    }
    return firstDigit * 10 + lastDigit
}

/**
 * Get from the line the first digit and the last digit
 * and form a two digit number
 *
 * @param line the line to get the digits
 * @return the two digit number
 */
fun getIntsWithText(line: String): Int {
    var firstDigit = 0
    for (i in 0..line.length - 1) {
        when {
            line.substring(i).startsWith("one") -> {
                firstDigit = 1
                break
            }
            line.substring(i).startsWith("two") -> {
                firstDigit = 2
                break
            }
            line.substring(i).startsWith("three") -> {
                firstDigit = 3
                break
            }
            line.substring(i).startsWith("four") -> {
                firstDigit = 4
                break
            }
            line.substring(i).startsWith("five") -> {
                firstDigit = 5
                break
            }
            line.substring(i).startsWith("six") -> {
                firstDigit = 6
                break
            }
            line.substring(i).startsWith("seven") -> {
                firstDigit = 7
                break
            }
            line.substring(i).startsWith("eight") -> {
                firstDigit = 8
                break
            }
            line.substring(i).startsWith("nine") -> {
                firstDigit = 9
                break
            }
            line[i].isDigit() -> {
                firstDigit = line[i].toString().toInt()
                break
            }
        }
    }
    var lastDigit = 0
    for (i in line.length - 1 downTo 0) {
        when {
            line.substring(i).startsWith("one") -> {
                lastDigit = 1
                break
            }
            line.substring(i).startsWith("two") -> {
                lastDigit = 2
                break
            }
            line.substring(i).startsWith("three") -> {
                lastDigit = 3
                break
            }
            line.substring(i).startsWith("four") -> {
                lastDigit = 4
                break
            }
            line.substring(i).startsWith("five") -> {
                lastDigit = 5
                break
            }
            line.substring(i).startsWith("six") -> {
                lastDigit = 6
                break
            }
            line.substring(i).startsWith("seven") -> {
                lastDigit = 7
                break
            }
            line.substring(i).startsWith("eight") -> {
                lastDigit = 8
                break
            }
            line.substring(i).startsWith("nine") -> {
                lastDigit = 9
                break
            }
            line[i].isDigit() -> {
                lastDigit = line[i].toString().toInt()
                break
            }
        }
    }
    return firstDigit * 10 + lastDigit
}
