package com.arodmar432p.blackjackspecial.cardGames.ui.ranking

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.arodmar432p.blackjackspecial.cardGames.data.Ranking

@Composable
fun RankingScreen(rankingViewModel: RankingViewModel) {
    val rankings: List<Ranking> by rankingViewModel.rankings.observeAsState(initial = emptyList())

    LazyColumn {
        items(rankings) { ranking ->
            Text(text = "Username: ${ranking.username}")
            Text(text = "Victories: ${ranking.victories}")
            Divider()
        }
    }
}
