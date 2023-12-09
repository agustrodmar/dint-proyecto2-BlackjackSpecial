package com.arodmar432p.blackjackspecial.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.data.Player


@Composable
fun BlackjackDealerScreen(viewModel: BlackjackDealerViewModel) {
    val players by viewModel.players.observeAsState()
    val winner by viewModel.winner.observeAsState()
    val showDialog by viewModel.showDialog.observeAsState()
    val gameInProgress by viewModel.gameInProgress.observeAsState()

    Column {
        Button(onClick = { viewModel.startGame() }) {
            Text("Start Game")
        }

        Spacer(modifier = Modifier.height(16.dp))

        players?.forEach { player ->
            PlayerCardDealer(player = player, onHitMeClick = { viewModel.hitMe() }, onPassClick = { viewModel.stand() })
        }

        if (showDialog == true) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("Game Over") },
                text = { Text("The winner is ${winner?.name}") },
                confirmButton = {
                    Button(onClick = { viewModel.startGame() }) {
                        Text("Play Again")
                    }
                }
            )
        }
    }
}

@Composable
fun PlayerCardDealer(player: Player, onHitMeClick: () -> Unit, onPassClick: () -> Unit) {
    Column {
        Text(text = player.name)
        player.hand.forEach { card ->
            Text(text = "${card.rank} of ${card.suit}")
        }
        Button(onClick = onHitMeClick) {
            Text("Hit Me")
        }
        Button(onClick = onPassClick) {
            Text("Pass")
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

