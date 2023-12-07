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

    fun getCard(): Card {
        if (cardsList.isEmpty()) {
            throw IllegalStateException("Deck is empty!")
        }
        return cardsList.removeAt(cardsList.size - 1)
    }
}
    /*
    fun dealCards(players: List<Player>, numCards: Int) {
        for (i in 0 until numCards) {
            for (player in players) {
                player.hand.add(getCard())
            }
        }
    }
    */

/*
    // Función que usa Diego para el carta más alta, es iinteresante para llevar el control de
    // cartas que quedan en la baraja.
    fun getCardsTotal(): Int {
        return cardsList.size
    }


    companion object {
        fun getFaceDownCard(context: Context): Card {
            val idDrawable = context.resources.getIdentifier(
                "bocabajo",
                "drawable",
                context.packageName
            )
            return Card(Rank.NONE, Suit.NONE, 0, 0, idDrawable)
        }
    }
}
/*


 */


 fun ejemploBing() {
 Box(modifier = Modifier.fillMaxHeight(0.7f)
    .fillMaxWidth()) {
    for (i in jugador.mano) {
        Box(modifier = Modifier.clip(shape = MaterialTheme.shapes.medium)
        .offset(x,y)
        .fillMaxWidth()
        .fillMaxHeight()
        ) {
        Image(
            painter = painterResource(
                id = context.resources.getIdentifier(
                "c" + i.idDrawable.toString(),
                "drawable",
                context.packageName
                )
            ),
            contentDescription = "Carta mostrada",
            modifier = Modifier
                .height(150.dp)
                .width(75.dp)
         )
 */
