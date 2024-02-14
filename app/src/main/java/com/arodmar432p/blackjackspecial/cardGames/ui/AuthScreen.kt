package com.arodmar432p.blackjackspecial.cardGames.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arodmar432p.blackjackspecial.cardGames.data.BlackjackRoutes


// val userState: LiveData<User?> = Firebase.auth.currentUserLiveData() // Para comprobar si el usuario se ha conectado satisfactoriamente

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: AuthViewModel, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isAdult by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var isSignInDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage by viewModel.errorMessage.observeAsState()

    val user by viewModel.userState.observeAsState()
    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate(BlackjackRoutes.MainMenuScreen.route)
        }
    }

    Column {
        Button(
            onClick = {
                isSignInDialog = true
                showDialog = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
            border = BorderStroke(2.dp, Color.White),
            modifier = Modifier.sizeIn(minWidth = 200.dp, minHeight = 50.dp)
        ) {
            Text("Iniciar sesión", color = Color.Black)
        }

        Button(
            onClick = {
                isSignInDialog = false
                showDialog = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
            border = BorderStroke(2.dp, Color.White),
            modifier = Modifier.sizeIn(minWidth = 200.dp, minHeight = 50.dp)
        ) {
            Text("Registrarse", color = Color.Black)
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(if (isSignInDialog) "Iniciar sesión" else "Registrarse") },
                text = {
                    Column {
                        TextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Nombre de usuario") }
                        )
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
                        if (!isSignInDialog) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = isAdult,
                                    onCheckedChange = { isAdult = it }
                                )
                                Text(text = "Confirmo que soy mayor de 18 años")
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (isAdult || isSignInDialog) {
                                if (isSignInDialog) {
                                    viewModel.signIn(email, password)
                                } else {
                                    viewModel.createUser(email, password, username)
                                }
                                showDialog = false
                            } else {
                                viewModel.errorMessage.value = "Debes ser mayor de 18 años para registrarte."
                            }
                        }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }

    SnackbarHost(hostState = snackbarHostState)
}
