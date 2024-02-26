package com.arodmar432p.blackjackspecial.cardGames.ui.results


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.arodmar432p.blackjackspecial.cardGames.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ResultsViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            getUser(uid)
        }
    }

    fun getUser(uid: String) {
        _isLoading.value = true
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(uid)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    val user = document.toObject(User::class.java)
                    _user.value = user
                } else {
                    Log.d(TAG, "No such document")
                }
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
                _isLoading.value = false
            }
    }

    fun saveUser(user: User) {
        userRepository.saveUser(user)
    }
}