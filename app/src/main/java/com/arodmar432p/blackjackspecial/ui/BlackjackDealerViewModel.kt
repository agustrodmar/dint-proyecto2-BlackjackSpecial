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
    private val _players = MutableLiveData<List<Player>>()
    private val _winner = MutableLiveData<Player>()
    private val _showDialog = MutableLiveData<Boolean>()
    private val _currentTurn = MutableLiveData<Player>()
    private val _gameInProgress = MutableLiveData<Boolean>()
    private val deck = Deck()
    private val dealer = Player("Dealer", mutableListOf(), 0)

    // Exponlas a través de métodos públicos
    val players: LiveData<List<Player>> get() = _players
    val winner: LiveData<Player> get() = _winner
    val showDialog: LiveData<Boolean> get() = _showDialog
    val gameInProgress: LiveData<Boolean> get() = _gameInProgress

    init {
        startGame()
    }

    private fun calculatePoints(hand: List<Card>): Int {
        var total = 0
        var aces = 0

        for (card in hand) {
            total += if (card.rank != Rank.ACE) {
                if (card.rank.ordinal >= Rank.JACK.ordinal) 10 else card.rank.ordinal + 1
            } else {
                aces++
                11  // Considera el as como 11 puntos inicialmente
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10  // Si la suma total es mayor a 21 y hay ases, considera cada as como 1 punto
            aces--
        }

        return total
    }

    private fun checkForBlackjack() {
        _players.value?.let { players ->
            players.find { calculatePoints(it.hand) == 21 }?.let { player ->
                _winner.value = player
                _showDialog.value = true
            }
        }
    }

    private fun dealerTurn() {
        while (calculatePoints(dealer.hand) < 17) {
            dealer.hand.add(deck.getCard())
        }
    }

    private fun determineWinner() {
        val playerPoints = _players.value?.first()?.let { calculatePoints(it.hand) }
        val dealerPoints = calculatePoints(dealer.hand)
        if (playerPoints != null) {
            _winner.value = when {
                playerPoints > 21 -> dealer
                dealerPoints > 21 -> _players.value?.first()
                playerPoints > dealerPoints -> _players.value?.first()
                else -> dealer
            }
        }
    }

    fun startGame() {
        for (player in _players.value!!) {
            player.hand.clear()
        }
        dealer.hand.clear()
        deck.shuffle()
        _players.value?.first()?.hand?.add(deck.getCard())
        dealer.hand.add(deck.getCard())
        _players.value?.first()?.hand?.add(deck.getCard())
        dealer.hand.add(deck.getCard())
        checkForBlackjack()
    }

    fun playerTurn() {
        _players.value?.first()?.hand?.add(deck.getCard())
        if (_players.value?.first()?.let { calculatePoints(it.hand) }!! > 21) {
            dealerTurn()
            determineWinner()
        }
    }

    private fun passTurn() {
        val currentPlayer = _currentTurn.value
        if (currentPlayer == dealer) {
            dealerTurn()
            determineWinner()
        } else {
            _currentTurn.value = dealer
        }
    }

    private fun requestCard(player: Player) {
        player.hand.add(deck.getCard())
    }

    fun hitMe() {
        _players.value?.first()?.hand?.add(deck.getCard())
        if (_players.value?.first()?.let { calculatePoints(it.hand) }!! > 21) {
            dealerTurn()
            determineWinner()
        }
    }

    fun stand() {
        passTurn()
    }


    fun endPlayerTurn() {
        dealerTurn()
        determineWinner()
    }
}