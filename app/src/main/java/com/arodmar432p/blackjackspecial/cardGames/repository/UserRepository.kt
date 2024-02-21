package com.arodmar432p.blackjackspecial.cardGames.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRepository {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    val user = MutableLiveData<User?>()

    fun getUser(): LiveData<User?> {
        loadUserData()
        return user
    }

    private fun loadUserData() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            db.collection("users").document(firebaseUser.uid).get().addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                this.user.value = user
            }
        }
    }

    fun saveUser(user: User) {
        db.collection("users").document(user.uid).set(user)
    }
}