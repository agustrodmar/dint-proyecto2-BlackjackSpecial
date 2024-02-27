package com.arodmar432p.blackjackspecial.cardGames.data

/**
 * The data class that represents
 * the ranking in the game.
 *
 * @property uid hashcode that represents the user ID.
 * @property username username login in the App.
 * @property victories number of victories the user has collected.
 */
data class Ranking(
    val uid: String = "",
    val username: String = "",
    val victories: Int = 0
)