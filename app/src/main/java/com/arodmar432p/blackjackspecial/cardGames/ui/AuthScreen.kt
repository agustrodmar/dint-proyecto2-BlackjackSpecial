package com.arodmar432p.blackjackspecial.cardGames.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.arodmar432p.blackjackspecial.cardGames.util.currentUserLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isAdult by remember { mutableStateOf(false) }
    val userState: LiveData<User?> = Firebase.auth.currentUserLiveData() // Para comprobar si el usuario se ha conectado satisfactoriamente

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
        Checkbox(
            checked = isAdult,
            onCheckedChange = { isAdult = it }
        )
        Button(onClick = {
            if (isAdult) {
                viewModel.createUser(email, password)
                // tengo que guardar el nombre de usuario y otros datos en Firestore
            } else {
                // Muestra un mensaje de error
            }
        }) {
            Text("Registrarse")
        }
    }
}