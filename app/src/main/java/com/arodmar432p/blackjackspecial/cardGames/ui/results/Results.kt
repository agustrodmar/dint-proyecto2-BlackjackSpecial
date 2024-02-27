package com.arodmar432p.blackjackspecial.cardGames.ui.results


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A composable function to display the results screen.
 *
 * @param gameViewModel The ViewModel for the game.
 */
@Composable
fun ResultsScreen(resultsViewModel: ResultsViewModel) {

    val user by resultsViewModel.user.collectAsState()
    val isLoading by resultsViewModel.isLoading.collectAsState()

    ResultsWallpaper(ResultsViewModel())

    LaunchedEffect(Unit) {
        val uid = Firebase.auth.currentUser?.uid
        if (uid != null) {
            resultsViewModel.getUser(uid)
        }
    }

    UserResults(user = user, isLoading = isLoading)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "avatar de usuario",
            modifier = Modifier
                .size(65.dp)
                .height(20.dp)
                .width(20.dp)
                .offset(x = 780.dp, y = 385.dp),
        )
    }

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
fun UserResults(user: User?, isLoading: Boolean) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Text(text = "Cargando datos del usuario...", color = Color.Black,
                modifier = Modifier.offset(y = 390.dp))
        } else if (user != null) {
            Text(text = user.username, color = (Color(0xFFEAEFC4)),
                fontWeight = FontWeight.Bold, fontSize = 30.sp,
                modifier = Modifier.offset(x= (-25).dp, y = 380.dp))
            Text(text = "Horas de juego: ${user.hoursInApp}", color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x= (100).dp, y = 433.dp))
            Text(text = "Total partidas: ${user.gamesPlayed}", color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x= (-105).dp, y = 415.dp))
            Text(text = "Victorias: ${user.victories}", color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x= (-128).dp, y = 450.dp) )
        } else {
            Text(text = "No se encontraron datos del usuario.", color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(y = 390.dp) )
        }
    }
}

@Composable
fun ResultsWallpaper(resultsViewModel: ResultsViewModel){


    val user by resultsViewModel.user.collectAsState()

    SideEffect {
        Log.d("ResultsScreen", "ResultsScreen se ha recomponido. Datos del usuario: $user")
    }

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
    ResultsWallpaper(ResultsViewModel())
}