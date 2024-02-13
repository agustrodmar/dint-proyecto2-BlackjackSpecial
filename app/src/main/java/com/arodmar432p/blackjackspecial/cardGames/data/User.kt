package com.arodmar432p.blackjackspecial.cardGames.data

data class User(
    val uid: String,
    val username: String,
    val email: String,
    val age: Int,
    val hoursInApp: Int,
    val gamesPlayed: Int,
    val victories: Int
)
