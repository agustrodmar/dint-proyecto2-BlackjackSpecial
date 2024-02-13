package com.arodmar432p.blackjackspecial.cardGames.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

fun FirebaseAuth.currentUserLiveData(): LiveData<User?> {
    val firebaseUserLiveData = MutableLiveData<User?>()
    addAuthStateListener {
        firebaseUserLiveData.value = it.currentUser?.toUser()
    }
    return firebaseUserLiveData
}

fun FirebaseUser.toUser(): User {
    return User(uid, displayName ?: "", email ?: "", 0, 0, 0, 0)
}

