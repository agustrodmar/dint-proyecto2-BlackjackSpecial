package com.arodmar432p.blackjackspecial.cardGames.ui.results

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ResultsViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    val user = MutableLiveData<User?>()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            db.collection("users").document(firebaseUser.uid).get().addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                this.user.value = user
                Log.d("ResultsViewModel", "Datos del usuario cargados: $user")
            }.addOnFailureListener { exception ->
                Log.e("ResultsViewModel", "Error al cargar los datos del usuario", exception)
            }
        }
    }

    fun saveUser(user: User) {
        try {
            db.collection("users").document(user.uid).set(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("ResultsViewModel", "Datos del usuario guardados: $user")
                } else {
                    Log.e("ResultsViewModel", "Error al guardar los datos del usuario", task.exception)
                }
            }
        } catch (e: Exception) {
            Log.e("ResultsViewModel", "Error al guardar los datos del usuario", e)
        }
    }
}