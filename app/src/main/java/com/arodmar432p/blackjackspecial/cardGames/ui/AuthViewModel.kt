package com.arodmar432p.blackjackspecial.cardGames.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.arodmar432p.blackjackspecial.cardGames.util.currentUserLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {
    private val auth = Firebase.auth

    val userState: LiveData<User?> = auth.currentUserLiveData()



    fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Successfully registered
            } else {
                // I must handle the error situation
            }
        }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Log in successful
            } else {
                // I must handle the error situation
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