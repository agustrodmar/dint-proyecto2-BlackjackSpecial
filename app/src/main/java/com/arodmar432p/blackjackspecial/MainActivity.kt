package com.arodmar432p.blackjackspecial

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arodmar432p.blackjackspecial.cardGames.ui.menu.MainMenu
import com.arodmar432p.blackjackspecial.cardGames.data.BlackjackRoutes
import com.arodmar432p.blackjackspecial.cardGames.repository.RankingRepository
import com.arodmar432p.blackjackspecial.cardGames.repository.UserRepository
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.AuthViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackDealerScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackDealerViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackGame
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.AuthScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.ranking.RankingScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.ranking.RankingViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsViewModel
import com.arodmar432p.blackjackspecial.cardGames.util.MyViewModelFactory
import com.arodmar432p.blackjackspecial.ui.theme.BlackjackSpecialTheme


/**
 * The main activity of the Blackjack game.
 */
class MainActivity : ComponentActivity() {
    // private lateinit var vsGameViewModel: BlackjackGameViewModel
    private lateinit var dealerGameViewModel: BlackjackDealerViewModel
   //  private lateinit var highestCardViewModel: HighestCardViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var resultsViewModel: ResultsViewModel

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userRepository = UserRepository()
        val factory = MyViewModelFactory(userRepository, RankingRepository())

        // Creating Factory ViewModel.
        // vsGameViewModel = ViewModelProvider(this, factory)[BlackjackGameViewModel::class.java]
        dealerGameViewModel = ViewModelProvider(this, factory)[BlackjackDealerViewModel::class.java]
        // highestCardViewModel = ViewModelProvider(this, factory)[HighestCardViewModel::class.java]
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        resultsViewModel = ViewModelProvider(this, factory)[ResultsViewModel::class.java]
        val rankingViewModel = ViewModelProvider(this, factory)[RankingViewModel::class.java]


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
                            MainMenu(navController = navController)
                        }
                        composable(BlackjackRoutes.BlackjackScreen.route) {
                            BlackjackGame(blackjackDealerViewModel = dealerGameViewModel)
                        }
                        composable(BlackjackRoutes.BlackjackDealerScreen.route) {
                            BlackjackDealerScreen(blackjackDealerViewModel = dealerGameViewModel)
                        }
                        composable(BlackjackRoutes.ResultsScreen.route) {
                            ResultsScreen(ResultsViewModel())
                        }
                        composable(BlackjackRoutes.AuthScreen.route) {
                            AuthScreen(viewModel = authViewModel, navController)
                        }
                        /*composable(BlackjackRoutes.HighestCardScreen.route) {
                            HighestCardScreen(highestCardViewModel = highestCardViewModel)
                        }*/

                        composable(BlackjackRoutes.RankingScreen.route) {
                            RankingScreen(rankingViewModel)
                        }
                    }
                }
            }
        }

        // Observe the event to close the app
        authViewModel.eventCloseApp.observe(this) { event ->
            if (event) {
                finish()
                authViewModel.onAppClosed()
            }
        }
    }
}
