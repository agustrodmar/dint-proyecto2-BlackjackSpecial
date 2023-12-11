package com.arodmar432p.blackjackspecial.cardGames.data


data class Card(
    val rank: Rank,
    val suit: Suit,
    val minPoints: Int,
    val maxPoints: Int,
    val idDrawable: String,
)