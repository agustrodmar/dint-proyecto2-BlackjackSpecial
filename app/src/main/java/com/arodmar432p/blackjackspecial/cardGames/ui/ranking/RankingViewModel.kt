package com.arodmar432p.blackjackspecial.cardGames.ui.ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.cardGames.data.Ranking
import com.arodmar432p.blackjackspecial.cardGames.repository.RankingRepository


class RankingViewModel(private val rankingRepository: RankingRepository) : ViewModel() {

    private val _rankings = MutableLiveData<List<Ranking>>()
    val rankings: LiveData<List<Ranking>> get() = _rankings

    init {
        getRanking()
    }

    private fun getRanking() {
        rankingRepository.getRankings().addOnSuccessListener { querySnapshot ->
            val rankings = querySnapshot.documents.mapNotNull { it.toObject(Ranking::class.java) }
            _rankings.value = rankings
        }
    }
}