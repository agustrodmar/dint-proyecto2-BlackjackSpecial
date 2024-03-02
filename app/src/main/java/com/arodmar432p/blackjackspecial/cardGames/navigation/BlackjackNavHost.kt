package com.arodmar432p.blackjackspecial.cardGames.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.AuthScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.AuthViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackDealerScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackDealerViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackGame
import com.arodmar432p.blackjackspecial.cardGames.ui.menu.MainMenu
import com.arodmar432p.blackjackspecial.cardGames.ui.ranking.RankingScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.ranking.RankingViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsScreen
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsViewModel

/**
 * A class that represents the navigation host for the Blackjack application.
 *
 * @property navController The navigation controller for navigating between composables.
 * @property authViewModel The ViewModel for authentication-related operations.
 * @property dealerGameViewModel The ViewModel for the dealer game operations.
 * @property rankingViewModel The ViewModel for ranking-related operations.
 * @property resultsViewModel The ViewModel for results-related operations.
 */
class BlackjackNavHost(
    private val navController: NavHostController,
    private val authViewModel: AuthViewModel,
    private val dealerGameViewModel: BlackjackDealerViewModel,
    private val rankingViewModel: RankingViewModel,
    private val resultsViewModel: ResultsViewModel
) {
    /**
     * Creates the navigation host with all the routes for the application.
     *
     * Requires API level 28 (Android 9.0, Pie) or higher due to certain Compose features.
     */
    @RequiresApi(Build.VERSION_CODES.P)
    @Composable
    fun Create() {
        NavHost(
            navController = navController,
            startDestination = BlackjackRoutes.AuthScreen.route
        ) {
            composable(BlackjackRoutes.MainMenuScreen.route) {
                MainMenu(navController = navController, authViewModel)
            }
            composable(BlackjackRoutes.BlackjackScreen.route) {
                BlackjackGame(blackjackDealerViewModel = dealerGameViewModel)
            }
            composable(BlackjackRoutes.BlackjackDealerScreen.route) {
                BlackjackDealerScreen(blackjackDealerViewModel = dealerGameViewModel)
            }
            composable(BlackjackRoutes.ResultsScreen.route) {
                Log.d("BlackjackNavHost", "ResultsViewModel instance: $resultsViewModel")
                ResultsScreen(resultsViewModel)
            }
            composable(BlackjackRoutes.AuthScreen.route) {
                AuthScreen(viewModel = authViewModel, navController)
            }
            composable(BlackjackRoutes.RankingScreen.route) {
                RankingScreen(rankingViewModel)
            }
        }
    }
}