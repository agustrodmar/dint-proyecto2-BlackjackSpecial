package com.arodmar432p.blackjackspecial.data


data class Card(
    val rank: Rank,
    val suit: Suit,
    val minPoints: Int,
    val maxPoints: Int,
    val idDrawable: Int,
)