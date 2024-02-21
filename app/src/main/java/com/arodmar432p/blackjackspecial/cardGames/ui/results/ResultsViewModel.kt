package com.arodmar432p.blackjackspecial.cardGames.ui.results


import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.arodmar432p.blackjackspecial.cardGames.repository.UserRepository


class ResultsViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user = userRepository.getUser()

    fun saveUser(user: User) {
        userRepository.saveUser(user)
    }
}