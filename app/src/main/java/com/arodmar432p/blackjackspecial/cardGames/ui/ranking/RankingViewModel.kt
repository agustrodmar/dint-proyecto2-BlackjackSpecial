package com.arodmar432p.blackjackspecial.cardGames.ui.ranking


import android.util.Log
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.cardGames.data.Ranking
import com.arodmar432p.blackjackspecial.cardGames.repository.RankingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * This class handles the ranking data.
 *
 * @param rankingRepository The repository that this ViewModel will use to handle ranking tasks.
 */
class RankingViewModel(private val rankingRepository: RankingRepository) : ViewModel() {

    private val _rankings = MutableStateFlow<List<Ranking>>(emptyList())
    val rankings: StateFlow<List<Ranking>> get() = _rankings

    init {
        getRanking()
    }

    /**
     * This function retrieves the rankings from the repository.
     * It updates the `_rankings` value with the retrieved rankings.
     */
    fun getRanking() {
        rankingRepository.getRankings().addOnSuccessListener { querySnapshot ->
            val rankings = querySnapshot.documents.mapNotNull { it.toObject(Ranking::class.java) }
            _rankings.value = rankings
            Log.d("RankingViewModel", "Rankings cargados: $rankings")
        }
    }
}
