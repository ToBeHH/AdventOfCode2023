/**
 * Main method to read the file and give the result
 */
fun main() {
    Day19("day19input.txt").run()
}

class Day19(fileName: String) : BaseDay(fileName) {
    companion object {
        val LETTERS = listOf('x', 'm', 'a', 's')
    }

    override fun runPart1(): Long {
        val rules = mutableMapOf<String, List<Rule>>()
        var parseRules = true // are we parsing rules or items?
        var sum = 0L
        // parse all the lines
        for (line in lines) {
            if (line.isEmpty()) {
                parseRules = false
                continue
            }
            if (parseRules) {
                // we are parsing rules
                val (ruleName, rulesList) = parseRuleSet(line)
                rules[ruleName] = rulesList
            } else {
                // we are parsing items
                val part = Part.parse(line)
                // instead of storing the items, we immediately check, if the item was accepted
                if (part.isAccepted(rules)) {
                    // build the sum for all letters
                    sum += part.values.values.sum()
                }
            }
        }
        return sum
    }

    override fun runPart2(): Long {
        // the brute force approach does not work here, so we need to find all possible number ranges for each item
        val ranges = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
        val setOfNumbers = mutableMapOf<Char, MutableSet<Int>>()
        for (letter in LETTERS) {
            setOfNumbers[letter] = mutableSetOf(1, 4001)
            ranges[letter] = mutableListOf()
        }
        // first, parse all the rules and find the ranges for each letter
        val rules = mutableMapOf<String, List<Rule>>()
        for (line in lines) {
            if (line.isEmpty()) {
                break
            }
            val (ruleName, rulesList) = parseRuleSet(line)
            rules[ruleName] = rulesList
            for (letter in LETTERS) {
                rulesList.filter { it.type == letter }.forEach {
                    if (it.operator == '<') {
                        setOfNumbers[letter]!!.add(it.value)
                    } else {
                        setOfNumbers[letter]!!.add(it.value + 1)
                    }
                }
            }
        }

        // now for every letter create a list of ranges
        for (letter in LETTERS) {
            val listOfNumbers = setOfNumbers[letter]!!.sorted()
            listOfNumbers.forEachIndexed { index, i ->
                if (index < setOfNumbers[letter]!!.size - 1) {
                    ranges[letter]!!.add(Pair(i, listOfNumbers[index + 1] - 1))
                }
            }
        }

        // now check all possible combinations (brute force)
        // no pruning, takes about 30 minutes to run on my M1 MacBook Pro
        // so code could be optimized further
        var sumAccepted: Long = 0
        for (x in ranges['x']!!) {
            for (m in ranges['m']!!) {
                for (a in ranges['a']!!) {
                    for (s in ranges['s']!!) {
                        val values = mutableMapOf<Char, Long>()
                        values['x'] = x.first.toLong()
                        values['m'] = m.first.toLong()
                        values['a'] = a.first.toLong()
                        values['s'] = s.first.toLong()
                        if (Part(values).isAccepted(rules)) {
                            sumAccepted += (x.second - x.first + 1).toLong() *
                                    (m.second - m.first + 1).toLong() *
                                    (a.second - a.first + 1).toLong() *
                                    (s.second - s.first + 1).toLong()
                        }
                    }
                }
            }
        }

        return sumAccepted
    }

    private fun parseRuleSet(line: String): Pair<String, List<Rule>> {
        return Pair(line.substringBefore("{"),
            line.substringAfter("{").substringBefore("}").split(",")
                .map { Rule.parse(it) }
        )
    }

    class Rule(val type: Char, val operator: Char, val value: Int, private val nextRule: String) {
        fun isAccepted(): Boolean {
            return type == '#' && nextRule == "A"
        }

        fun isRejected(): Boolean {
            return type == '#' && nextRule == "R"
        }

        fun getIdentifierForNextRule(part: Part): String? {
            if (type == '#') {
                // the next rule is only an A or R
                return nextRule
            }
            if (type in LETTERS) {
                if (operator == '<') {
                    if (part.values[type]!! < value) {
                        return nextRule
                    }
                } else if (operator == '>') {
                    if (part.values[type]!! > value) {
                        return nextRule
                    }
                }
            }
            // this rule could not be applied to the given item, you should go and check the next rule
            return null
        }

        companion object {
            fun parse(it: String): Rule {
                // does it contain an operator?
                if (it.contains('<') || it.contains('>')) {
                    // yes, parse it
                    val type = it[0]
                    val operator = it[1]
                    val value = it.substring(2).substringBefore(":").toInt()
                    val nextRule = it.substringAfter(":")
                    return Rule(type, operator, value, nextRule)
                } else {
                    // no, it's a string
                    return Rule('#', '#', 0, it)
                }
            }
        }
    }

    /**
     * Data class for one part to be tested
     */
    data class Part(val values: Map<Char, Long>) {
        /**
         * Check if this item is accepted by the rules
         *
         * @param rules the rules to check
         */
        fun isAccepted(rules: MutableMap<String, List<Rule>>): Boolean {
            var currentRuleSet: List<Rule> = rules["in"]!!
            // we iterate until we find a rule that is accepted or rejected
            while (true) {
                for (rule in currentRuleSet) {
                    if (rule.isAccepted()) {
                        return true
                    } else if (rule.isRejected()) {
                        return false
                    } else {
                        val nextRuleIdentifier = rule.getIdentifierForNextRule(this)
                        if (nextRuleIdentifier != null) {
                            if (nextRuleIdentifier == "A") {
                                return true
                            } else if (nextRuleIdentifier == "R") {
                                return false
                            }
                            currentRuleSet = rules[nextRuleIdentifier]!!
                            break
                        }
                    }
                }
            }
        }

        companion object {
            fun parse(line: String): Part {
                val parts = line.substring(1, line.length - 1).split(",")
                val values = mutableMapOf<Char, Long>()
                LETTERS.forEachIndexed { index, c ->
                    values[c] = parts[index].substringAfter("=").toLong()
                }
                return Part(values)
            }
        }
    }
}
