package com.arodmar432p.blackjackspecial.cardGames.ui


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

/**
 * A composable function to display the results screen.
 *
 * @param gameViewModel The ViewModel for the game.
 */
@Composable
fun ResultsScreen(gameViewModel: BlackjackGameViewModel, authViewModel: AuthViewModel) {
    val player1Wins by gameViewModel.player1Wins.observeAsState(initial = 0)
    val player2Wins by gameViewModel.player2Wins.observeAsState(initial = 0)
    val user by authViewModel.userState.observeAsState()

    ResultsWallpaper()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Resultados 1vs1", color = Color.White, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Victorias del Jugador 1: $player1Wins", color = Color.White)
        Text(text = "Victorias del Jugador 2: $player2Wins", color = Color.White)
        if (user != null) {
            Text(text = "Horas jugadas: ${user!!.hoursInApp}", color = Color.White)
            Text(text = "Partidas jugadas: ${user!!.gamesPlayed}", color = Color.White)
            Text(text = "Partidas ganadas: ${user!!.victories}", color = Color.White)
        }
    }
}

@Composable
fun LeaderboardImage(maxWidth: Dp, maxHeight: Dp) {
    val leaderboardImage: Painter = painterResource(id = R.drawable.leaderboard)

    Box(
        modifier = Modifier
            .size(maxWidth, maxHeight)
    ) {
        Image(
            painter = leaderboardImage,
            contentDescription = "El Leaderboard",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 78.dp)
                .border(1.dp, Color(0xFFEAEFC4), RectangleShape),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun ResultsWallpaper(){

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.wallpaper1),
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
                .alpha(0.08f)
        )

        Box(
            modifier = Modifier
                .height(450.dp) // Reduce este valor para hacer la tabla de madera más pequeña
                .width(550.dp) // Reduce este valor para hacer la tabla de madera más pequeña
                .align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.backgroundmadera),
                contentDescription = "Tabla de madera sobre la que se situa el Leaderboard",
                modifier = Modifier
                    .size(450.dp)
                    .height(450.dp)
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