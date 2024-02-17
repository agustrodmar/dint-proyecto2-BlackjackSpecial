package com.arodmar432p.blackjackspecial.cardGames.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.arodmar432p.blackjackspecial.R
import com.arodmar432p.blackjackspecial.cardGames.data.BlackjackRoutes


// val userState: LiveData<User?> = Firebase.auth.currentUserLiveData() // Para comprobar si el usuario se ha conectado satisfactoriamente

@Composable
fun RegisterScreen(viewModel: AuthViewModel, navController: NavController) {


    val snackbarHostState = remember { SnackbarHostState() }
    // val errorMessage by viewModel.errorMessage.observeAsState()

    AuthScreenWallpaper()

    val user by viewModel.userState.observeAsState()
    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate(BlackjackRoutes.MainMenuScreen.route)
        }
    }

    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun AuthScreenWallpaper(modifier: Modifier = Modifier) {
    val wallpaper : Painter = painterResource(id = R.drawable.wallpaper1)
    val textoInicio: Painter = painterResource(id = R.drawable.textomenuprincipal)
    val jokerWallpaper: Painter = painterResource(id = R.drawable.jokerwallpaper)
    val castilloNaipes: Painter = painterResource(id = R.drawable.castillonaipesazul)
    val logoApp: Painter = painterResource(id = R.drawable.logo)
    val texturaTapete: Painter = painterResource(id = R.drawable.texturawallpaper)

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        // val screenWidth = constraints.maxWidth.dp
        // val screenHeight = constraints.maxHeight.dp

        Image(
            painter = wallpaper,
            contentDescription = "Fondo de pantalla de Register Screen",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = textoInicio,
            contentDescription = "Warm welcome to the App",
            modifier = Modifier.align(Alignment.Center)
        )

        Image(
            painter = jokerWallpaper,
            contentDescription = "joker card on the wallpaper board",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(90.dp)
        )

        Image(
            painter = castilloNaipes,
            contentDescription = "A blue card castle",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(110.dp)
                .offset(x = (1).dp)
        )

        Image(
            painter = logoApp,
            contentDescription = "The App logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(350.dp)
        )

        Image(
            painter = texturaTapete,
            contentDescription = "Textura de un tapete",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.05f)
        )

        ColumnaMenu(AuthViewModel())
    }
}

@Composable
fun ColumnaMenu(authViewModel: AuthViewModel) {
    val showDialogNewGame = remember { mutableStateOf(false)}
    val showDialogLoadGame = remember { mutableStateOf(false)}
    val columna: Painter = painterResource(id = R.drawable.columna)

    if (showDialogNewGame.value) {
        DialogNewGame(authViewModel, onDismissRequest = { showDialogNewGame.value = false })
    }

    if (showDialogLoadGame.value) {
        DialogLoadGame(authViewModel, onDismissRequest = { showDialogLoadGame.value = false })
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fraction = 0.15f)
            .background(Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray)))
    ) {
        Image(
            painter = columna,
            contentDescription = "Columna de madera",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column {
            Button(
                onClick = { showDialogNewGame.value = true },
                shape = RectangleShape, // Esquinas completamente cuadradas
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF231513)),
                border = BorderStroke(2.dp, Color(0xFFEAEFC4)),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 200.dp)
                    .sizeIn(minWidth = 150.dp, minHeight = 50.dp)
                    .offset(x = 25.dp)
            ) {
                Text("Nueva Partida", color = Color.White)
            }

            Button(
                onClick = { showDialogLoadGame.value = true },
                shape = RectangleShape, // Esquinas completamente cuadradas
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF231513)),
                border = BorderStroke(2.dp, Color(0xFFEAEFC4)),
                modifier = Modifier
                    .fillMaxWidth((0.8f))
                    .padding(top = 175.dp)
                    .sizeIn(minWidth = 150.dp, minHeight = 50.dp)
                    .offset(x = 25.dp)
            ) {
                Text("Cargar partida", color = Color.White)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogNewGame(authViewModel: AuthViewModel, onDismissRequest: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isAdult by remember { mutableStateOf(false) }
    val wallpaperDialog : Painter = painterResource(id = R.drawable.wallpaperdialog)

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RectangleShape,
            border = BorderStroke(2.dp, Color(0xFFEAEFC4)),
            modifier = Modifier
                .size(width = 600.dp, height = 550.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Transparent
                        ), 0f, 1000f
                    ), shape = RectangleShape
                )
        )  {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = wallpaperDialog,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Crea una cuenta", color = Color.White, fontSize = 24.sp,
                    modifier = Modifier
                        .offset(y = (-80).dp)
                        .align(Alignment.CenterHorizontally))
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Nombre de usuario") },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Gray,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Transparent, // Elimina el indicador cuando el campo de texto está enfocado
                        unfocusedIndicatorColor = Color.Transparent, // Elimina el indicador cuando el campo de texto no está enfocado
                        containerColor = Color(0xFFEAEFC4)
                    ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                        .offset(y = (-30).dp)

                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Gray,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color(0xFFEAEFC4),
                        unfocusedIndicatorColor = Color(0xFFEAEFC4),
                        containerColor = Color(0xFFEAEFC4)
                    ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                        .offset(y = 0.dp)
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Gray,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color(0xFFEAEFC4)

                    ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth(0.80f)
                        .height(50.dp)
                        .offset(y = 30.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .offset(x = (-110).dp, y = 50.dp)
                ) {
                    Checkbox(
                        checked = isAdult,
                        onCheckedChange = { isAdult = it }
                    )
                    Text(
                        text = "Confirmo que soy mayor de 18 años",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
                Button(
                    onClick = {
                        if (isAdult) {
                            authViewModel.createUser(email, password, username)
                            onDismissRequest()
                        } else {
                            authViewModel.errorMessage.value = "Debes ser mayor de 18 años para registrarte."
                        }
                    },
                    shape = RectangleShape, // Esquinas completamente cuadradas
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF231513)), // Marrón por dentro
                    border = BorderStroke(2.dp, Color(0xFFEAEFC4)), // Borde del color especificado
                    modifier = Modifier
                        .fillMaxWidth(0.35f)
                        .sizeIn(minWidth = 150.dp, minHeight = 60.dp)
                        .offset(y = 70.dp)
                ) {
                    Text("Empezar a jugar", color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogLoadGame(authViewModel: AuthViewModel, onDismissRequest: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismissRequest) {
        Column {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") }
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") }
            )
            Button(
                onClick = {
                    authViewModel.signIn(email, password)
                    onDismissRequest()
                }
            ) {
                Text("Aceptar")
            }
        }
    }
}

@Preview(showBackground = true, name = "Preview AuthScreenWallpaper", widthDp = 1920, heightDp = 1080)
@Composable
fun PreviewAuthScreenWallpaper() {
    AuthScreenWallpaper()
}

