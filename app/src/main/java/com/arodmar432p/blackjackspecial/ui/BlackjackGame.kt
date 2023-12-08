package com.arodmar432p.blackjackspecial.ui


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
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
import com.arodmar432p.blackjackspecial.data.Player

@Composable
fun BlackjackScreen(gameViewModel: BlackjackGameViewModel) {
    val backgroundImage = painterResource(id = R.drawable.tapete)
    val players by gameViewModel.players.observeAsState(initial = emptyList())
    val winner by gameViewModel.winner.observeAsState()
    val gameInProgress by gameViewModel.gameInProgress.observeAsState(initial = false)
    val currentTurn by gameViewModel.currentTurn.observeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column {
            Text(text = "Blackjack Game", color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))

            // Muestra el turno actual
            currentTurn?.let {
                Text(text = "Turno: ${it.name}", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (gameInProgress) {
                players.forEachIndexed { index, player ->
                    currentTurn?.let { PlayerCard(player, gameViewModel, index, currentTurn = it) }
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
                    Text(text = "Start Game", color = Color.Black)
                }
            }
        }
    }
}


@Composable
fun PlayerCard(player: Player, gameViewModel: BlackjackGameViewModel, index: Int, currentTurn: Player) {

    val winner by gameViewModel.winner.observeAsState()

    if (winner != null) {
        AlertDialog(
            onDismissRequest = { gameViewModel.closeDialog() },
            title = { Text(text = "Game Over") },
            text = { Text(text = "Winner is: ${winner!!.name}") },
            confirmButton = {
                Button(onClick = { gameViewModel.closeDialog() }) {
                    Text("Aceptar")
                }
            }
        )
      // Con este condicional manejo que sea empate.
    } else if (gameViewModel.showDialog.value == true) {
        AlertDialog(
            onDismissRequest = { gameViewModel.closeDialog() },
            title = { Text(text = "Game Over") },
            text = { Text(text = "El resultado es de empate") },
            confirmButton = {
                Button(onClick = { gameViewModel.closeDialog() }) {
                    Text("Aceptar")
                }
            }
        )
    }

    Column {
        Text(text = "${player.name}", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        // Debe mostrar los puntos solo cuando el jugador tenga el turno
        if (gameViewModel.currentTurn.value == player) {
            Text(text = "Points: ${gameViewModel.calculatePoints(player.hand)}", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Muestra las cartas del jugador
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            player.hand.forEachIndexed { cardIndex, card ->
                val isGameOver = gameViewModel.winner.value != null || gameViewModel.showDialog.value == true
                val shouldHideCard = gameViewModel.currentTurn.value != player && cardIndex != 0 && !isGameOver
                val cardResource = if (shouldHideCard) {
                    getCardResource("bocabajo")
                } else {
                    getCardResource(card.idDrawable)
                }
                Image(
                    painterResource(id = cardResource),
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .width(75.dp)
                        .offset(x = (cardIndex * 50).dp)
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(
                onClick = { gameViewModel.hitMe(player) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text(text = "Hit Me", color = Color.White)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { gameViewModel.pass(player) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text(text = "Pass", color = Color.White)
            }
        }
    }
}
fun getCardResource(cardName: String): Int {
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
/*
@Preview(showBackground = true)
@Composable
fun BlackjackScreenPreview() {
    // Provide a mock ViewModel for the preview
    val mockViewModel = BlackjackGameViewModel(mockResources, mockResourcesFaceDown)
    BlackjackScreen(mockViewModel)
}
*/