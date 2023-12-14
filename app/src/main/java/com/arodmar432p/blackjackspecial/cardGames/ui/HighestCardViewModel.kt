package com.arodmar432p.blackjackspecial.cardGames.ui

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
        "trebolesk" to R.drawable.trebolesk,
    ))

    // LiveData for the current card
    val currentCard = MutableLiveData<Card>()

    // LiveData for the remaining cards in the deck
    val remainingCards = MutableLiveData<Int>()

    // LiveData for the deck state
    val isDeckReset = MutableLiveData<Boolean>()

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