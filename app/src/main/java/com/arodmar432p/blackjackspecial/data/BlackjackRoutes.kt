package com.arodmar432p.blackjackspecial.data

sealed class BlackjackRoutes(val route: String) {
    object MainMenuScreen : BlackjackRoutes("mainMenuScreen")
    object BlackjackScreen : BlackjackRoutes("BlackjackScreen")
    object BlackjackDealerScreen : BlackjackRoutes("BlackjackDealerScreen")
    object ResultsScreen : BlackjackRoutes("ResultsScreen")
}