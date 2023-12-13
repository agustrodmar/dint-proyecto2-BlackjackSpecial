package com.arodmar432p.blackjackspecial.cardGames.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.BlackjackRoutes

@Composable
fun BetScreen(blackjackDealerViewModel: BlackjackDealerViewModel, navController: NavController) {
    val playerChips by blackjackDealerViewModel.playerChips.observeAsState(0)
    val currentBet by blackjackDealerViewModel.currentBet.observeAsState(0)
    val context = LocalContext.current
    val isStartGameButtonEnabled by blackjackDealerViewModel.isStartGameButtonEnabled.observeAsState(false)
    val sliderValue by blackjackDealerViewModel.sliderValue.observeAsState(0)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.tapete),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { blackjackDealerViewModel.decreaseBet() },
                    enabled = currentBet > 0
                ) {
                    Text("-")
                }

                Text("Apuesta actual: $currentBet", color = Color.Black)

                Button(
                    onClick = { blackjackDealerViewModel.increaseBet() },
                    enabled = playerChips > 0
                ) {
                    Text("+")
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Fichas: $playerChips", color = Color.White)
            Text("Apuesta actual: $currentBet", color = Color.White)

            Button(
                onClick = {
                    blackjackDealerViewModel.sliderValue.value = sliderValue
                    blackjackDealerViewModel.placeBet()
                    blackjackDealerViewModel.startGame()
                    navController.navigate(BlackjackRoutes.BlackjackDealerScreen.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White),
                enabled = isStartGameButtonEnabled
            ) {
                Text("Realizar apuesta", color = Color.Black)
            }
        }
    }
}
