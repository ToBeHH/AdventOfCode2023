/**
 * Main method to read the file and give the result
 */
fun main() {
    Day08("day08input.txt").run()
}

class Day08(fileName: String) : BaseDay(fileName) {
    override fun runPart1(): Long {
        val network = readNetwork()
        val currentNode = "AAA"
        return simulateSteps(currentNode, network) { it == "ZZZ" }
    }

    private fun simulateSteps(startNode: String, network: Network, finished: (String) -> Boolean): Long {
        var currentNode = startNode
        var steps : Long = 0
        var strategyStep = 0

        while (!finished(currentNode)) {
            val node = network.nodes.find { it.name == currentNode }!!

            currentNode = if (network.stratey[strategyStep] == 'R') {
                node.right
            } else {
                node.left
            }
            strategyStep += 1
            steps += 1
            if (strategyStep == network.stratey.length) {
                strategyStep = 0
            }
        }
        return steps
    }

    private fun gcd(a: Long, b: Long): Long {
        return if (b == 0.toLong()) a else gcd(b, a % b)
    }

    override fun runPart2(): Long {
        val network = readNetwork()
        val currentNodes = network.nodes.filter { it.name[2] == 'A' }.map { it.name }.toTypedArray()
        val steps = mutableListOf<Long>()
        println("Starting with ${currentNodes.size} nodes")

        for(i in currentNodes.indices) {
            steps.add(simulateSteps(currentNodes[i], network) { it[2] == 'Z' })
        }

        // calculate lcm of all steps
        return steps.reduce { x, y -> (x * y) / gcd(x, y) }
    }

    internal fun readNetwork(): Network {
        return Network.read(lines)
    }

    data class Node(val name: String, val left: String, val right: String) {
        companion object {
            fun read(line: String): Node {
                // parse AAA = (BBB, CCC)
                val parts = line.split(" ")
                return Node(parts[0], parts[2].substring(1,4), parts[3].substring(0,3))
            }
        }
    }

    data class Network(val nodes: List<Node>, val stratey: String) {
        companion object {
            fun read(lines: List<String>): Network {
                val strategy = lines[0]

                val nodes = mutableListOf<Node>()
                lines.subList(2, lines.size).forEach {
                    nodes.add(Node.read(it))
                }
                return Network(nodes, strategy)
            }
        }
    }
}
