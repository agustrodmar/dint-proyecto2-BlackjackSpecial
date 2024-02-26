package com.arodmar432p.blackjackspecial.cardGames.ui.authentication

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.arodmar432p.blackjackspecial.cardGames.repository.UserRepository
import com.arodmar432p.blackjackspecial.cardGames.util.currentUserLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import kotlin.math.max

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val auth = Firebase.auth

    val userState: LiveData<User?> = auth.currentUserLiveData()
    val errorMessage = MutableLiveData<String>()

    fun createUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val firebaseUser = auth.currentUser
                if (firebaseUser != null) {
                    val user = User(firebaseUser.uid, username, email, 0, 0, 0, 0)
                    saveUser(user)
                }
            } else {

                val exception = task.exception
                if (exception is FirebaseAuthUserCollisionException) {

                    errorMessage.value = "El correo electrónico ya está en uso."
                } else if (exception is FirebaseAuthWeakPasswordException) {

                    errorMessage.value = "La contraseña es demasiado débil."
                }
                else {

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
        userRepository.saveUser(user)
    }

    fun signOut() {
        auth.signOut()
    }
}