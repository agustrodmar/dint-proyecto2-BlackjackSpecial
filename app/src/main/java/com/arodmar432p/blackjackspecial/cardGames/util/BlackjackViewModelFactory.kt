package com.arodmar432p.blackjackspecial.cardGames.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arodmar432p.blackjackspecial.cardGames.repository.AvatarRepository
import com.arodmar432p.blackjackspecial.cardGames.repository.RankingRepository
import com.arodmar432p.blackjackspecial.cardGames.repository.UserRepository
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.AuthViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackDealerViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.ranking.RankingViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsViewModel


/**
 * A ViewModelProvider.Factory that creates ViewModels with custom constructors.
 *
 * @property userRepository The UserRepository to pass to the ViewModels.
 * @property rankingRepository The RankingRepository to pass to the ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class BlackjackViewModelFactory(
    private val userRepository: UserRepository,
    private val rankingRepository: RankingRepository,
    private val avatarRepository: AvatarRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(BlackjackDealerViewModel::class.java) -> {
                BlackjackDealerViewModel(userRepository, rankingRepository) as T
            }
            modelClass.isAssignableFrom(ResultsViewModel::class.java) -> {
                ResultsViewModel(avatarRepository) as T
            }
            modelClass.isAssignableFrom(RankingViewModel::class.java) -> {
                RankingViewModel(rankingRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}