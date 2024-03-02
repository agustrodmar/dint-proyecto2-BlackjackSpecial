package com.arodmar432p.blackjackspecial.cardGames.ui.results


import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arodmar432p.blackjackspecial.cardGames.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.arodmar432p.blackjackspecial.cardGames.repository.AvatarRepository
import kotlinx.coroutines.launch


/**
 * ViewModel for handling results-related operations.
 */
class ResultsViewModel(private val avatarRepository: AvatarRepository) : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val _user = MutableStateFlow<User?>(null)
    private val _userAvatar = MutableStateFlow<Bitmap?>(null)
    val userAvatar: StateFlow<Bitmap?> get() = _userAvatar

    init {
        viewModelScope.launch {
            val user = auth.currentUser
            if (user != null) {
                Log.d("ResultsViewModel", "User is not null: ${user.displayName}")
                try {
                    val avatarId = user.displayName.takeIf { !it.isNullOrEmpty() } ?: user.uid
                    val avatarData = avatarRepository.getAvatarData(avatarId)
                    val bitmap = BitmapFactory.decodeByteArray(avatarData, 0, avatarData.size)
                    _userAvatar.value = bitmap
                    Log.d("ResultsViewModel", "Avatar data received")
                } catch (e: Exception) {
                    Log.e("ResultsViewModel", "Error getting avatar data", e)
                }
            } else {
                Log.d("ResultsViewModel", "User is null")
            }
        }
    }

    /**
     * StateFlow that represents the current user.
     */
    val user: StateFlow<User?> get() = _user
    private val _isLoading = MutableStateFlow(true)

    /**
     * StateFlow that represents whether the ViewModel is currently loading data.
     */
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            getUser(uid)
        }
    }


    /**
     * Retrieves a user with the given UID.
     *
     * @param uid The UID of the user to retrieve.
     */
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
}