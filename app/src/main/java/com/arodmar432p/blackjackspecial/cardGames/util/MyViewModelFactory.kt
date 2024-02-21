package com.arodmar432p.blackjackspecial.cardGames.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import com.arodmar432p.blackjackspecial.cardGames.repository.UserRepository
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.AuthViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackDealerViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackvs2.BlackjackGameViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.highestcard.HighestCardViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsViewModel

class MyViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(userRepository) as T
        }
        if (modelClass.isAssignableFrom(BlackjackDealerViewModel::class.java)) {
            return BlackjackDealerViewModel(userRepository) as T
        }
        if (modelClass.isAssignableFrom(ResultsViewModel::class.java)) {
            return ResultsViewModel(userRepository) as T
        }
        if (modelClass.isAssignableFrom(BlackjackGameViewModel::class.java)) {
            // Asegúrate de pasar las dependencias correctas al constructor
            return BlackjackGameViewModel(/* tus dependencias aquí */) as T
        }
        if (modelClass.isAssignableFrom(HighestCardViewModel::class.java)) {
            // Asegúrate de pasar las dependencias correctas al constructor
            return HighestCardViewModel(/* tus dependencias aquí */) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
