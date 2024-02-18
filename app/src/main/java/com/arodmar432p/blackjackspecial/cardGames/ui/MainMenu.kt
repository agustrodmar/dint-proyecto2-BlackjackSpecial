package com.arodmar432p.blackjackspecial.cardGames.ui

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.BlackjackRoutes


/**
 * A composable function to display the main menu of the Blackjack game.
 *
 * @param navController The NavController for navigation.
 * @param gameViewModel The ViewModel for the game.
 */
@Composable
fun MainMenu(navController: NavController, gameViewModel: BlackjackGameViewModel) {
    // Remember a mutable state for the open dialog
    val openDialog = remember { mutableStateOf(false) }
    // Get the background image
    val background = painterResource(id = R.drawable.wallpaper1)
    // Get the current context
    val context = LocalContext.current

    // I Get the drawables for the game modes
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // One player game image
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Contra la mesa", color = Color.White)
                Image(
                    painter = onePlayerDrawable,
                    contentDescription = "One Player Game",
                    modifier = Modifier
                        .sizeIn(minWidth = 200.dp, minHeight = 50.dp)
                        .clickable { navController.navigate(BlackjackRoutes.BlackjackDealerScreen.route) }
                )
            }

            // Two players game image
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Dos jugadores", color = Color.White)
                Image(
                    painter = twoPlayersDrawable,
                    contentDescription = "Two Players Game",
                    modifier = Modifier
                        .sizeIn(minWidth = 200.dp, minHeight = 50.dp)
                        .clickable { navController.navigate(BlackjackRoutes.BlackjackScreen.route) }
                )
            }
        }
    }

    // Music toggle button
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        IconButton(onClick = { gameViewModel.toggleMusic(context) }) {
            Icon(painterResource(R.drawable.icmusicnote), contentDescription = "Toggle Music")
        }
    }
    Row(modifier = Modifier.fillMaxSize()) {
        ColumnaMainMenu(navController, AuthViewModel(), gameViewModel = gameViewModel)
    }
}

@Composable
fun ColumnaMainMenu(navController: NavController, authViewModel: AuthViewModel, gameViewModel: BlackjackGameViewModel) {
    val columna: Painter = painterResource(id = R.drawable.columna)
    // Get the current context
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fraction = 0.15f)
            .background(Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray)))
    ) {
        Image(
            painter = columna,
            contentDescription = "Columna de madera",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column (modifier = Modifier
            .align(Alignment.Center)) {

            Button(
                onClick = { navController.navigate(BlackjackRoutes.ResultsScreen.route) },
                shape = RectangleShape, // Esquinas completamente cuadradas
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF231513)),
                border = BorderStroke(3.dp, Color(0xFFEAEFC4)),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .sizeIn(minWidth = 150.dp, minHeight = 50.dp)
            ) {
                Text("Resultados", color = Color.White)
            }

            Button(
                onClick = {
                    authViewModel.signOut()
                    navController.navigate(BlackjackRoutes.AuthScreen.route)
                },
                shape = RectangleShape, // Esquinas completamente cuadradas
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
                onClick = { gameViewModel.closeApp()},
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