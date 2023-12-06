package com.arodmar432p.blackjackspecial.data

import android.annotation.SuppressLint
import android.content.Context


class Deck(private val context: Context) {
    private val cardsList = ArrayList<Card>()

    init {
        createDeck()
    }

    private fun createDeck() {
        for (suit in Suit.values()) {
            for (rank in Rank.values()) {
                val minPoints = if (rank == Rank.ACE) 1 else rank.ordinal
                val maxPoints = if (rank == Rank.ACE) 11 else rank.ordinal

                val idDrawable = getIdDrawable("${suit.name.lowercase()}_${rank.ordinal}")

                cardsList.add(Card(rank, suit, minPoints, maxPoints, idDrawable))
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun getIdDrawable(cardName: String) =
        context.resources.getIdentifier(
            cardName,
            "drawable",
            context.packageName
        )

    fun shuffle() {
        cardsList.shuffle()
    }

    fun getCard(): Card {
        return cardsList.removeAt(cardsList.size - 1)
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