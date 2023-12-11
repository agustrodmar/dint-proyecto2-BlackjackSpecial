package com.arodmar432p.blackjackspecial.ui

import android.app.Application
import android.util.Log

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.cardGames.data.Card
import com.arodmar432p.blackjackspecial.cardGames.data.Deck
import com.arodmar432p.blackjackspecial.cardGames.data.Player
import com.arodmar432p.blackjackspecial.cardGames.data.Rank

class BlackjackGameViewModel : ViewModel() {
    private val deck = Deck()
    private val _players = MutableLiveData<List<Player>>(emptyList())
    val players: LiveData<List<Player>> get() = _players
    private val _winner = MutableLiveData<Player?>()
    val winner: LiveData<Player?> get() = _winner
    private val _currentTurn = MutableLiveData<Player>()
    val currentTurn: LiveData<Player> get() = _currentTurn
    private val _gameInProgress = MutableLiveData<Boolean>()
    val gameInProgress: LiveData<Boolean> get() = _gameInProgress

    private val _showDialog = MutableLiveData<Boolean>() // Para declarar el ganador
    val showDialog: LiveData<Boolean> get() = _showDialog

    private val _player1Wins = MutableLiveData<Int>(0)

    // Para manejar mi pantalla de Resultados
    val player1Wins: LiveData<Int> get() = _player1Wins

    private val _player2Wins = MutableLiveData<Int>(0)
    val player2Wins: LiveData<Int> get() = _player2Wins

    private val _eventCloseApp = MutableLiveData<Boolean>()
    val eventCloseApp: LiveData<Boolean> get() = _eventCloseApp




    init {
        _gameInProgress.value = false
        val player1 = Player("Player 1", mutableListOf(), 0)
        val player2 = Player("Player 2", mutableListOf(), 0)
        _players.value = listOf(player1, player2)
        _currentTurn.value = player1
        checkForBlackjack()
        _showDialog.value = false

    }

    fun startGame() {
        _winner.value = null
        _showDialog.value = false
        restartGame()
        deck.shuffle()
        startDeal(2)
        _gameInProgress.value = true
    }
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

    fun closeDialog() {
        _showDialog.value = false
        _gameInProgress.value = false
    }

    fun startDeal(numCards: Int) {
        for (i in 0 until numCards) {
            for (player in _players.value!!) {
                player.hand.add(deck.getCard())
            }
        }
    }

    fun hitMe(player: Player) {
        Log.d("BlackjackGameViewModel", "Hit Me button pressed for ${player.name}")
        if (_currentTurn.value == player) {
            requestCard(player)
            if (_gameInProgress.value == true) {
                passTurn()
            }
        }
    }

    fun pass(player: Player) {
        if (_currentTurn.value == player) {
            passTurn()
        }
    }

    private fun requestCard(player: Player) {
        player.hand.add(deck.getCard())

        if (calculatePoints(player.hand) > 21) {
            endGame(_players.value?.first { it != player }!!)
        }
    }

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
                else -> null  // Con esto pienso manejar el empate
            }
            endGame(winner)
        }
    }


    fun calculatePoints(hand: List<Card>): Int {
        var total = 0
        var aces = 0

        for (card in hand) {
            total += if (card.rank != Rank.ACE) {
                if (card.rank.ordinal >= Rank.JACK.ordinal) 10 else card.rank.ordinal + 1
            } else {
                aces++
                card.maxPoints
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10
            aces--
        }

        return total
    }

    fun checkForBlackjack() {
        for (player in _players.value!!) {
            if (calculatePoints(player.hand) == 21) {
                _winner.value = player
                _showDialog.value = true
                return
            }
        }
    }

    fun restartGame() {
        for (player in _players.value!!) {
            player.hand.clear()
        }
        _currentTurn.value = _players.value?.first()
        _gameInProgress.value = false
    }

    fun closeApp() {
        _eventCloseApp.value = true
    }

    fun onAppClosed() {
        _eventCloseApp.value = false
    }
}

