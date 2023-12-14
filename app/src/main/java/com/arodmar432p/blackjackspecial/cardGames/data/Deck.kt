package com.arodmar432p.blackjackspecial.cardGames.data

/**
 * A class representing a deck of cards in the game of Blackjack.
 *
 * @property cardImageMap A map containing the image resources for the cards.
 */
class Deck(private val cardImageMap: Map<String, Int>) {

    /**
     * Companion object to create a deck of cards.
     */
    companion object {
        /**
         * Creates a deck of cards.
         *
         * @param cardImageMap A map containing the image resources for the cards.
         * @return An ArrayList of Card objects representing a deck of cards.
         */
        fun createDeck(cardImageMap: Map<String, Int>): ArrayList<Card> {
            val cardsList = ArrayList<Card>()
            // Loop through each suit
            for (suit in Suit.values()) {
                // Loop through each rank
                for (rank in Rank.values()) {
                    // Determine the minimum and maximum points for the card
                    val minPoints =
                        if (rank == Rank.ACE) 1 else if (rank.ordinal > 10) 10 else rank.ordinal
                    val maxPoints =
                        if (rank == Rank.ACE) 11 else if (rank.ordinal > 10) 10 else rank.ordinal

                    // Construct the drawable name for the card image
                    val idDrawableName = when (suit) {
                        Suit.HEARTS -> "corazones"
                        Suit.DIAMONDS -> "diamantes"
                        Suit.CLUBS -> "treboles"
                        Suit.SPADES -> "picas"
                    } + when (rank) {
                        Rank.ACE -> "a"
                        Rank.TWO -> "2"
                        Rank.THREE -> "3"
                        Rank.FOUR -> "4"
                        Rank.FIVE -> "5"
                        Rank.SIX -> "6"
                        Rank.SEVEN -> "7"
                        Rank.EIGHT -> "8"
                        Rank.NINE -> "9"
                        Rank.TEN -> "10"
                        Rank.JACK -> "j"
                        Rank.QUEEN -> "q"
                        Rank.KING -> "k"
                    }

                    // Get the drawable ID from the map
                    val idDrawable = cardImageMap[idDrawableName]
                    // If the drawable ID is not null, add the card to the list
                    if (idDrawable != null) {
                        cardsList.add(Card(rank, suit, minPoints, maxPoints, idDrawable))
                    }
                }
            }
            // Return the list of cards
            return cardsList
        }
    }


    // Create the deck of cards
    val cardsList = createDeck(cardImageMap)


    /**
     * Resets the deck of cards.
     *
     * This function recreates the deck to ensure all cards are present.
     */
    private fun reset() {
        cardsList.clear()
        cardsList.addAll(createDeck(cardImageMap))
    }

    /**
     * Shuffles the deck of cards.
     *
     * This function first resets the deck to ensure all cards are present,
     * then shuffles the cards.
     */
    fun shuffle() {
        reset()
        cardsList.shuffle()
    }

    /**
     * Checks if the deck has cards.
     *
     * @return A Boolean indicating whether the deck has cards.
     */
    fun hasCards(): Boolean {
        return cardsList.isNotEmpty()
    }

    /**
     * Gets a card from the deck.
     *
     * @return A Card object.
     * @throws IllegalStateException If the deck is empty.
     */
    fun getCard(): Card {
        if (cardsList.isEmpty()) {
            throw IllegalStateException("No se puede obtener una carta porque el mazo está vacío.")
        }
        return cardsList.removeAt(cardsList.size - 1)
    }
}
