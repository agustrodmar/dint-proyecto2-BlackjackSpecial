package com.arodmar432p.blackjackspecial.cardGames.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.arodmar432p.blackjackspecial.cardGames.util.currentUserLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {
    private val auth = Firebase.auth

    val userState: LiveData<User?> = auth.currentUserLiveData()
    val errorMessage = MutableLiveData<String>()

    fun createUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Usuario registrado con éxito
                val firebaseUser = auth.currentUser
                if (firebaseUser != null) {
                    val user = User(firebaseUser.uid, username, email, 0, 0, 0, 0)
                    saveUser(user)
                }
            } else {
                // Debo manejar la situación de error
                val exception = task.exception
                if (exception is FirebaseAuthUserCollisionException) {
                    // El correo electrónico ya está en uso
                    errorMessage.value = "El correo electrónico ya está en uso."
                } else if (exception is FirebaseAuthWeakPasswordException) {
                    // La contraseña es demasiado débil
                    errorMessage.value = "La contraseña es demasiado débil."
                }
                else {
                    // Otro error ocurrió
                    errorMessage.value = "Ocurrió un error desconocido."
                }
            }
        }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                }
            }
    }

    fun saveUser(user: User) {
        val db = Firebase.firestore
        db.collection("users").document(user.uid).set(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // El usuario se ha guardado con éxito
            } else {
                // Ha ocurrido un error
            }
        }
    }


    fun signOut() {
        auth.signOut()
    }
}