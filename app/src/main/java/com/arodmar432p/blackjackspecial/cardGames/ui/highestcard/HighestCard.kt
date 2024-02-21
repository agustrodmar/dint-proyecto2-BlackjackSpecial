package com.arodmar432p.blackjackspecial.cardGames.ui.highestcard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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


/**
 * Composable function for the HighestCard game screen.
 *
 * This function displays the game UI, including the current card, the remaining cards,
 * and the "Hit Me" and "Reset" buttons. It observes the current card and the deck state
 * from the provided ViewModel to update the UI as the game progresses.
 *
 * @param highestCardViewModel The ViewModel for the HighestCard game.
 */
@Composable
fun HighestCardScreen(highestCardViewModel: HighestCardViewModel) {
    // Observe the current card
    val currentCard by highestCardViewModel.currentCard.observeAsState()

    // Load the background image
    val backgroundImage = painterResource(id = R.drawable.tapete)

    // Load the face down card image
    val faceDownImage = painterResource(id = R.drawable.bocabajo3)

    // Create a Box to layer the background image and the game UI
    Box(modifier = Modifier.fillMaxSize()) {
        // Display the background image
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Display the game UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display the current card
            if (currentCard == null) {
                // If there's no current card, show the face down card
                Image(
                    painter = faceDownImage,
                    contentDescription = "Carta bocabajo",
                    modifier = Modifier.size(200.dp)
                )
            } else {
                // If there's a current card, show it
                val cardDrawable = currentCard!!.idDrawable?.let { painterResource(id = it) }
                if (cardDrawable != null) {
                    Image(
                        painter = cardDrawable,
                        contentDescription = "Carta actual: ${currentCard!!.rank} de ${currentCard!!.suit}",
                        modifier = Modifier.size(200.dp)
                    )
                }
            }

            // Display the remaining cards
            Text(text = "Cartas restantes: ${highestCardViewModel.remainingCards.value}",
                color = Color.White)

            // Display the "Hit Me" button
            Button(
                onClick = { highestCardViewModel.hitMe() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text(text = "¡Dame carta!",
                    color = Color.Black)
            }

            // Display the "Reset" button
            Button(
                onClick = { highestCardViewModel.resetGame() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text(text = "   Reiniciar   ",
                    color = Color.Black)
            }
        }
    }
}