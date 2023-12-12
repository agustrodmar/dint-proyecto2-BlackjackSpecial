package com.arodmar432p.blackjackspecial.cardGames.data

import androidx.annotation.DrawableRes


/**
 * A data class representing a card in the game of Blackjack.
 * @property rank The rank of the card.
 * @property suit The suit of the card.
 * @property minPoints The minimum points the card can contribute in the game.
 * @property maxPoints The maximum points the card can contribute in the game.
 * @property idDrawable The drawable resource ID of the card image, if available.
 */
data class Card(
    val rank: Rank,
    val suit: Suit,
    val minPoints: Int,
    val maxPoints: Int,
    @DrawableRes val idDrawable: Int?
)