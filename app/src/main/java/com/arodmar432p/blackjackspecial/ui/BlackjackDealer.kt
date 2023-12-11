package com.arodmar432p.blackjackspecial.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.arodmar432p.blackjackspecial.data.Player


@Composable
fun BlackjackDealerScreen(blackjackDealerViewModel: BlackjackDealerViewModel) {
    val playerPoints by blackjackDealerViewModel.playerPoints.observeAsState(0)
    val dealerPoints by blackjackDealerViewModel.dealerPoints.observeAsState(0)
    val winner by blackjackDealerViewModel.winner.observeAsState("")
    val playerHand by blackjackDealerViewModel.playerHand.observeAsState(listOf())
    val dealerHand by blackjackDealerViewModel.dealerHand.observeAsState(listOf())
    val gameInProgress by blackjackDealerViewModel.gameInProgress.observeAsState(false)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.tapete),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        if (gameInProgress) {
            GameScreen(blackjackDealerViewModel, playerPoints, dealerPoints, playerHand, dealerHand)
        } else {
            StartScreen(blackjackDealerViewModel, winner)
        }
    }
}

@Composable
fun StartScreen(blackjackDealerViewModel: BlackjackDealerViewModel, winner: String?) {
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Player Points: $playerPoints")
        Row(
            modifier = Modifier.offset { IntOffset((playerHand.size * 10).dp.roundToPx(), 0) }
        ) {
            playerHand.forEach { card ->
                Image(painter = painterResource(id = getCardResourceDealer(card.idDrawable)), contentDescription = "Player Card", modifier = Modifier.size(50.dp))
            }
        }
        Text(text = "Dealer Points: $dealerPoints")
        Row(
            modifier = Modifier.offset { IntOffset((dealerHand.size * 10).dp.roundToPx(), 0) }
        ) {
            dealerHand.forEach { card ->
                Image(painter = painterResource(id = getCardResourceDealer(card.idDrawable)), contentDescription = "Dealer Card", modifier = Modifier.size(50.dp))
            }
        }

        Row {
            Button(
                onClick = { blackjackDealerViewModel.playerTurn() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text("Hit", color = Color.Black)
            }
            Button(
                onClick = { blackjackDealerViewModel.stand() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text("Stand", color = Color.Black)
            }
        }
    }
}
fun getCardResourceDealer(cardName: String): Int {
    return when (cardName) {
        "corazonesa" -> R.drawable.corazonesa
        "corazones2" -> R.drawable.corazones2
        "corazones3" -> R.drawable.corazones3
        "corazones4" -> R.drawable.corazones4
        "corazones5" -> R.drawable.corazones5
        "corazones6" -> R.drawable.corazones6
        "corazones7" -> R.drawable.corazones7
        "corazones8" -> R.drawable.corazones8
        "corazones9" -> R.drawable.corazones9
        "corazones10" -> R.drawable.corazones10
        "corazonesj" -> R.drawable.corazonesj
        "corazonesq" -> R.drawable.corazonesq
        "corazonesk" -> R.drawable.corazonesk
        "diamantesa" -> R.drawable.diamantesa
        "diamantes2" -> R.drawable.diamantes2
        "diamantes3" -> R.drawable.diamantes3
        "diamantes4" -> R.drawable.diamantes4
        "diamantes5" -> R.drawable.diamantes5
        "diamantes6" -> R.drawable.diamantes6
        "diamantes7" -> R.drawable.diamantes7
        "diamantes8" -> R.drawable.diamantes8
        "diamantes9" -> R.drawable.diamantes9
        "diamantes10" -> R.drawable.diamantes10
        "diamantesj" -> R.drawable.diamantesj
        "diamantesq" -> R.drawable.diamantesq
        "diamantesk" -> R.drawable.diamantesk
        "picasa" -> R.drawable.picasa
        "picas2" -> R.drawable.picas2
        "picas3" -> R.drawable.picas3
        "picas4" -> R.drawable.picas4
        "picas5" -> R.drawable.picas5
        "picas6" -> R.drawable.picas6
        "picas7" -> R.drawable.picas7
        "picas8" -> R.drawable.picas8
        "picas9" -> R.drawable.picas9
        "picas10" -> R.drawable.picas10
        "picasj" -> R.drawable.picasj
        "picasq" -> R.drawable.picasq
        "picask" -> R.drawable.picask
        "trebolesa" -> R.drawable.trebolesa
        "treboles2" -> R.drawable.treboles2
        "treboles3" -> R.drawable.treboles3
        "treboles4" -> R.drawable.treboles4
        "treboles5" -> R.drawable.treboles5
        "treboles6" -> R.drawable.treboles6
        "treboles7" -> R.drawable.treboles7
        "treboles8" -> R.drawable.treboles8
        "treboles9" -> R.drawable.treboles9
        "treboles10" -> R.drawable.treboles10
        "trebolesj" -> R.drawable.trebolesj
        "trebolesq" -> R.drawable.trebolesq
        "trebolesk" -> R.drawable.trebolesk
        else -> R.drawable.bocabajo
    }
}

