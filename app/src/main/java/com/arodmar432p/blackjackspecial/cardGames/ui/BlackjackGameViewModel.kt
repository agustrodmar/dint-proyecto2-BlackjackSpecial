package com.arodmar432p.blackjackspecial.cardGames.ui

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Card
import com.arodmar432p.blackjackspecial.cardGames.data.Deck
import com.arodmar432p.blackjackspecial.cardGames.data.Player
import com.arodmar432p.blackjackspecial.cardGames.data.Rank

/**
 * A ViewModel class representing the game of Blackjack.
 */
class BlackjackGameViewModel : ViewModel() {
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

    // LiveData objects to hold the game state
    private val _players = MutableLiveData<List<Player>>(emptyList())
    val players: LiveData<List<Player>> get() = _players
    private val _winner = MutableLiveData<Player?>()
    val winner: LiveData<Player?> get() = _winner
    private val _currentTurn = MutableLiveData<Player>()
    val currentTurn: LiveData<Player> get() = _currentTurn
    private val _gameInProgress = MutableLiveData<Boolean>()
    val gameInProgress: LiveData<Boolean> get() = _gameInProgress

    private val _showDialog = MutableLiveData<Boolean>() // To declare the winner
    val showDialog: LiveData<Boolean> get() = _showDialog// To handle my Results screen

    private val _player1Wins = MutableLiveData(0)

    // Para manejar mi pantalla de Resultados
    val player1Wins: LiveData<Int> get() = _player1Wins

    private val _player2Wins = MutableLiveData(0)
    val player2Wins: LiveData<Int> get() = _player2Wins

    private val _eventCloseApp = MutableLiveData<Boolean>()
    val eventCloseApp: LiveData<Boolean> get() = _eventCloseApp

    private var mediaPlayer: MediaPlayer? = null

    private var isPlaying = false


    init {
        _gameInProgress.value = false
        val player1 = Player("Player 1", mutableListOf(), 0)
        val player2 = Player("Player 2", mutableListOf(), 0)
        _players.value = listOf(player1, player2)
        _currentTurn.value = player1
        checkForBlackjack()
        _showDialog.value = false

    }

    /**
     * Starts a new game.
     */
    fun startGame() {
        _winner.value = null
        _showDialog.value = false
        restartGame()
        deck.shuffle()
        startDeal()
        _gameInProgress.value = true
    }

    /**
     * Ends the game and declares the winner.
     * @param winner The winner of the game.
     */
    private fun endGame(winner: Player?) {
        if (winner != null) {
            _winner.value = winner
            _showDialog.value = true
            if (winner.name == "Player 1") {
                _player1Wins.value = (_player1Wins.value ?: 0) + 1
            } else if (winner.name == "Player 2") {
                _player2Wins.value = (_player2Wins.value ?: 0) + 1
            }
        } else {
            _winner.value = null
            _showDialog.value = true
        }
    }

    /**
     * Closes the game over dialog and resets the game.
     */
    fun closeDialog() {
        _showDialog.value = false
        _gameInProgress.value = false
    }

    /**
     * Deals a specified number of cards to each player.
     * @param numCards The number of cards to deal.
     */
    private fun startDeal() {
        for (i in 0 until 2) {
            for (player in _players.value!!) {
                player.hand.add(deck.getCard())
            }
        }
    }

    /**
     * Handles the player's turn when they choose to hit.
     * @param player The player who is hitting.
     */
    fun hitMe(player: Player) {
        Log.d("BlackjackGameViewModel", "Hit Me button pressed for ${player.name}")
        if (_currentTurn.value == player) {
            requestCard(player)
            if (_gameInProgress.value == true) {
                passTurn()
            }
        }
    }

    /**
     * Handles the player's turn when they choose to pass.
     * @param player The player who is passing.
     */
    fun pass(player: Player) {
        if (_currentTurn.value == player) {
            passTurn()
        }
    }

    /**
     * Requests a card for a player.
     * @param player The player who is requesting a card.
     */
    private fun requestCard(player: Player) {
        player.hand.add(deck.getCard())

        if (calculatePoints(player.hand) > 21) {
            endGame(_players.value?.first { it != player }!!)
        }
    }

    /**
     * Passes the turn to the next player.
     */
    private fun passTurn() {
        val currentPlayer = _currentTurn.value
        val nextPlayer = _players.value?.let { players ->
            val currentIndex = players.indexOf(currentPlayer)
            val nextIndex = (currentIndex + 1) % players.size
            players[nextIndex]
        }
        _currentTurn.value = nextPlayer

        if (_currentTurn.value == _players.value?.first()) {
            val currentPlayerPoints = calculatePoints(currentPlayer!!.hand)
            val nextPlayerPoints = calculatePoints(nextPlayer!!.hand)
            val winner = when {
                currentPlayerPoints > 21 -> nextPlayer
                nextPlayerPoints > 21 -> currentPlayer
                currentPlayerPoints > nextPlayerPoints -> currentPlayer
                currentPlayerPoints < nextPlayerPoints -> nextPlayer
                else -> null  // // This is how I plan to handle a tie
            }
            endGame(winner)
        }
    }


    /**
     * Calculates the points for a single card.
     * @param card The card to calculate points for.
     * @return The points value of the card.
     */
    private fun calculateCardPoints(card: Card): Int {
        return if (card.rank != Rank.ACE) {
            if (card.rank.ordinal >= Rank.JACK.ordinal) 10 else card.rank.ordinal + 1
        } else {
            card.maxPoints
        }
    }

    /**
     * Calculates the total points for a hand of cards.
     * @param hand The hand of cards to calculate points for.
     * @return The total points of the hand.
     */
    fun calculatePoints(hand: List<Card>): Int {
        var total = 0
        var aces = 0

        // Calculate the total points, taking into account the value of aces
        for (card in hand) {
            total += calculateCardPoints(card)
            if (card.rank == Rank.ACE) {
                aces++
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
     * Checks for a blackjack at the start of the game.
     */
    private fun checkForBlackjack() {
        for (player in _players.value!!) {
            if (calculatePoints(player.hand) == 21) {
                _winner.value = player
                _showDialog.value = true
                return
            }
        }
    }

    /**
     * Restarts the game by clearing the players' hands and resetting the game state.
     */
    private fun restartGame() {
        for (player in _players.value!!) {
            player.hand.clear()
        }
        _currentTurn.value = _players.value?.first()
        _gameInProgress.value = false
    }

    /**
     * Toggles the music on and off.
     * @param context The context to use to create the MediaPlayer.
     */
    fun toggleMusic(context: Context) {
        try {
            if (isPlaying) {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                isPlaying = false
            } else {
                mediaPlayer = MediaPlayer.create(context, R.raw.blackjack)
                mediaPlayer?.start()
                isPlaying = true
            }
        } catch (e: Exception) {
            Log.e("BlackjackGameViewModel", "Error al reproducir la música: ${e.message}")
        }
    }

    /**
     * Stops the music.
     */
    private fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    /**
     * Stops the music when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        stopMusic()
    }

    /**
     * Closes the app.
     */
    fun closeApp() {
        _eventCloseApp.value = true
    }

    /**
     * Resets the close app event after the app is closed.
     */
    fun onAppClosed() {
        _eventCloseApp.value = false
    }
}