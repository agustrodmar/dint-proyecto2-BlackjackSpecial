package com.arodmar432p.blackjackspecial

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.arodmar432p.blackjackspecial.cardGames.navigation.BlackjackNavHost
import com.arodmar432p.blackjackspecial.cardGames.repository.AvatarRepository
import com.arodmar432p.blackjackspecial.cardGames.repository.RankingRepository
import com.arodmar432p.blackjackspecial.cardGames.repository.UserRepository
import com.arodmar432p.blackjackspecial.cardGames.service.AvatarApi
import com.arodmar432p.blackjackspecial.cardGames.ui.authentication.AuthViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.blackjackdealer.BlackjackDealerViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.ranking.RankingViewModel
import com.arodmar432p.blackjackspecial.cardGames.ui.results.ResultsViewModel
import com.arodmar432p.blackjackspecial.cardGames.util.BlackjackViewModelFactory
import com.arodmar432p.blackjackspecial.ui.theme.BlackjackSpecialTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * The main activity of the Blackjack game.
 */
class MainActivity : ComponentActivity() {
    // private lateinit var vsGameViewModel: BlackjackGameViewModel
    private lateinit var dealerGameViewModel: BlackjackDealerViewModel
   //  private lateinit var highestCardViewModel: HighestCardViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var resultsViewModel: ResultsViewModel
    private lateinit var rankingViewModel: RankingViewModel

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.multiavatar.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val avatarApi = retrofit.create(AvatarApi::class.java)
        val avatarRepository = AvatarRepository(avatarApi)
        val userRepository = UserRepository()

        val factory = BlackjackViewModelFactory(userRepository, RankingRepository(), avatarRepository)

        // Creating Factory ViewModel.
        try {
            dealerGameViewModel = ViewModelProvider(this, factory)[BlackjackDealerViewModel::class.java]
            authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
            resultsViewModel = ViewModelProvider(this, factory)[ResultsViewModel::class.java]
            Log.d("MainActivity", "ResultsViewModel instance: $resultsViewModel")
            rankingViewModel = ViewModelProvider(this, factory)[RankingViewModel::class.java]
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al crear ViewModel", e)
        }

        setContent {
            BlackjackSpecialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    BlackjackNavHost(
                        navController = navController,
                        authViewModel = authViewModel,
                        dealerGameViewModel = dealerGameViewModel,
                        rankingViewModel = rankingViewModel,
                        resultsViewModel = resultsViewModel
                    ).Create()
                }
            }
        }

        // Observe the event to close the app
        authViewModel.eventCloseApp.observe(this) { event ->
            Log.d("MainActivity", "eventCloseApp observed, value: $event")
            if (event) {
                finish()
                authViewModel.onAppClosed()
                Log.d("MainActivity", "App finished and onAppClosed() called")
            }
        }
    }
}
