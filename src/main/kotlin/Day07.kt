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

    interface CommonCard : Comparable<CommonCard> {
        val card: Char

        fun getCards(): String

        override fun compareTo(other: CommonCard): Int {
            return getCards().indexOf(card).compareTo(getCards().indexOf(other.card))
        }
    }

    interface CommonGame : Comparable<CommonGame> {
        val cards: List<CommonCard>

        val bid: Int

        enum class Category {
            FIVE_OF_A_KIND,
            FOUR_OF_A_KIND,
            FULL_HOUSE,
            THREE_OF_A_KIND,
            TWO_PAIRS,
            ONE_PAIR,
            HIGH_CARD
        }

        fun categorize(): Category
        fun getCard(index: Int): CommonCard

        override fun compareTo(other: CommonGame): Int {
            return if (this.categorize() == other.categorize()) {
                for (i in 0..4) {
                    if (this.getCard(i) != other.getCard(i)) {
                        return this.getCard(i).compareTo(other.getCard(i))
                    }
                }
                return 0
            } else {
                this.categorize().compareTo(other.categorize())
            }
        }

        fun category(grouped: Map<Char, List<CommonCard>>): Category {
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
    }

    class Game(
        override val cards: List<Card>,
        override val bid: Int
    ) : CommonGame {
        class Card(char: Char) : CommonCard {
            override val card: Char = char

            override fun getCards(): String {
                return "AKQJT98765432"
            }

            override fun equals(other: Any?): Boolean {
                if (other == null) return false
                if (other !is Card) return false
                return card == other.card
            }

            override fun hashCode(): Int {
                return card.hashCode()
            }

            override fun toString(): String {
                return "Card($card)"
            }

            companion object {
                fun getCard(name: Char) = Card(name)
            }
        }

        override fun getCard(index: Int): Card {
            return cards[index]
        }

        /**
         * Categorize the cards similar to poker
         */
        override fun categorize(): CommonGame.Category {
            return category(cards.groupBy { it.card })
        }

        /**
         * Factory to read the game from a file
         */
        companion object Factory {
            fun readGames(lines: List<String>): List<Game> {
                return lines.map { line ->
                    Game(
                        line.split(" ")[0].toCharArray().toList().map { c -> Card.getCard(c) },
                        line.split(" ")[1].toInt()
                    )
                }
            }
        }

        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            if (other !is Game) return false
            return cards == other.cards && bid == other.bid
        }

        override fun hashCode(): Int {
            return cards.sumOf { it.card.hashCode() } + bid.hashCode()
        }

        override fun toString(): String {
            return "Game(${cards.map { it.card }.joinToString("")}, $bid)"
        }
    }

    data class Game2(
        override val cards: List<Card>,
        override val bid: Int
    ) : CommonGame {
        class Card(char: Char) : CommonCard {
            override val card: Char = char

            override fun getCards(): String {
                return "AKQT98765432J"
            }

            override fun toString(): String {
                return "Card2($card)"
            }

            override fun equals(other: Any?): Boolean {
                if (other == null) return false
                if (other !is Card) return false
                return card == other.card
            }

            override fun hashCode(): Int {
                return card.hashCode()
            }

            companion object {
                fun getCard(name: Char) = Card(name)
            }
        }

        override fun getCard(index: Int): Card {
            return cards[index]
        }

        /**
         * Categorize the cards similar to poker, using J as a Joker
         */
        override fun categorize(): CommonGame.Category {
            if (cards.contains(Card('J'))) {
                val possibleCategories = mutableListOf<CommonGame.Category>()
                val grouped = cards.groupBy { it }
                for (i in grouped.keys) {
                    if (i != Card('J')) {
                        val newCards = cards.map { if (it == Card('J')) i else it }
                        val cat = category(newCards.groupBy { it.card })
                        possibleCategories.add(cat)
                    }
                }
                if (possibleCategories.isEmpty()) {
                    return CommonGame.Category.FIVE_OF_A_KIND
                }
                // actually min is giving the highest possible category
                return possibleCategories.min()
            }

            return category(cards.groupBy { it.card })
        }

        /**
         * Factory to read the game from a file
         */
        companion object Factory {
            fun readGames(lines: List<String>): List<Game2> {
                return lines.map { line ->
                    Game2(
                        line.split(" ")[0].toCharArray().toList().map { c -> Card.getCard(c) },
                        line.split(" ")[1].toInt()
                    )
                }
            }
        }

        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            if (other !is Game2) return false
            return cards == other.cards && bid == other.bid
        }

        override fun hashCode(): Int {
            return cards.sumOf { it.card.hashCode() } + bid.hashCode()
        }

        override fun toString(): String {
            return "Game2(${cards.map { it.card }.joinToString("")}, $bid)"
        }
    }
}
