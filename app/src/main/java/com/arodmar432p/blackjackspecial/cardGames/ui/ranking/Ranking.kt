package com.arodmar432p.blackjackspecial.cardGames.ui.ranking

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Ranking


/**
 * This function displays the ranking screen.
 *
 * @param rankingViewModel The ViewModel that this function will use to handle ranking tasks.
 */
@Composable
fun RankingScreen(rankingViewModel: RankingViewModel) {

    LaunchedEffect(Unit) {
        rankingViewModel.getRanking()
    }

    val rankings: List<Ranking> by rankingViewModel.rankings.collectAsState()

    Log.d("RankingScreen", "Datos de ranking: $rankings")

    BoxWithConstraints {
        val maxWidth = this.maxWidth
        val maxHeight = this.maxHeight

        RankingWallpaper(rankings, maxWidth, maxHeight)
        AppLogo()
    }
}

@Composable
fun AppLogo(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "The App logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(380.dp)
                .offset(y = (-40).dp)
        )
    }

}


/**
 * This function displays the ranking wallpaper.
 *
 * @param rankings The list of rankings that this function will display.
 */
@Composable
fun RankingWallpaper(rankings: List<Ranking>, maxWidth: Dp, maxHeight: Dp) {

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.wallpaper3),
            contentDescription = "Ranking wallpaper",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Image(
            painter = painterResource(id = R.drawable.texturawallpaper),
            contentDescription = "The texture",
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
                contentDescription = "wood background texture for the leaaderboard",
                modifier = Modifier
                    .size(450.dp)
                    .height(450.dp)
                    .offset(y = 50.dp, x = 50.dp)
                    .border(1.dp, Color(0xFFEAEFC4), RectangleShape),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .height(740.dp)
                .width(550.dp)
                .align(Alignment.Center)
                .offset(y = 100.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy((-6).dp)
            ) {
                items(rankings) { ranking ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .paint(painterResource(id = R.drawable.wallpaperranking))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(54.dp),
                        ) {
                            Text(
                                text = ranking.username,
                                style = TextStyle(color = Color(0xFFEAEFC4),
                                    fontWeight = FontWeight.Bold, fontSize = 30.sp),
                                modifier = Modifier
                                    .weight(1f)
                                    .offset((-25).dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Victorias: ${ranking.victories}",
                                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .padding(10.dp)
                                    .weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}