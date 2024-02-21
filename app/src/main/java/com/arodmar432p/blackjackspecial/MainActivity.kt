package com.arodmar432p.blackjackspecial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackvs2.BlackjackGameViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.menu.MainMenu
import com.arodmar432p.blackjackspecial.cardGames.data.BlackjackRoutes
import com.arodmar432p.blackjackspecial.cardGames.repository.UserRepository
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.AuthViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackDealerScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackDealerViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackGame
import com.arodmar432p.blackjackspecial.cardGames.ui.highestcard.HighestCardScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.highestcard.HighestCardViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.RegisterScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsViewModel
import com.arodmar432p.blackjackspecial.cardGames.util.MyViewModelFactory
import com.arodmar432p.blackjackspecial.ui.theme.BlackjackSpecialTheme


/**
 * The main activity of the Blackjack game.
 */
class MainActivity : ComponentActivity() {
    private lateinit var vsGameViewModel: BlackjackGameViewModel
    private lateinit var dealerGameViewModel: BlackjackDealerViewModel
    private lateinit var highestCardViewModel: HighestCardViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var resultsViewModel: ResultsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create UserRepository and MyViewModelFactory
        val userRepository = UserRepository()
        val factory = MyViewModelFactory(userRepository)

        // Crear ViewModel con la fábrica
        vsGameViewModel = ViewModelProvider(this, factory).get(BlackjackGameViewModel::class.java)
        dealerGameViewModel = ViewModelProvider(this, factory).get(BlackjackDealerViewModel::class.java)
        highestCardViewModel = ViewModelProvider(this, factory).get(HighestCardViewModel::class.java)
        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        resultsViewModel = ViewModelProvider(this, factory).get(ResultsViewModel::class.java)


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
                            ResultsScreen(blackjackDealerViewModel = dealerGameViewModel, authViewModel, resultsViewModel = ResultsViewModel(userRepository))
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
