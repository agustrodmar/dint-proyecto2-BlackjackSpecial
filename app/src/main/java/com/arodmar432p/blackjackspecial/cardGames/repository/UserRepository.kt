package com.arodmar432p.blackjackspecial.cardGames.repository

import android.util.Log
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


/**
 * Repository for managing users in Firestore.
 */
class UserRepository {
    private val db = Firebase.firestore

    /**
     * Saves a user to Firestore.
     *
     * @param user The user to save.
     */
    fun saveUser(user: User) {
        db.collection("users").document(user.uid).set(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("UserRepository", "Datos del usuario guardados: $user")
            } else {
                Log.e("UserRepository", "Error al guardar usuario")
            }
        }
    }
}