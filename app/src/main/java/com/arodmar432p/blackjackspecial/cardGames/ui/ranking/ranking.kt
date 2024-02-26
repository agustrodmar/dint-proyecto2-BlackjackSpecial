package com.arodmar432p.blackjackspecial.cardGames.ui.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Ranking
import com.arodmar432p.blackjackspecial.cardGames.repository.RankingRepository
import com.arodmar432p.blackjackspecial.cardGames.repository.UserRepository
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.AuthViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackDealerViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsViewModel


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

    RankingWallpaper(
        BlackjackDealerViewModel(UserRepository(), RankingRepository()), AuthViewModel(
            UserRepository()
        )
    )
}


@Composable
fun LeaderboardImage(maxWidth: Dp, maxHeight: Dp) {
    val leaderboardImage: Painter = painterResource(id = R.drawable.leaderboardwallpaper)

    Box(
        modifier = Modifier
            .size(maxWidth, maxHeight)
    ) {
        Image(
            painter = leaderboardImage,
            contentDescription = "The Leaderboard",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 78.dp, y = 40.dp)
                .border(1.dp, Color(0xFFEAEFC4), RectangleShape),
            contentScale = ContentScale.Fit
        )
    }
}



@Composable
fun RankingWallpaper(blackjackDealerViewModel: BlackjackDealerViewModel, authViewModel: AuthViewModel){


    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.wallpaper3),
            contentDescription = "El wallpaper de Results",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Image(
            painter = painterResource(id = R.drawable.texturawallpaper),
            contentDescription = "Textura de un tapete",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.02f)
        )

        Box(
            modifier = Modifier
                .height(450.dp)
                .width(550.dp)
                .align(Alignment.Center)


        ) {
            Image(
                painter = painterResource(id = R.drawable.backgroundmadera),
                contentDescription = "Tabla de madera sobre la que se situa el Leaderboard",
                modifier = Modifier
                    .size(450.dp)
                    .height(450.dp)
                    .offset(y = 50.dp, x = 50.dp)
                    .border(1.dp, Color(0xFFEAEFC4), RectangleShape),
                contentScale = ContentScale.Crop
            )

            LeaderboardImage(maxWidth = 390.dp, maxHeight = 650.dp)
        }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "The App logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(350.dp)
                .offset(y = (-30).dp)
        )
    }
}