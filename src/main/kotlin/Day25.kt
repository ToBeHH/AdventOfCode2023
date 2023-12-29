/**
 * Main method to read the file and give the result
 */
fun main() {
    Day25("day25input.txt").run()
}

class Day25(fileName: String) : BaseDay(fileName) {

    override fun runPart1(): Int {
        val connections = mutableListOf<Connection>()
        val nodes = mutableMapOf<String, Node>()
        lines.forEach {
            val parts = it.split(": ")
            parts[1].split(" ").forEach { s ->
                connections.add(Connection(parts[0], s))
                nodes.put(s, Node(s))
            }
            nodes.put(parts[0], Node(parts[0]))
        }
        nodes.forEach {
            it.value.connections = connections.filter { c -> c.left == it.key || c.right == it.key }
            it.value.fillComponents()
        }

        var group1 = 1
        var group2 = 0

        val firstNodeKey = nodes.keys.first()

        nodes.keys.forEachIndexed { index, key ->
            if (index > 0) { // ignoring the first node
                // calculate all the paths from previousNode to this node
                var connections = 0
                val visitedNodes = mutableSetOf<String>()
                visitedNodes.add(firstNodeKey)
                for (component in nodes[firstNodeKey]!!.components) {
                    if (component == key) {
                        connections++
                    } else {
                        val qed = mutableSetOf<String>()
                        val q = ArrayDeque<Pair<String, List<String>>>()
                        q.add(Pair(component, listOf(component)))
                        var found = false
                        while (q.isNotEmpty() && !found && connections < 4) {
                            val (comp, path) = q.removeFirst()
                            for (c in nodes[comp]!!.components) {
                                if (key == c) {
                                    connections++
                                    visitedNodes.addAll(path)
                                    found = true
                                    break
                                } else if (c !in qed && c !in path && c !in visitedNodes) {
                                    q.add(Pair(c, path + c))
                                    qed.add(c)
                                }
                            }
                        }
                    }
                }
                // If it finds more than 3 unique ways to get to given component then it is in group 1
                if (connections >= 4) {
                    group1++
                } else {
                    group2++
                }
            }
        }

        return group1 * group2
    }

    override fun runPart2(): Int {
        // there is no part 2 for this day
        return 0
    }

    data class Connection(val left: String, val right: String, var disconnectedGroups: Int = 0) {

        override fun equals(other: Any?): Boolean {
            if (other is Connection) {
                return (left == other.left && right == other.right) ||
                        (left == other.right && right == other.left)
            }
            return false
        }

        override fun hashCode(): Int {
            return left.hashCode() + right.hashCode()
        }

        override fun toString(): String {
            return "$left/$right"
        }
    }

    data class Node(
        val name: String,
        var connections: List<Connection> = emptyList(),
        var components: Set<String> = emptySet()
    ) {
        fun fillComponents() {
            components = mutableSetOf()
            connections.forEach {
                components = components.plus(it.left)
                components = components.plus(it.right)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (other is Node) {
                return name == other.name
            }
            return false
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }

        override fun toString(): String {
            return "$name: (${connections.size}) ${connections.map { it.left + "-" + it.right }}"
        }
    }

}
