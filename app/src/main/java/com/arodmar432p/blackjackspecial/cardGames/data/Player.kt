package com.arodmar432p.blackjackspecial.cardGames.data

/**
 * A data class representing a player in the game of Blackjack.
 *
 * @property name The name of the player.
 * @property hand The cards that the player holds.
 * @property points The points that the player has.
 */
data class Player(
    val name: String,
    var hand: MutableList<Card> = mutableListOf(),
    var points: Int
)
