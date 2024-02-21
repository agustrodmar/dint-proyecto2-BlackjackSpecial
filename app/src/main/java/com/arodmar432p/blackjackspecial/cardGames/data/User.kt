package com.arodmar432p.blackjackspecial.cardGames.data


data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val age: Int = 0,
    val hoursInApp: Int = 0,
    var gamesPlayed: Int = 0,
    var victories: Int = 0
)