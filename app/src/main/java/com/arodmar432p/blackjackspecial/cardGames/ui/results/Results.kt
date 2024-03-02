package com.arodmar432p.blackjackspecial.cardGames.ui.results


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
 * @param resultsViewModel The ViewModel for showing the results.
 */
@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ResultsScreen(resultsViewModel: ResultsViewModel){
    BoxWithConstraints {
        val maxWidth = this.maxWidth
        val maxHeight = this.maxHeight
        val user by resultsViewModel.user.collectAsState()
        val isLoading by resultsViewModel.isLoading.collectAsState()
        val userAvatar = resultsViewModel.userAvatar.value

        ResultsWallpaper(resultsViewModel)

        LaunchedEffect(Unit) {
            val uid = Firebase.auth.currentUser?.uid
            if (uid != null) {
                Log.d("ResultsScreen", "Fetching user data for uid: $uid")
                resultsViewModel.getUser(uid)
            } else {
                Log.d("ResultsScreen", "User is not logged in")
            }
        }

        UserResults(user = user, avatarBitmap = userAvatar, isLoading = isLoading, maxWidth, maxHeight)
    }
}

/**
 * A composable function that displays a user's avatar.
 *
 * @param avatarBitmap The [Bitmap] of the user's avatar.
 */
@Composable
fun UserAvatar(avatarBitmap: Bitmap?) {
    avatarBitmap?.let {
        val imageBitmap = it.asImageBitmap()
        Image(bitmap = imageBitmap, contentDescription = "User Avatar", modifier = Modifier.size(200.dp))
        Log.d("UserAvatar", "Avatar image displayed")
    } ?: run {
        Log.d("UserAvatar", "Avatar image is null")
    }
}

/**
 * This function displays the leaderboard image.
 *
 * @param maxWidth The maximum width of the leaderboard image.
 * @param maxHeight The maximum height of the leaderboard image.
 */
@Composable
fun ResultsImage(maxWidth: Dp, maxHeight: Dp) {
    val leaderboardImage: Painter = painterResource(id = R.drawable.leaderboardwallpaper)

    Box(
        modifier = Modifier
            .size(maxWidth, maxHeight)
    ) {
        Image(
            painter = leaderboardImage,
            contentDescription = "Results board",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 78.dp, y = 40.dp)
                .border(1.dp, Color(0xFFEAEFC4), RectangleShape),
            contentScale = ContentScale.Fit
        )
    }
}

/**
 * This function displays the user results.
 *
 * @param user The user whose results will be displayed.
 * @param isLoading A boolean indicating whether the user data is still loading.
 */
@Composable
fun UserResults(user: User?, avatarBitmap: Bitmap?, isLoading: Boolean, maxWidth: Dp, maxHeight: Dp) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .wrapContentSize()
        ) {
            avatarBitmap?.let {
                val imageBitmap = it.asImageBitmap()
                Image(bitmap = imageBitmap, contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(65.dp)
                        .offset(x = (-75).dp, y = 385.dp)
                )
                Spacer(modifier = Modifier.width(25.dp))
            }

            user?.let {
                Text(text = it.username, color = (Color(0xFFEAEFC4)),
                    fontWeight = FontWeight.Bold, fontSize = 30.sp,
                    modifier = Modifier.offset(x= (-80).dp, y = 405.dp)
                        .wrapContentSize())
            }
        }

        if (isLoading) {
            Text(text = "Cargando datos del usuario...", color = Color.Black,
                modifier = Modifier
                    .offset(y = 390.dp)
                    .wrapContentSize())
        } else if (user != null) {

            Text(text = "Horas de juego: ${user.hoursInApp}", color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .offset(x= (95).dp, y = 430.dp)
                    .wrapContentSize())
            Text(text = "Total partidas: ${user.gamesPlayed}", color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x= (-105).dp, y = 412.dp))
            Text(text = "Victorias: ${user.victories}", color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .offset(x= (-122).dp, y = 460.dp)
                    .wrapContentSize())
        } else {
            Text(text = "No se encontraron datos del usuario.", color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .offset(y = 390.dp)
                    .wrapContentSize())
        }
    }
}


/**
 * This function displays the results wallpaper.
 *
 * @param resultsViewModel The ViewModel that this function will use to handle results tasks.
 */
@Composable
fun ResultsWallpaper(resultsViewModel: ResultsViewModel) {

    val user by resultsViewModel.user.collectAsState()

    SideEffect {
        Log.d("ResultsScreen", "ResultsScreen se ha recomponido. Datos del usuario: $user")
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.wallpaper3),
            contentDescription = "Results wallpaper",
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
                contentDescription = "a Wood backbround below the Results board",
                modifier = Modifier
                    .size(450.dp)
                    .height(450.dp)
                    .offset(y = 50.dp, x = 50.dp)
                    .border(1.dp, Color(0xFFEAEFC4), RectangleShape),
                contentScale = ContentScale.Crop
            )

            ResultsImage(maxWidth = 390.dp, maxHeight = 650.dp)
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

