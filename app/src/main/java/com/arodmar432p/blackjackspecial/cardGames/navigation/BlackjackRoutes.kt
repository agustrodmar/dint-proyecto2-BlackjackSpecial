package com.arodmar432p.blackjackspecial.cardGames.navigation

/**
 * A sealed class representing the different routes in the Blackjack game.
 *
 * @property route The string representation of the route.
 */
sealed class BlackjackRoutes(val route: String) {

    /**
     * Represents the main menu screen route.
     */
    object MainMenuScreen : BlackjackRoutes("MainMenuScreen")

    /**
     * Represents the Blackjack game screen route.
     */
    object BlackjackScreen : BlackjackRoutes("BlackjackScreen")

    /**
     * Represents the Blackjack dealer screen route.
     */
    object BlackjackDealerScreen : BlackjackRoutes("BlackjackDealerScreen")

    /**
     * Represents the results screen route.
     */
    object ResultsScreen : BlackjackRoutes("ResultsScreen")

    /*
    /**
     * Represents the Highest Card Game Screen
     */
    object HighestCardScreen : BlackjackRoutes("HighestCardScreen")

     */

    /**
     * Represents the Authentication Screen
     */
    object AuthScreen : BlackjackRoutes("AuthScreen")

    /**
     * Represents the Ranking Screen
     */
    object RankingScreen : BlackjackRoutes("RankingScreen")

}