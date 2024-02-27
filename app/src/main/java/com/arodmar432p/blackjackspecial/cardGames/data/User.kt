package com.arodmar432p.blackjackspecial.cardGames.data


/**
 * Represents a user in the Firestore collection.
 *
 * @property uid Unique identifier for the user. Default value is an empty string.
 * @property username The username of the user. Default value is an empty string.
 * @property email The email address of the user. Default value is an empty string.
 * @property age The age of the user. Default value is 0.
 * @property hoursInApp The total number of hours the user has spent in the app. Default value is 0.
 * @property gamesPlayed The total number of games the user has played. This is a mutable property
 * and its default value is 0.
 * @property victories The total number of games the user has won. This is a mutable property
 * and its default value is 0.
 */
data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val age: Int = 0,
    val hoursInApp: Int = 0,
    var gamesPlayed: Int = 0,
    var victories: Int = 0
)