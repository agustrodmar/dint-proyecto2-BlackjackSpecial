package com.arodmar432p.blackjackspecial.ui

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.data.Card
import com.arodmar432p.blackjackspecial.data.Deck
import com.arodmar432p.blackjackspecial.data.Player
import com.arodmar432p.blackjackspecial.data.Rank

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

    init {
        _gameInProgress.value = false
        val player1 = Player("Player 1", mutableListOf(), 0)
        val player2 = Player("Player 2", mutableListOf(), 0)
        _players.value = listOf(player1, player2)
        _currentTurn.value = player1
    }

    fun startGame() {
        restartGame()
        deck.shuffle()
        startDeal(2)
        for (player in _players.value!!) {
            if (calculatePoints(player.hand) == 21) {
                _winner.value = player
                _gameInProgress.value = false
                return
            }
        }
        _gameInProgress.value = true
    }

    fun startDeal(numCards: Int) {
        for (i in 0 until numCards) {
            for (player in _players.value!!) {
                player.hand.add(deck.getCard())
            }
        }
    }

    fun hitMe(player: Player) {
        if (_currentTurn.value == player) {
            requestCard(player)
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
            _winner.value = _players.value?.first { it != player }
            _gameInProgress.value = false
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
            _winner.value = when {
                currentPlayerPoints > 21 -> nextPlayer
                nextPlayerPoints > 21 -> currentPlayer
                currentPlayerPoints > nextPlayerPoints -> currentPlayer
                else -> nextPlayer
            }
            _gameInProgress.value = false
        }
    }

    fun calculatePoints(hand: List<Card>): Int {
        var total = 0
        var aces = 0

        for (card in hand) {
            total += if (card.rank != Rank.ACE) {
                card.maxPoints
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

    fun restartGame() {
        for (player in _players.value!!) {
            player.hand.clear()
        }
        _currentTurn.value = _players.value?.first()
        _gameInProgress.value = false
    }
}

