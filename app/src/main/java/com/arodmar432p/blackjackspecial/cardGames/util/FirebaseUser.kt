package com.arodmar432p.blackjackspecial.cardGames.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


/**
 * Extension function on FirebaseAuth to get a LiveData of the current user.
 *
 * @return A LiveData that will be updated with the current user.
 */
fun FirebaseAuth.currentUserLiveData(): LiveData<User?> {
    val firebaseUserLiveData = MutableLiveData<User?>()
    addAuthStateListener {
        firebaseUserLiveData.value = it.currentUser?.toUser()
    }
    return firebaseUserLiveData
}


/**
 * Extension function on FirebaseUser to convert it to a User.
 *
 * @return The FirebaseUser as a User.
 */
fun FirebaseUser.toUser(): User {
    return User(uid, displayName ?: "", email ?: "", 0, 0, 0, 0)
}

