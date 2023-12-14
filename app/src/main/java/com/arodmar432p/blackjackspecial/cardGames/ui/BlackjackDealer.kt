package com.arodmar432p.blackjackspecial.cardGames.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Card


/**
 * A composable function to display the Blackjack dealer screen.
 *
 * @param blackjackDealerViewModel The ViewModel for the dealer.
 */
@Composable
fun BlackjackDealerScreen(blackjackDealerViewModel: BlackjackDealerViewModel) {
    // Get the game state from the ViewModel
    val playerPoints by blackjackDealerViewModel.playerPoints.observeAsState(0)
    val winner by blackjackDealerViewModel.winner.observeAsState("")
    val playerHand by blackjackDealerViewModel.playerHand.observeAsState(listOf())
    val dealerHand by blackjackDealerViewModel.dealerHand.observeAsState(listOf())
    val gameInProgress by blackjackDealerViewModel.gameInProgress.observeAsState(false)
    val isGameOver by blackjackDealerViewModel.isGameOver.observeAsState(false)

    // Display the dealer screen
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.tapete),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Display the game screen or the start screen
        if (gameInProgress || isGameOver) {
            GameScreen(blackjackDealerViewModel, playerPoints, playerHand, dealerHand)
        } else {
            StartScreen(blackjackDealerViewModel)
        }

        // Display a dialog when the game is over
        if (isGameOver) {
            AlertDialog(
                onDismissRequest = { blackjackDealerViewModel.closeDialog() },
                title = { Text(text = "Fin de la ronda") },
                text = {
                    if (winner == "Draw") {
                        Text(text = "Empate")
                    } else {
                        Text(text = "El ganador es: $winner")
                    }
                },
                confirmButton = {
                    val context = LocalContext.current
                    Button(onClick = {
                        blackjackDealerViewModel.closeDialog()
                        blackjackDealerViewModel.startGame()
                        blackjackDealerViewModel.playDealSound(context)
                    },  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                        border = BorderStroke(2.dp, Color.White)
                        ) {
                        Text("Aceptar")

                    }
                }
            )
        }
    }
}

/**
 * A composable function to display the start screen of the Blackjack game.
 *
 * @param blackjackDealerViewModel The ViewModel for the dealer.
 * @param winner The winner of the game.
 */
@Composable
fun StartScreen(blackjackDealerViewModel: BlackjackDealerViewModel) {

    // Get the game reset state from the ViewModel
    val gameReset by blackjackDealerViewModel.gameReset.observeAsState(false)

    // Start a new game when the game is reset
    if (gameReset) {
        blackjackDealerViewModel.startGame()
        blackjackDealerViewModel.gameReset.value = false
    }

    // Display the start screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        // Start game button
        Button(
            onClick = { blackjackDealerViewModel.startGame() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Text("Empezar Partida", color = Color.Black)
        }
    }
}

/**
 * A composable function to display the game screen of the Blackjack game.
 *
 * @param blackjackDealerViewModel The ViewModel for the dealer.
 * @param playerPoints The points of the player.
 * @param playerHand The hand of the player.
 * @param dealerHand The hand of the dealer.
 */
@Composable
fun GameScreen(
    blackjackDealerViewModel: BlackjackDealerViewModel,
    playerPoints: Int,
    playerHand: List<Card>,
    dealerHand: List<Card>
) {
    // Get the game over state from the ViewModel
    val isGameOver by blackjackDealerViewModel.isGameOver.observeAsState(false)

    // Display the game screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        // Display the dealer's cards
        Row(
            horizontalArrangement = Arrangement.spacedBy((-80).dp)
        ) {
            dealerHand.forEachIndexed { index, card ->
                val cardResource = if (index != 0 && !isGameOver) R.drawable.bocabajo else card.idDrawable
                Box(
                    modifier = Modifier
                        .size(90.dp, 150.dp)
                        .offset { IntOffset((index * 50).dp.roundToPx(), (index * 20).dp.roundToPx()) }
                ) {
                    Image(
                        painter = painterResource(id = cardResource!!),
                        contentDescription = "Dealer Card"
                    )
                }
            }
        }
        Text(text = "Player Points: $playerPoints", color = Color.White)

        Row(
            horizontalArrangement = Arrangement.spacedBy((-80).dp)
        ) {
            playerHand.forEachIndexed { index, card ->
                Box(
                    modifier = Modifier
                        .size(90.dp, 150.dp)
                        .offset { IntOffset((index * 50).dp.roundToPx(), (index * 20).dp.roundToPx()) }
                ) {
                    Image(
                        painter = painterResource(id = card.idDrawable!!),
                        contentDescription = "Card ${card.rank} of ${card.suit}"
                    )
                }
            }
        }

        // Display the hit and stand buttons
        Row {
            Button(
                onClick = { blackjackDealerViewModel.playerTurn() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text("Dame carta", color = Color.Black)
            }
            Button(
                onClick = { blackjackDealerViewModel.stand() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text("Plantarse", color = Color.Black)
            }
        }
    }
}
