package com.arodmar432p.blackjackspecial.ui

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.data.Card
import com.arodmar432p.blackjackspecial.data.Deck
import com.arodmar432p.blackjackspecial.data.Player
import com.arodmar432p.blackjackspecial.data.Rank

class BlackjackDealerViewModel : ViewModel() {
    private val deck = Deck()

    val Dealer = Player("La mesa", mutableListOf(), 0)
    val player = Player("El jugador", mutableListOf(), 0)

    private val _players = MutableLiveData<List<Player>>(listOf(Dealer, player))
    val players: LiveData<List<Player>> get() = _players

    private val _winner = MutableLiveData<Player?>()
    val winner: LiveData<Player?> get() = _winner

    private val _currentTurn = MutableLiveData<Player>(Dealer)
    val currentTurn: LiveData<Player> get() = _currentTurn

    private val _gameInProgress = MutableLiveData<Boolean>(false)
    val gameInProgress: LiveData<Boolean> get() = _gameInProgress

    private val _showDialog = MutableLiveData<Boolean>(false)
    val showDialog: LiveData<Boolean> get() = _showDialog

    private val _DealerWins = MutableLiveData<Int>(0)
    val DealerWins: LiveData<Int> get() = _DealerWins

    private val _playerWins = MutableLiveData<Int>(0)
    val playerWins: LiveData<Int> get() = _playerWins

    private val _eventCloseApp = MutableLiveData<Boolean>()
    val eventCloseApp: LiveData<Boolean> get() = _eventCloseApp

    init {
        checkForBlackjack()
    }


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
        _winner.value = winner
        _showDialog.value = true
        if (winner?.name == "La mesa") {
            _DealerWins.value = (_DealerWins.value ?: 0) + 1
        } else if (winner?.name == "El jugador") {
            _playerWins.value = (_playerWins.value ?: 0) + 1
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
        Log.d("BlackjackDealerViewModel", "Hit Me button pressed for ${player.name}")
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

        if (_currentTurn.value == Dealer && calculatePoints(Dealer.hand) < 17) {
            hitMe(Dealer)
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

}

