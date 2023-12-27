import util.ExtraMath.lcm

/**
 * Main method to read the file and give the result
 */
fun main() {
    Day20("day20input.txt").run()
}

class Day20(fileName: String) : BaseDay(fileName) {
    override fun runPart1(): Long {
        var lows = 0L
        var highs = 0L
        // create a map of modules
        val modules = getModules()

        // run 1000 times
        repeat(1000) {
            val (l, h, _) = buttonPress(modules, null)
            lows += l
            highs += h
        }
        return lows * highs
    }

    override fun runPart2(): Long {
        // create a map of modules
        val modules = getModules()

        // find the one module, that feed into the rx module
        val rxInput = modules.asSequence()
            .filter { (_, module) -> "rx" in module.destinations }
            .map { (label, _) -> label }.single()
        // as it turns out, the module is a conjunction module!
        require(modules[rxInput] is Conjunction) { "rx module is not a conjunction module" }

        // we are expecting high numbers, so we need to detect cycles!
        // we are now checking for all input modules for the rx module the cycle length, and
        // then we can calculate the least common multiplier (lcm) of all cycle lengths
        return modules.asSequence()
            .filter { (_, module) -> rxInput in module.destinations }
            .map { (label, _) -> findConjunctionModuleCycleLength(modules, label) }
            .toList()
            .lcm()
    }

    /**
     * Parse the list of modules and return a map of modules.
     *
     * The map contains the name of the module as key and the module itself as value.
     *
     * The module itself is an abstract class with four implementations:
     * - FlipFlop: This module has a state and will change the state on every pulse.
     * - Conjunction: This module will send a pulse, if all inputs are high.
     * - Broadcaster: This module will send a pulse to all destinations.
     * - Testing: This module is used for testing and will not send any pulses.
     *
     * @return a map of modules
     */
    private fun getModules(): Map<String, Module> {
        val modules = mutableListOf<Module>()
        for (line in lines) {
            val name = line.substringBefore(" -> ")
            val destinations = line.substringAfter(" -> ").split(",").map { it.trim() }
            when {
                name.startsWith("%") -> modules.add(FlipFlop(name.substring(1), destinations))
                name.startsWith("&") -> modules.add(Conjunction(name.substring(1), destinations))
                name == "broadcaster" -> modules.add(Broadcaster(name, destinations))
            }
        }

        // now we need to register the inputs for the conjunctions
        modules.forEach { module ->
            module.destinations.forEach { destination ->
                val destinationModule = modules.find { it.name == destination }
                if (destinationModule is Conjunction) {
                    destinationModule.registerInput(module.name)
                }
            }
        }

        // create a map of modules
        val modulesMap = modules.associateBy { it.name }.toMutableMap()
        // check, if we have some testing modules and add them
        val extraModules = mutableListOf<Module>()
        modulesMap.forEach { module ->
            module.value.destinations.forEach {
                if (!modulesMap.containsKey(it)) {
                    extraModules.add(
                        Testing(it, emptyList())
                    )
                }
            }
        }
        extraModules.forEach { modulesMap[it.name] = it }
        return modulesMap
    }

    /**
     * Simulate the button press.
     */
    private fun buttonPress(modules: Map<String, Module>, stopModule: String?): Triple<Long, Long, Boolean> {
        var lows = 0
        var highs = 0
        var stopModuleHitCount = false
        val nextModules = ArrayDeque<Pulse>()
        nextModules.add(Pulse("button", "broadcaster", PulseType.LOW))
        while (nextModules.isNotEmpty()) {
            val pulse = nextModules.removeLast()
            // check, if the stop module is firing a HIGH pulse.
            // since the stop module is a conjunction module before the rx module,
            // we are checking for a HIGH pulse instead of a LOW pulse.
            if (pulse.source == stopModule && pulse.type == PulseType.HIGH) {
                stopModuleHitCount = true
            }
            val module = modules[pulse.destination]!!
            module.pulse(pulse).forEach { nextModules.addFirst(it) }
            if (pulse.type == PulseType.LOW) {
                lows++
            } else {
                highs++
            }
        }
        return Triple(lows.toLong(), highs.toLong(), stopModuleHitCount)
    }

    /**
     * Find the cycle length of a conjunction module.
     *
     * @param modules the map of modules
     * @param conjunctionModlueName the name of the conjunction module
     * @return the cycle length
     */
    private fun findConjunctionModuleCycleLength(modules: Map<String, Module>, conjunctionModlueName: String): Long {
        // Continue by pushing the button until the stop module sends a LOW impulse.
        var impulseSent = false
        while (!impulseSent) {
            val (_, _, hit) = buttonPress(modules, conjunctionModlueName)
            impulseSent = hit
        }

        // Starting at the state right after the monitored conjunction module
        // sent a HIGH pulse, we again push the button repeatedly until the
        // monitored module sends another HIGH pulse. This is the presumed
        // cycle length.
        var cycleLength = 0L
        impulseSent = false
        while (!impulseSent) {
            val (_, _, hit) = buttonPress(modules, conjunctionModlueName)
            impulseSent = hit
            cycleLength += 1
        }

        return cycleLength
    }

    /**
     * We have two types of pulses: LOW and HIGH
     */
    enum class PulseType {
        LOW, HIGH
    }

    /**
     * A data class to represent a pulse.
     *
     * A pulse has a source, a destination and a type.
     */
    data class Pulse(
        val source: String,
        val destination: String,
        val type: PulseType
    )

    /**
     * An abstract class to represent a module.
     *
     * A module has a name and a list of destinations.
     *
     * A module can send pulses to its destinations.
     */
    abstract class Module {
        abstract val name: String
        abstract val destinations: List<String>

        abstract fun pulse(pulse: Pulse): List<Pulse>
    }

    /**
     * A flip-flop module.
     *
     * According to the requirements:
     * Flip-flop modules (prefix %) are either on or off; they are initially off. If a flip-flop module
     * receives a high pulse, it is ignored and nothing happens. However, if a flip-flop module receives
     * a low pulse, it flips between on and off. If it was off, it turns on and sends a high pulse.
     * If it was on, it turns off and sends a low pulse.
     *
     * @param name the name of the module
     * @param destinations the list of destinations
     */
    class FlipFlop(override val name: String, override val destinations: List<String>) : Module() {
        private var state = false

        override fun pulse(pulse: Pulse): List<Pulse> {
            return if (pulse.type == PulseType.HIGH) {
                emptyList()
            } else {
                if (state) {
                    state = false
                    destinations.map { Pulse(name, it, PulseType.LOW) }
                } else {
                    state = true
                    destinations.map { Pulse(name, it, PulseType.HIGH) }
                }
            }
        }
    }

    /**
     * A conjunction module.
     *
     * According to the requirements:
     * Conjunction modules remember the type of the most recent pulse received from each of their
     * connected input modules; they initially default to remembering a low pulse for each input.
     * When a pulse is received, the conjunction module first updates its memory for that input.
     * Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise,
     * it sends a high pulse.
     *
     * @param name the name of the module
     * @param destinations the list of destinations
     * @param memory the memory of the module
     */
    class Conjunction(
        override val name: String, override val destinations: List<String>,
        private val memory: MutableMap<String, PulseType> = mutableMapOf()
    ) : Module() {
        override fun pulse(pulse: Pulse): List<Pulse> {
            // First update the memory for the input
            memory[pulse.source] = pulse.type

            // Then determine the kind of pulse to send
            val sendKind = if (memory.values.all { it == PulseType.HIGH }) {
                PulseType.LOW
            } else {
                PulseType.HIGH
            }

            return destinations.map { Pulse(name, it, sendKind) }
        }

        // Used to register an input to this module.
        fun registerInput(label: String) {
            memory[label] = PulseType.LOW
        }
    }

    /**
     * A broadcaster module.
     *
     * According to the requirements:
     * There is a single broadcast module (named broadcaster). When it receives a pulse,
     * it sends the same pulse to all of its destination modules.
     *
     * @param name the name of the module
     * @param destinations the list of destinations
     */
    class Broadcaster(override val name: String, override val destinations: List<String>) : Module() {
        override fun pulse(pulse: Pulse): List<Pulse> {
            return destinations.map { Pulse(name, it, pulse.type) }
        }
    }

    /**
     * A testing module.
     *
     * Testing modules are untyped modules, which do not produce any pulses.
     *
     * @param name the name of the module
     * @param destinations the list of destinations
     */
    class Testing(override val name: String, override val destinations: List<String>) : Module() {
        override fun pulse(pulse: Pulse): List<Pulse> {
            return emptyList()
        }
    }

}
