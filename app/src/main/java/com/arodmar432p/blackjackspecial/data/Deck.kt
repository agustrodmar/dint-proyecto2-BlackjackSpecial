package com.arodmar432p.blackjackspecial.data


class Deck {
    private val cardsList = ArrayList<Card>()

    init {
        createDeck()
    }

    private fun createDeck() {
        for (suit in Suit.values()) {
            for (rank in Rank.values()) {
                val minPoints = if (rank == Rank.ACE) 1 else if (rank.ordinal > 10) 10 else rank.ordinal
                val maxPoints = if (rank == Rank.ACE) 11 else if (rank.ordinal > 10) 10 else rank.ordinal

                val idDrawable = when (suit) {
                    Suit.HEARTS -> "corazones"
                    Suit.DIAMONDS -> "diamantes"
                    Suit.CLUBS -> "treboles"
                    Suit.SPADES -> "picas"
                    else -> ""
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
                    else -> ""
                }

                if (idDrawable.isNotBlank()) {  // Añade esta línea
                    cardsList.add(Card(rank, suit, minPoints, maxPoints, idDrawable))
                }
            }
        }
    }

    fun shuffle() {
        cardsList.shuffle()
    }

    fun hasCards(): Boolean {
        return cardsList.isNotEmpty()
    }


    fun getCard(): Card {
        if (cardsList.isEmpty()) {
            throw IllegalStateException("Deck is empty!")
        }
        return cardsList.removeAt(cardsList.size - 1)
    }
}

