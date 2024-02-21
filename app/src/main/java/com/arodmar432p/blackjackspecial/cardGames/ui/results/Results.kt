package com.arodmar432p.blackjackspecial.cardGames.ui.results


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.AuthViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackDealerViewModel

/**
 * A composable function to display the results screen.
 *
 * @param gameViewModel The ViewModel for the game.
 */
@Composable
fun ResultsScreen(blackjackDealerViewModel: BlackjackDealerViewModel, authViewModel: AuthViewModel, resultsViewModel: ResultsViewModel) {
    val user by resultsViewModel.user.observeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Resultados", color = Color.White, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        if (user != null) {
            Text(text = "Horas jugadas: ${user!!.hoursInApp}", color = Color.White)
            Text(text = "Partidas jugadas: ${user!!.gamesPlayed}", color = Color.White)
            Text(text = "Victorias: ${user!!.victories}", color = Color.White)
        }
    }

    ResultsWallpaper()
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
            contentDescription = "El Leaderboard",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 78.dp, y = 40.dp)
                .border(1.dp, Color(0xFFEAEFC4), RectangleShape),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun ResultsWallpaper(){

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
@Preview(showBackground = true, name = "Preview AuthScreenWallpaper", widthDp = 1920, heightDp = 1080)
@Composable
fun ResultsScreenPreview(){
    ResultsWallpaper()
}