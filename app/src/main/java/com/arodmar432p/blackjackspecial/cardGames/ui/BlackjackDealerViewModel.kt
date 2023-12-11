package com.arodmar432p.blackjackspecial.cardGames.data.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.cardGames.data.Card
import com.arodmar432p.blackjackspecial.cardGames.data.Deck
import com.arodmar432p.blackjackspecial.cardGames.data.Player

class BlackjackDealerViewModel : ViewModel() {
    val deck = Deck()
    val player = Player("Player", mutableListOf(), 0)
    val dealer = Player("Dealer", mutableListOf(), 0)
    val winner = MutableLiveData<String>()
    val playerPoints = MutableLiveData<Int>()
    val dealerPoints = MutableLiveData<Int>()
    val playerHand = MutableLiveData<List<Card>>()
    val dealerHand = MutableLiveData<List<Card>>()
    val gameInProgress = MutableLiveData<Boolean>()

    fun startGame() {
        deck.shuffle()
        player.hand.clear()
        dealer.hand.clear()
        player.hand.add(deck.getCard())
        dealer.hand.add(deck.getCard())
        player.hand.add(deck.getCard())
        dealer.hand.add(deck.getCard())
        playerHand.value = player.hand
        dealerHand.value = dealer.hand
        gameInProgress.value = true
        calculatePoints()
    }



    fun playerTurn() {
        if (deck.hasCards()) {
            player.hand.add(deck.getCard())
            playerHand.value = player.hand
            calculatePoints()
        }
    }

    fun dealerTurn() {
        while (dealer.points < 17 && deck.hasCards()) {
            dealer.hand.add(deck.getCard())
            dealerHand.value = dealer.hand
            calculatePoints()
        }
        endTurn()
    }

    fun stand() {
        dealerTurn()
    }

    private fun calculatePoints() {
        player.points = player.hand.sumOf { it.maxPoints }
        dealer.points = dealer.hand.sumOf { it.maxPoints }
        playerPoints.value = player.points
        dealerPoints.value = dealer.points
    }

    private fun endTurn() {
        when {
            player.points > 21 -> winner.value = "Dealer"
            dealer.points > 21 -> winner.value = "Player"
            player.points > dealer.points -> winner.value = "Player"
            dealer.points > player.points -> winner.value = "Dealer"
            else -> winner.value = "Draw"
        }
        gameInProgress.value = false
    }
}