package com.arodmar432p.blackjackspecial.cardGames.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Player


/**
 * A composable function to display the Blackjack game screen.
 * @param gameViewModel The ViewModel for the game.
 */

@Composable
fun BlackjackScreen(gameViewModel: BlackjackGameViewModel) {
    // Get the game state from the ViewModel
    val backgroundImage = painterResource(id = R.drawable.tapete)
    val players by gameViewModel.players.observeAsState(initial = emptyList())
    val winner by gameViewModel.winner.observeAsState()
    val gameInProgress by gameViewModel.gameInProgress.observeAsState(initial = false)
    val currentTurn by gameViewModel.currentTurn.observeAsState()

    // Display the game screen
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Display the current turn
            currentTurn?.let {
                Text(text = "Turno: ${it.name}", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Display the players' cards or the winner and start game button
            if (gameInProgress) {
                players.forEachIndexed { _, player ->
                    currentTurn?.let { PlayerCard(player, gameViewModel) }
                }

            } else {
                winner?.let { winner ->
                    Text(text = "Winner is: ${winner.name}", color = Color.White)
                }
                Button(
                    onClick = { gameViewModel.startGame() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                    border = BorderStroke(2.dp, Color.White)
                ) {
                    Text(text = "Empezar ronda", color = Color.Black)
                }
            }
        }
    }
}


/**
 * A composable function to display a player's card.
 * @param player The player.
 * @param gameViewModel The ViewModel for the game.
 */
@Composable
fun PlayerCard(player: Player, gameViewModel: BlackjackGameViewModel) {
    // Get the winner from the ViewModel
    val winner by gameViewModel.winner.observeAsState()

    // Display a dialog if the game is over
    if (winner != null) {
        AlertDialog(
            onDismissRequest = { gameViewModel.closeDialog() },
            title = { Text(text = "Game Over") },
            text = { Text(text = "Winner is: ${winner!!.name}") },
            confirmButton = {
                Button(onClick = { gameViewModel.closeDialog() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                    border = BorderStroke(2.dp, Color.White)) {
                    Text("Aceptar",
                        color = Color.Black)
                }
            }
        )

    } else if (gameViewModel.showDialog.value == true) {
        AlertDialog(
            onDismissRequest = { gameViewModel.closeDialog() },
            title = { Text(text = "Game Over") },
            text = { Text(text = "El resultado es de empate") },
            confirmButton = {
                Button(onClick = { gameViewModel.closeDialog() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                    border = BorderStroke(2.dp, Color.White)
                    ) {
                    Text("Aceptar")
                }
            }
        )
    }

    // Display the player's name, points, and cards
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = player.name, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        // Display the points only when it's the player's turn
        if (gameViewModel.currentTurn.value == player) {
            Text(text = "Puntos: ${gameViewModel.calculatePoints(player.hand)}", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display the player's cards
        Box(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            player.hand.forEachIndexed { cardIndex, card ->
                val isGameOver = gameViewModel.winner.value != null || gameViewModel.showDialog.value == true
                val shouldHideCard = gameViewModel.currentTurn.value != player && cardIndex != 0 &&
                        !isGameOver
                val cardResource = if (shouldHideCard) {
                    R.drawable.bocabajo2
                } else {
                    card.idDrawable
                }
                Image(
                    painterResource(id = cardResource!!),
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .width(75.dp)
                        .offset(x = (cardIndex * 15).dp, y = (cardIndex * 20).dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { gameViewModel.hitMe(player) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text(text = "Dame carta", color = Color.Black)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { gameViewModel.pass(player) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text(text = "Plantarse", color = Color.Black)
            }
        }
    }
}