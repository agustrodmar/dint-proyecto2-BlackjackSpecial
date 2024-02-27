package com.arodmar432p.blackjackspecial.cardGames.ui.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.BlackjackRoutes
import com.arodmar432p.blackjackspecial.cardGames.repository.UserRepository
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.AuthViewModel



/**
 * A composable function to display the main menu of the Blackjack game.
 *
 * @param navController The NavController for navigation.
 * @param gameViewModel The ViewModel for the game.
 */
@Composable
fun MainMenu(navController: NavController) {
    // Get the background image
    val background = painterResource(id = R.drawable.wallpaper3)

    // Get the drawables for the game modes
    val onePlayerDrawable = painterResource(id = R.drawable.unjugador)
    val twoPlayersDrawable = painterResource(id = R.drawable.dosjugadores)


    // Display the main menu
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = background,
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Image(
            painter = painterResource(id = R.drawable.texturawallpaper),
            contentDescription = "Texture for the background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.12f)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(275.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // One player game image
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Contra la mesa", color = Color.White, fontSize = 30.sp)
                Image(
                    painter = onePlayerDrawable,
                    contentDescription = "One Player Game",
                    modifier = Modifier
                        .size(260.dp)
                        .clickable { navController.navigate(BlackjackRoutes.BlackjackDealerScreen.route) }
                )
            }

            // Two players game image
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Dos jugadores", color = Color.White, fontSize = 30.sp)
                Image(
                    painter = twoPlayersDrawable,
                    contentDescription = "Two Players Game",
                    modifier = Modifier
                        .size(250.dp)
                        .clickable { navController.navigate(BlackjackRoutes.BlackjackScreen.route) }
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App logo",
            modifier = Modifier
                .offset(y = (-340).dp)
                .size(350.dp)
        )
    }

    Row(modifier = Modifier.fillMaxSize()) {
        ColumnMainMenu(navController, AuthViewModel(UserRepository()))
    }
}

@Composable
fun ColumnMainMenu(navController: NavController, authViewModel: AuthViewModel) {
    val columna: Painter = painterResource(id = R.drawable.columna)
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fraction = 0.15f)
            .background(Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray)))
    ) {
        Image(
            painter = columna,
            contentDescription = "Wood column",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column (modifier = Modifier
            .align(Alignment.Center)) {

            Button(
                onClick = { navController.navigate(BlackjackRoutes.ResultsScreen.route) },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF231513)),
                border = BorderStroke(3.dp, Color(0xFFEAEFC4)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .sizeIn(minWidth = 150.dp, minHeight = 50.dp)
                    .offset(y = (-105).dp)

            ) {
                Text("Resultados", color = Color.White)
            }

            Button(
                onClick = { navController.navigate(BlackjackRoutes.RankingScreen.route) },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF231513)),
                border = BorderStroke(3.dp, Color(0xFFEAEFC4)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .sizeIn(minWidth = 150.dp, minHeight = 50.dp)
            ) {
                Text("Ranking", color = Color.White)
            }

            Button(
                onClick = {
                    authViewModel.signOut()
                    navController.navigate(BlackjackRoutes.AuthScreen.route)
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF231513)),
                border = BorderStroke(3.dp, Color(0xFFEAEFC4)),
                modifier = Modifier
                    .fillMaxWidth((0.60f))
                    .padding(top = 125.dp)
                    .sizeIn(minWidth = 150.dp, minHeight = 50.dp)
            ) {
                Text("Cerrar sesión", color = Color.White)
            }

            Button(
                onClick = { authViewModel.closeApp()},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF231513)),
                border = BorderStroke(3.dp, Color(0xFFEAEFC4)),
                modifier = Modifier
                    .fillMaxWidth(0.60f)
                    .padding(top = 110.dp)
                    .sizeIn(minWidth = 150.dp, minHeight = 50.dp)
            ) {
                Text("Salir de la App", color = Color.White)
            }
        }
    }
}