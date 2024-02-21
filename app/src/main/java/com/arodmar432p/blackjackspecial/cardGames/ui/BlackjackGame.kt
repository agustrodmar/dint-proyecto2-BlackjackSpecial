package com.arodmar432p.blackjackspecial.cardGames.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.Card

/**
 * A composable function to display the Blackjack dealer screen.
 *
 * @param blackjackDealerViewModel The ViewModel for the dealer.
 */
@Composable
fun BlackjackGame(blackjackDealerViewModel: BlackjackDealerViewModel) {
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
            painter = painterResource(id = R.drawable.wallpapercpu),
            contentDescription = "The wallpaper for the CPU game",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = painterResource(id = R.drawable.texturawallpaper),
            contentDescription = " Textura de tapete",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.20f),
            contentScale = ContentScale.Crop
        )

        // Display the game screen or the start screen
        if (gameInProgress || isGameOver) {
            GameScreen2(blackjackDealerViewModel, playerPoints, playerHand, dealerHand)
        } else {
            StartScreen2(blackjackDealerViewModel)
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
fun StartScreen2(blackjackDealerViewModel: BlackjackDealerViewModel) {

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
fun GameScreen2(
    blackjackDealerViewModel: BlackjackDealerViewModel,
    playerPoints: Int,
    playerHand: List<Card>,
    dealerHand: List<Card>
) {
    // Get the game over state from the ViewModel
    val isGameOver by blackjackDealerViewModel.isGameOver.observeAsState(false)
    val screenSize = LocalConfiguration.current.screenWidthDp.dp
    val density = LocalDensity.current.density

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LowerPanel2(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }

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
            horizontalArrangement = Arrangement.spacedBy((20).dp)
        ) {
            dealerHand.forEachIndexed { index, card ->
                val cardResource = if (index != 0 && !isGameOver) R.drawable.bocabajodealer else card.idDrawable

                Box(
                    modifier = Modifier
                        .size(190.dp, 250.dp)
                ) {
                    if (cardResource == R.drawable.bocabajodealer) {
                        Image(
                            painter = painterResource(id = cardResource),
                            contentDescription = "Dealer Card when is not tapped",
                            modifier = Modifier
                                .scale(.7f)
                                .offset(y = 4.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = cardResource!!),
                            contentDescription = "Dealer Card",
                            modifier = Modifier
                                .offset(x = 2.5.dp, y = (-20).dp)
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.paneltapete),
                        contentDescription = "El panel del tapete para colocar las cartas. ",
                        modifier = Modifier
                            .size(210.dp)
                    )
                }
            }
        }
        Text(text = "Player Points: $playerPoints", color = Color.White)

        Row(
            horizontalArrangement = Arrangement.spacedBy((20).dp)
        ) {
            playerHand.forEachIndexed { index, card ->
                Box(
                    modifier = Modifier
                        .size(190.dp, 250.dp)
                        .offset(y = 250.dp)
                ) {
                    Image(
                        painter = painterResource(id = card.idDrawable!!),
                        contentDescription = "Card ${card.rank} of ${card.suit}",
                        modifier = Modifier
                            .offset(x = 2.5.dp, y = (-25).dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.paneltapete),
                        contentDescription = "El panel del tapete para colocar las cartas. ",
                        modifier = Modifier
                            .size(210.dp)
                    )
                }
            }
        }

        // Display the hit and stand buttons
        Row {
            Button(
                onClick = { blackjackDealerViewModel.playerTurn() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF231513)),
                border = BorderStroke(4.dp, Color(0xFFEAEFC4)),
                modifier = Modifier
                    .height(50.dp)
                    .offset(x = (-30).dp, y = 200.dp)
            ) {
                Text("Dame carta", color = Color.White)

                Icon(
                    painter = painterResource(id = R.drawable.hitme),
                    contentDescription = "Pass Icon",
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically)
                        .offset(10.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { blackjackDealerViewModel.stand() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF231513)),
                border = BorderStroke(4.dp, Color(0xFFEAEFC4)),
                modifier = Modifier
                    .height(50.dp)
                    .offset(x = 30.dp, y = 200.dp)
            ) {
                Text("Plantarse", color = Color.White)

                Icon(
                    painter = painterResource(id = R.drawable.pass),
                    contentDescription = "Pass Icon",
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically)
                        .offset(10.dp)
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logotapete),
                contentDescription = "Logo Tapete",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-350).dp)
                    .size((330.dp * density).coerceAtMost(screenSize / 2))
            )

            Image(
                painter = painterResource(id = R.drawable.barajafondo),
                contentDescription = "Wallpaper decoration",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size((220.dp * density).coerceAtMost(screenSize / 4))
                    .offset(x = 25.dp, y = (-520).dp * density)
            )

            Image(
                painter = painterResource(id = R.drawable.jokerfondo),
                contentDescription = "The Joker logo",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxHeight()
                    .width((350.dp * density).coerceAtMost(screenSize / 2))
                    .scale(1.65f)
                    .offset(y = (-40).dp * density)
            )
        }
    }
}

/**
 * The composable that represents the lower panel in the game
 */
@Composable
fun LowerPanel2(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = painterResource(id = R.drawable.panelinferior),
            contentDescription = "Lower Panel",
            modifier = Modifier.fillMaxSize()
        )
    }
}