package com.arodmar432p.blackjackspecial.cardGames.ui

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Card
import com.arodmar432p.blackjackspecial.cardGames.data.Deck
import com.arodmar432p.blackjackspecial.cardGames.data.Player
import com.arodmar432p.blackjackspecial.cardGames.data.Rank

/**
 * A ViewModel class representing the dealer in the game of Blackjack.
 */
class BlackjackDealerViewModel(application: Application)
    : AndroidViewModel(application) {

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

    // The player
    private val player = Player("Player", mutableListOf(), 0)

    // The dealer
    private val dealer = Player("Dealer", mutableListOf(), 0)

    // LiveData objects to hold the game state
    val winner = MutableLiveData<String>()
    val playerPoints = MutableLiveData<Int>()
    private val dealerPoints = MutableLiveData<Int>()
    val playerHand = MutableLiveData<List<Card>>()
    val dealerHand = MutableLiveData<List<Card>>()
    val isGameOver = MutableLiveData(false)
    val gameReset = MutableLiveData(false)
    private val _gameInProgress = MutableLiveData<Boolean>()
    val gameInProgress: LiveData<Boolean> get() = _gameInProgress
    private val _playerChips = MutableLiveData(5000)
    val playerChips: LiveData<Int> get() = _playerChips
    private val _currentBet = MutableLiveData(0)
    val currentBet: LiveData<Int> get() = _currentBet

    // MediaPlayer to play the shuffle sound
    private var dealSoundPlayer: MediaPlayer? = null

    // MediaPlayer to play chips sound
    private var chipSoundPlayer: MediaPlayer? = null

    // Button that allows player to Bet or not...
    val isStartGameButtonEnabled: LiveData<Boolean> get() = _currentBet.map { it > 0 }

    var sliderValue = MutableLiveData<Int>()
    /**
     * Starts a new game.
     */

    init {
        if (_currentBet.value == 0) {

        }
    }


    fun startGame() {
        // Shuffle the deck and clear the hands
        deck.shuffle()
        player.hand.clear()
        dealer.hand.clear()
        // Deal two cards to each player
        player.hand.add(deck.getCard())
        dealer.hand.add(deck.getCard())
        player.hand.add(deck.getCard())
        dealer.hand.add(deck.getCard())
        // Update the LiveData objects
        playerHand.value = player.hand
        dealerHand.value = dealer.hand
        _gameInProgress.value = true
        calculatePoints()
    }


    /**
     * Let a player bet against the Dealer
     */
    fun placeBet() {
        if (sliderValue.value!! <= _playerChips.value!!) {
            _currentBet.value = sliderValue.value
            _playerChips.value = _playerChips.value!! - sliderValue.value!!
        } else {
            // Toast Message
            Toast.makeText(getApplication(),
                "No tienes suficientes fichas para esta apuesta",
                Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Function that handles win scenerarios
     */
    private fun winBet() {
        _playerChips.value = _playerChips.value!! + (_currentBet.value!! * 2)
        _currentBet.value = 0
    }

    /**
     * Function that handles scenerarios where player lose.
     */
    private fun loseBet() {
        _currentBet.value = 0
    }

    private fun draw() {
        _playerChips.value = _playerChips.value!! + _currentBet.value!!
        _currentBet.value = 0
    }

    fun increaseBet() {
        if (_playerChips.value!! > _currentBet.value!!) {
            _currentBet.value = _currentBet.value!! + 1
            _playerChips.value = _playerChips.value!! - 1
        }
    }

    fun decreaseBet() {
        if (_currentBet.value!! > 0) {
            _currentBet.value = _currentBet.value!! - 1
            _playerChips.value = _playerChips.value!! + 1
        }
    }


    private fun checkWinner() {
        calculatePoints()
        val playerPoints = playerPoints.value
        val dealerPoints = dealerPoints.value

        if (playerPoints != null) {
            if (dealerPoints != null) {
                if (playerPoints > 21 || dealerPoints > 21) {
                    if (playerPoints > 21) {
                        loseBet()
                    } else {
                        winBet()
                    }
                } else {
                    if (playerPoints > dealerPoints) {
                        winBet()
                    } else if (dealerPoints > playerPoints) {
                        loseBet()
                    } else {
                        draw()
                    }
                }
            }
        }
    }

    fun endGame() {
        calculatePoints()
        checkWinner()
        _gameInProgress.value = false
    }


    /**
     * Handles the player's turn.
     */
    fun playerTurn() {
        // If the deck has cards, add a card to the player's hand
        if (deck.hasCards()) {
            player.hand.add(deck.getCard())
            playerHand.value = player.hand
            calculatePoints()
        }
    }

    /**
     * Handles the dealer's turn.
     */
    private fun dealerTurn() {
        // While the dealer has less than 17 points and the deck has cards, add a card to the dealer's hand
        while (dealer.points < 17 && deck.hasCards()) {
            dealer.hand.add(deck.getCard())
            dealerHand.value = dealer.hand
            calculatePoints()
        }
        endTurn()
    }

    /**
     * Ends the player's turn and starts the dealer's turn.
     */
    fun stand() {
        dealerTurn()
    }

    /**
     * Calculates the points for a player.
     * @param player The player to calculate points for.
     * @return The total points.
     */
    private fun calculateCardPoints(player: Player): Int {
        var total = 0
        var aces = 0

        // Calculate the total points, taking into account the value of aces
        for (card in player.hand) {
            total += if (card.rank != Rank.ACE) {
                if (card.rank.ordinal >= Rank.JACK.ordinal) 10 else card.rank.ordinal + 1
            } else {
                aces++
                card.maxPoints
            }
        }

        // If the total is over 21 and there are aces, subtract 10 for each ace
        while (total > 21 && aces > 0) {
            total -= 10
            aces--
        }

        return total
    }

    /**
     * Calculates the points for both the player and the dealer.
     */
    private fun calculatePoints() {
        for (currentPlayer in listOf(player, dealer)) {
            currentPlayer.points = calculateCardPoints(currentPlayer)
            if (currentPlayer == player) {
                playerPoints.value = currentPlayer.points
            } else {
                dealerPoints.value = currentPlayer.points
            }
        }
    }


    /**
     * Ends the turn and determines the winner.
     */
    private fun endTurn() {
        when {
            player.points > 21 -> winner.value = "Dealer"
            dealer.points > 21 -> winner.value = "Player"
            player.points > dealer.points -> winner.value = "Player"
            dealer.points > player.points -> winner.value = "Dealer"
            else -> winner.value = "Draw"
        }
        isGameOver.value = true
    }

    /**
     * Closes the game over dialog and resets the game.
     */
    fun closeDialog() {
        isGameOver.value = false
        gameReset.value = true
    }


    /**
     * Plays the deal sound.
     *
     * @param context The context to use to create the MediaPlayer.
     */
    fun playDealSound(context: Context) {
        try {
            dealSoundPlayer = MediaPlayer.create(context, R.raw.repartir)
            dealSoundPlayer?.start()
        } catch (e: Exception) {
            Log.e("BlackjackDealerViewModel", "Error playing deal sound: ${e.message}")
        }
    }

    /**
     * Plays the chips sound.
     *
     * @param context The context to use to create the MediaPlayer
     */
    fun betSound(context: Context) {
        try {
            chipSoundPlayer = MediaPlayer.create(context, R.raw.fichas)
            chipSoundPlayer?.start()
        } catch (e: Exception) {
            Log.e("BlackjackDealerViewModel", "Error playing chips sound: ${e.message}")
        }
    }
}