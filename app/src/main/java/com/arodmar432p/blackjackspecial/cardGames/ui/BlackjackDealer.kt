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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Card


@Composable
fun BlackjackDealerScreen(blackjackDealerViewModel: BlackjackDealerViewModel) {
    val playerPoints by blackjackDealerViewModel.playerPoints.observeAsState(0)
    val dealerPoints by blackjackDealerViewModel.dealerPoints.observeAsState(0)
    val winner by blackjackDealerViewModel.winner.observeAsState("")
    val playerHand by blackjackDealerViewModel.playerHand.observeAsState(listOf())
    val dealerHand by blackjackDealerViewModel.dealerHand.observeAsState(listOf())
    val gameInProgress by blackjackDealerViewModel.gameInProgress.observeAsState(false)
    val isGameOver by blackjackDealerViewModel.isGameOver.observeAsState(false)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.tapete),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        if (gameInProgress || isGameOver) {
            GameScreen(blackjackDealerViewModel, playerPoints, dealerPoints, playerHand, dealerHand)
        } else {
            StartScreen(blackjackDealerViewModel, winner)
        }

        if (isGameOver) {
            AlertDialog(
                onDismissRequest = { blackjackDealerViewModel.closeDialog() },
                title = { Text(text = "Game Over") },
                text = { Text(text = "Winner is: $winner") },
                confirmButton = {
                    Button(onClick = {
                        blackjackDealerViewModel.closeDialog()
                        blackjackDealerViewModel.startGame()  // Añade esta línea
                    }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

@Composable
fun StartScreen(blackjackDealerViewModel: BlackjackDealerViewModel, winner: String?) {
    val gameReset by blackjackDealerViewModel.gameReset.observeAsState(false)

    if (gameReset) {
        blackjackDealerViewModel.startGame()
        blackjackDealerViewModel.gameReset.value = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        winner?.let {
            Text(text = "Winner: $winner", color = Color.White)
        }
        Button(
            onClick = { blackjackDealerViewModel.startGame() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Text("Start Game", color = Color.Black)
        }
    }
}

@Composable
fun GameScreen(blackjackDealerViewModel: BlackjackDealerViewModel, playerPoints: Int, dealerPoints: Int, playerHand: List<Card>, dealerHand: List<Card>) {
    val isGameOver by blackjackDealerViewModel.isGameOver.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // tamaño de las cartas de la mesa
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