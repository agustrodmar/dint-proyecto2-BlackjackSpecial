package com.arodmar432p.blackjackspecial.cardGames.ui.ranking

import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.cardGames.data.Ranking
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.arodmar432p.blackjackspecial.cardGames.repository.RankingRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class RankingViewModel(private val rankingRepository: RankingRepository) : ViewModel() {

    fun updateRanking(user: User) {
        val ranking = Ranking(user.uid, user.username, user.victories + 1)
        rankingRepository.saveRanking(ranking)
    }

    fun getRanking(): Task<QuerySnapshot> {
        return rankingRepository.getRankings()
    }
}