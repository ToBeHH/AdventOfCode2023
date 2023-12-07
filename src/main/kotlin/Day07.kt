/**
 * Main method to read the file and give the result
 */
fun main() {
    Day07("day07input.txt").run()
}

class Day07(fileName: String) : BaseDay(fileName) {
    override fun runPart1(): Int {
        return readGames().sorted().reversed().mapIndexed { index, game ->
            game.bid * (index + 1)
        }.sum()
    }

    override fun runPart2(): Int {
        return readGames2().sorted().reversed().mapIndexed { index, game ->
            game.bid * (index + 1)
        }.sum()
    }

    internal fun readGames(): List<Game> {
        return Game.readGames(lines)
    }

    internal fun readGames2(): List<Game2> {
        return Game2.readGames(lines)
    }

    data class Game(
        val cards: List<Card>,
        val bid: Int
    ) : Comparable<Game> {
        enum class Card(val char: Char) {
            A('A'), K('K'), Q('Q'), J('J'), T('T'),
            NINE('9'), EIGHT('8'), SEVEN('7'), SIX('6'),
            FIVE('5'), FOUR('4'), THREE('3'), TWO('2');

            companion object {
                fun getCard(name: Char) = Card.entries.find { it.char == name }
            }
        }
        enum class Category {
            FIVE_OF_A_KIND,
            FOUR_OF_A_KIND,
            FULL_HOUSE,
            THREE_OF_A_KIND,
            TWO_PAIRS,
            ONE_PAIR,
            HIGH_CARD
        }

        /**
         * Compare the game with another game
         */
        override fun compareTo(other: Game): Int {
            return if (this.categorize() == other.categorize()) {
                for(i in 0..4) {
                    if (this.cards[i] != other.cards[i]) {
                        return this.cards[i].compareTo(other.cards[i])
                    }
                }
                return 0
            } else {
                this.categorize().compareTo(other.categorize())
            }
        }

        /**
         * Categorize the cards similar to poker
         */
        fun categorize(): Category {
            if (cards.groupBy { it }.filter { it.value.size == 5 }.size == 1) {
                return Category.FIVE_OF_A_KIND
            }
            if (cards.groupBy { it }.filter { it.value.size == 4 }.size == 1) {
                return Category.FOUR_OF_A_KIND
            }
            if (cards.groupBy { it }.filter { it.value.size == 3 }.size == 1 &&
                cards.groupBy { it }.filter { it.value.size == 2 }.size == 1) {
                return Category.FULL_HOUSE
            }
            if (cards.groupBy { it }.filter { it.value.size == 3 }.size == 1) {
                return Category.THREE_OF_A_KIND
            }
            if (cards.groupBy { it }.filter { it.value.size == 2 }.size == 2) {
                return Category.TWO_PAIRS
            }
            if (cards.groupBy { it }.filter { it.value.size == 2 }.size == 1) {
                return Category.ONE_PAIR
            }
            return Category.HIGH_CARD
        }

        /**
         * Factory to read the game from a file
         */
        companion object Factory {
            fun readGames(lines: List<String>): List<Game> {
                return lines.map { line ->
                    Game(line.split(" ")[0].toCharArray().toList().map { c -> Card.getCard(c)!! },
                         line.split(" ")[1].toInt()) }
            }
        }
    }

    data class Game2(
        val cards: List<Card>,
        val bid: Int
    ) : Comparable<Game2> {
        enum class Card(val char: Char) {
            A('A'), K('K'), Q('Q'), T('T'),
            NINE('9'), EIGHT('8'), SEVEN('7'), SIX('6'),
            FIVE('5'), FOUR('4'), THREE('3'), TWO('2'), J('J');

            companion object {
                fun getCard(name: Char) = Card.entries.find { it.char == name }
            }
        }
        enum class Category {
            FIVE_OF_A_KIND,
            FOUR_OF_A_KIND,
            FULL_HOUSE,
            THREE_OF_A_KIND,
            TWO_PAIRS,
            ONE_PAIR,
            HIGH_CARD
        }

        /**
         * Compare the game with another game
         */
        override fun compareTo(other: Game2): Int {
            return if (this.categorize() == other.categorize()) {
                for(i in 0..4) {
                    if (this.cards[i] != other.cards[i]) {
                        return this.cards[i].compareTo(other.cards[i])
                    }
                }
                return 0
            } else {
                this.categorize().compareTo(other.categorize())
            }
        }

        /**
         * Categorize the cards similar to poker, using J as a Joker
         */
        fun categorize(): Category {
            if (cards.contains(Card.J)) {
                val possibleCategories = mutableListOf<Category>()
                val grouped = cards.groupBy { it }
                for (i in grouped.keys) {
                    if (i != Card.J) {
                        val newCards = cards.map { if (it == Card.J) i else it }
                        val cat = category(newCards.groupBy { it })
                        possibleCategories.add(cat)
                    }
                }
                if (possibleCategories.isEmpty()) {
                    return Category.FIVE_OF_A_KIND
                }
                // actually min is giving the highest possible category
                return possibleCategories.min()
            }

            return category(cards.groupBy { it })
        }

        private fun category(grouped: Map<Card, List<Card>>): Category {
            if (grouped.filter { it.value.size == 5 }.size == 1) {
                return Category.FIVE_OF_A_KIND
            }
            if (grouped.filter { it.value.size == 4 }.size == 1) {
                return Category.FOUR_OF_A_KIND
            }
            if (grouped.filter { it.value.size == 3 }.size == 1 &&
                grouped.filter { it.value.size == 2 }.size == 1
            ) {
                return Category.FULL_HOUSE
            }
            if (grouped.filter { it.value.size == 3 }.size == 1) {
                return Category.THREE_OF_A_KIND
            }
            if (grouped.filter { it.value.size == 2 }.size == 2) {
                return Category.TWO_PAIRS
            }
            if (grouped.filter { it.value.size == 2 }.size == 1) {
                return Category.ONE_PAIR
            }
            return Category.HIGH_CARD
        }

        /**
         * Factory to read the game from a file
         */
        companion object Factory {
            fun readGames(lines: List<String>): List<Game2> {
                return lines.map { line ->
                    Game2(line.split(" ")[0].toCharArray().toList().map { c -> Card.getCard(c)!! },
                         line.split(" ")[1].toInt()) }
            }
        }
    }
}
