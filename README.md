# 2dam-u2-pr-ctica-3-ra1-ra2-black-jack-agustrodmar
2dam-u2-pr-ctica-3-ra1-ra2-black-jack-agustrodmar created by GitHub Classroom

# Blackjack Project
This project is an implementation of the card game Blackjack, as part of my Mobile development studies. The game is developed in Kotlin and uses the Jetpack Compose framework for the user interface.

# Project Structure
The project consists of several main parts:

BlackjackDealerViewModel: This class is the ViewModel for the dealer in the Blackjack game. It handles the game logic, including dealing cards, tracking the player’s and dealer’s points, and determining the winner of each round.

BlackjackGameViewModel: This class is the ViewModel for the Blackjack game. It handles the game logic, including starting a new game, tracking the players’ points, and determining the winner of each game.

BlackjackDealerScreen: This is a composable function that displays the Blackjack dealer screen. It displays the dealer’s and player’s cards, the player’s points, and buttons for the player to hit or stand.

StartScreen: This is a composable function that displays the start screen of the Blackjack game. It displays the winner of the last game and a button to start a new game.

GameScreen: This is a composable function that displays the game screen of the Blackjack game. It displays the player’s and dealer’s cards, the player’s points, and buttons for the player to hit or stand.

# How to Play
Upon launching the app, the start screen is displayed. The player can start a new game by pressing the “Start Game” button. Once the game is started, two cards are dealt to the player and the dealer. The player can then decide to either hit (“Hit Me”) or stand (“Stand”). The goal of the game is to get a total point as close to 21 as possible without going over. If the player goes over 21, they automatically lose. If the player stands, it’s the dealer’s turn. The dealer must keep hitting until they have 17 points or more. If the dealer goes over 21, the player wins. If both the player and the dealer stand, the one with the total points closest to 21 wins.

Additional Features
The game also includes some additional features:

Music: The player can toggle the game music on and off.
Results: The game keeps track of each player’s wins.
End of Game Dialogs: At the end of each game, a dialog is displayed announcing the winner.

# Proyecto de Blackjack
Este proyecto es una implementación del juego de cartas Blackjack, como parte de mis estudios de Programación móvil. El juego está desarrollado en Kotlin y utiliza el framework Jetpack Compose para la interfaz de usuario.

# Estructura del Proyecto
El proyecto consta de varias partes principales:

BlackjackDealerViewModel: Esta clase es el ViewModel para el crupier en el juego de Blackjack. Maneja la lógica del juego, incluyendo el reparto de cartas, el seguimiento de los puntos del jugador y del crupier, y la determinación del ganador de cada ronda.

BlackjackGameViewModel: Esta clase es el ViewModel para el juego de Blackjack. Maneja la lógica del juego, incluyendo el inicio de una nueva partida, el seguimiento de los puntos de los jugadores, y la determinación del ganador de cada partida.

BlackjackDealerScreen: Esta es una función componible que muestra la pantalla del crupier de Blackjack. Muestra las cartas del crupier y del jugador, los puntos del jugador, y los botones para que el jugador pueda pedir carta o plantarse.

StartScreen: Esta es una función componible que muestra la pantalla de inicio del juego de Blackjack. Muestra el ganador de la última partida y un botón para empezar una nueva partida.

GameScreen: Esta es una función componible que muestra la pantalla del juego de Blackjack. Muestra las cartas del jugador y del crupier, los puntos del jugador, y los botones para que el jugador pueda pedir carta o plantarse.

# Cómo Jugar
Al iniciar la aplicación, se muestra la pantalla de inicio. El jugador puede empezar una nueva partida pulsando el botón “Empezar ronda”. Una vez iniciada la partida, se reparten dos cartas al jugador y al crupier. El jugador puede entonces decidir si pedir otra carta (“Dame carta”) o plantarse (“Plantarse”). El objetivo del juego es conseguir un total de puntos lo más cercano posible a 21 sin pasarse. Si el jugador se pasa de 21, pierde automáticamente. Si el jugador se planta, es el turno del crupier. El crupier debe seguir pidiendo cartas hasta que tenga 17 puntos o más. Si el crupier se pasa de 21, el jugador gana. Si ambos, el jugador y el crupier, se plantan, gana quien tenga el total de puntos más cercano a 21.

# Características Adicionales
El juego también incluye algunas características adicionales:

Música: El jugador puede activar o desactivar la música del juego.
Resultados: El juego lleva un registro de las victorias de cada jugador.
Diálogos de Fin de Partida: Al final de cada partida, se muestra un diálogo que anuncia el ganador.
