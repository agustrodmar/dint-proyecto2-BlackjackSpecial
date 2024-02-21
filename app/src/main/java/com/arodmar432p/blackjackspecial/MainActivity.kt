package com.arodmar432p.blackjackspecial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arodmar432p.blackjackspecial.cardGames.ui.BlackjackGameViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.MainMenu
import com.arodmar432p.blackjackspecial.cardGames.data.BlackjackRoutes
import com.arodmar432p.blackjackspecial.cardGames.repository.UserRepository
import com.arodmar432p.blackjackspecial.cardGames.ui.AuthViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.BlackjackDealerScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.BlackjackDealerViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.BlackjackGame
import com.arodmar432p.blackjackspecial.cardGames.ui.HighestCardScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.HighestCardViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.RegisterScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsViewModel
import com.arodmar432p.blackjackspecial.ui.theme.BlackjackSpecialTheme


/**
 * The main activity of the Blackjack game.
 */
class MainActivity : ComponentActivity() {
    // ViewModel for the vs game
    private val vsGameViewModel: BlackjackGameViewModel by viewModels()

    // ViewModel for the dealer game
    private val dealerGameViewModel: BlackjackDealerViewModel by viewModels()

    // ViewModel for the Highest Card game
    private val highestCardViewModel : HighestCardViewModel by viewModels()

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackjackSpecialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = BlackjackRoutes.AuthScreen.route
                    ) {
                        composable(BlackjackRoutes.MainMenuScreen.route) {
                            MainMenu(navController = navController, gameViewModel = vsGameViewModel)
                        }
                        composable(BlackjackRoutes.BlackjackScreen.route) {
                            BlackjackGame(blackjackDealerViewModel = dealerGameViewModel)
                        }
                        composable(BlackjackRoutes.BlackjackDealerScreen.route) {
                            BlackjackDealerScreen(blackjackDealerViewModel = dealerGameViewModel)
                        }
                        composable(BlackjackRoutes.ResultsScreen.route) {
                            ResultsScreen(blackjackDealerViewModel = BlackjackDealerViewModel(), authViewModel, resultsViewModel = ResultsViewModel(
                                UserRepository()
                            ))
                        }

                        composable(BlackjackRoutes.AuthScreen.route) {
                            RegisterScreen(viewModel = authViewModel, navController)
                        }

                        composable(BlackjackRoutes.HighestCardScreen.route) {
                            HighestCardScreen(highestCardViewModel = highestCardViewModel)
                        }
                    }
                }
            }
        }

        // Observe the event to close the app
        vsGameViewModel.eventCloseApp.observe(this) { event ->
            if (event) {
                finish()
                vsGameViewModel.onAppClosed()
            }
        }
    }
}
