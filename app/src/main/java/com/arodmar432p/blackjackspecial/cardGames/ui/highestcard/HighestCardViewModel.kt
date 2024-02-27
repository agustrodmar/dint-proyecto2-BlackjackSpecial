package com.arodmar432p.blackjackspecial.cardGames.ui.highestcard
/*

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Card
import com.arodmar432p.blackjackspecial.cardGames.data.Deck

/**
 * ViewModel for the HighestCard game.
 *
 * @property deck The deck of cards for the game.
 * @property currentCard LiveData representing the current card in the game.
 * @property remainingCards LiveData representing the number of remaining cards in the deck.
 * @property isDeckReset LiveData representing whether the deck has been reset.
 */
class HighestCardViewModel : ViewModel() {
    // The deck of cards
    private val deck = Deck(cardImageMap = mapOf("corazonesa" to  R.drawable.corazonesa,
        "corazones2" to  R.drawable.corazones2,
        "corazones3" to  R.drawable.corazones3,
        "corazones4" to R.drawable.corazones4,
        "corazones5" to  R.drawable.corazones5,
        "corazones6" to R.drawable.corazones6,
        "corazones7" to  R.drawable.corazones7,
        "corazones8" to  R.drawable.corazones8,
        "corazones9" to R.drawable.corazones9,
        "corazones10" to R.drawable.corazones10,
        "corazonesj" to R.drawable.corazonesj,
        "corazonesq" to R.drawable.corazonesq,
        "corazonesk" to R.drawable.corazonesk,
        "diamantesa" to R.drawable.diamantesa,
        "diamantes2" to  R.drawable.diamantes2,
        "diamantes3" to  R.drawable.diamantes3,
        "diamantes4" to  R.drawable.diamantes4,
        "diamantes5" to  R.drawable.diamantes5,
        "diamantes6" to R.drawable.diamantes6,
        "diamantes7" to  R.drawable.diamantes7,
        "diamantes8" to  R.drawable.diamantes8,
        "diamantes9" to  R.drawable.diamantes9,
        "diamantes10" to  R.drawable.diamantes10,
        "diamantesj" to  R.drawable.diamantesj,
        "diamantesq" to  R.drawable.diamantesq,
        "diamantesk" to  R.drawable.diamantesk,
        "picasa" to  R.drawable.picasa,
        "picas2" to  R.drawable.picas2,
        "picas3" to  R.drawable.picas3,
        "picas4" to R.drawable.picas4,
        "picas5" to  R.drawable.picas5,
        "picas6" to  R.drawable.picas6,
        "picas7" to  R.drawable.picas7,
        "picas8" to  R.drawable.picas8,
        "picas9" to  R.drawable.picas9,
        "picas10" to  R.drawable.picas10,
        "picasj" to  R.drawable.picasj,
        "picasq" to  R.drawable.picasq,
        "picask" to  R.drawable.picask,
        "trebolesa" to  R.drawable.trebolesa,
        "treboles2" to  R.drawable.treboles2,
        "treboles3" to  R.drawable.treboles3,
        "treboles4" to  R.drawable.treboles4,
        "treboles5" to  R.drawable.treboles5,
        "treboles6" to  R.drawable.treboles6,
        "treboles7" to  R.drawable.treboles7,
        "treboles8" to  R.drawable.treboles8,
        "treboles9" to  R.drawable.treboles9,
        "treboles10" to  R.drawable.treboles10,
        "trebolesj" to  R.drawable.trebolesj,
        "trebolesq" to  R.drawable.trebolesq,
        "trebolesk" to R.drawable.trebolesk,
    ))

    // LiveData for the current card
    val currentCard = MutableLiveData<Card>()

    // LiveData for the remaining cards in the deck
    val remainingCards = MutableLiveData<Int>()

    // LiveData for the deck state
    private val isDeckReset = MutableLiveData<Boolean>()

    init {
        // Initialize the game
        resetGame()
    }

    /**
     * Resets the game.
     * Shuffles the deck, updates the remaining cards, sets the current card to null, and updates the deck state.
     */
    fun resetGame() {
        // Shuffle the deck
        deck.shuffle()

        // Update the remaining cards
        remainingCards.value = deck.cardsList.size

        // Set the current card to null
        currentCard.value = null

        // Update the deck state
        isDeckReset.value = true
    }

    /**
     * Handles the "Hit Me" action in the game.
     * Checks if the deck has cards, gets a card from the deck if available, and updates the remaining cards.
     */
    fun hitMe() {
        // Check if the deck has cards
        if (deck.hasCards()) {
            try {
                // Get a card from the deck
                currentCard.value = deck.getCard()

                // Update the remaining cards
                remainingCards.value = deck.cardsList.size
            } catch (e: Exception) {
                Log.e("BlackjackDealerViewModel", "Error obtaining a card: ${e.message}")
            }
        }
    }
}
*/
