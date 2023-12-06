package com.arodmar432p.blackjackspecial.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arodmar432p.blackjackspecial.data.Player

@Composable
fun BlackjackScreen(gameViewModel: BlackjackGameViewModel) {
    val players by gameViewModel.players.observeAsState(initial = emptyList())
    val winner by gameViewModel.winner.observeAsState()
    val gameInProgress by gameViewModel.gameInProgress.observeAsState(initial = false)

    Column {
        Text(text = "Blackjack Game")
        Spacer(modifier = Modifier.height(16.dp))

        if (gameInProgress) {
            players.forEach { player ->
                PlayerCard(player, gameViewModel)
            }
        } else {
            if (winner != null) {
                Text(text = "Winner is: ${winner!!.name}")
            }
            Button(onClick = { gameViewModel.startGame() }) {
                Text(text = "Start Game")
            }
        }
    }
}


@Composable
fun PlayerCard(player: Player, gameViewModel: BlackjackGameViewModel) {
    Card(
        modifier = Modifier.padding(8.dp),
        // elevation = 4.dp
    ) {
        Column {
            Text(text = "Player: ${player.name}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Points: ${gameViewModel.calculatePoints(player.hand)}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { gameViewModel.requestCard(player) }) {
                Text(text = "Request Card")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { gameViewModel.passTurn() }) {
                Text(text = "Pass Turn")
            }
        }
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