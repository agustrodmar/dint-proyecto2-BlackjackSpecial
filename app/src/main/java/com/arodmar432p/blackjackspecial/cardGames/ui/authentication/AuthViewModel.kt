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
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth


/**
 * ViewModel for handling authentication-related operations.
 *
 * @property userRepository The UserRepository used for user-related operations.
 */
class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val auth = Firebase.auth
    private val _eventCloseApp = MutableLiveData<Boolean>()
    val eventCloseApp: LiveData<Boolean> get() = _eventCloseApp

    /**
     * LiveData that represents the current user.
     */
    val userState: LiveData<User?> = auth.currentUserLiveData()
    /**
     * MutableLiveData that represents any error messages that occur during authentication operations.
     */
    val errorMessage = MutableLiveData<String>()


    /**
     * Creates a new user with the given email, password, and username.
     *
     * @param email The email of the new user.
     * @param password The password of the new user.
     * @param username The username of the new user.
     */
    fun createUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val firebaseUser = auth.currentUser
                if (firebaseUser != null) {
                    val user = User(firebaseUser.uid, username, email, 0, 0, 0, 0)
                    saveUser(user)
                }
            } else {

                when (task.exception) {
                    is FirebaseAuthUserCollisionException -> {

                        errorMessage.value = "El correo electrónico ya está en uso."
                    }

                    is FirebaseAuthWeakPasswordException -> {

                        errorMessage.value = "La contraseña es demasiado débil."
                    }

                    else -> {

                        errorMessage.value = "Ocurrió un error desconocido."
                    }
                }
            }
        }
    }

    /**
     * Signs in a user with the given email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     */
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


    /**
     * Closes the app.
     */
    fun closeApp() {
        _eventCloseApp.value = true
    }

    /**
     * Resets the close app event after the app is closed.
     */
    fun onAppClosed() {
        _eventCloseApp.value = false
    }


    /**
     * Saves a user to the UserRepository.
     *
     * @param user The user to save.
     */
    private fun saveUser(user: User) {
        userRepository.saveUser(user)
    }

    /**
     * Signs out the current user.
     */
    fun signOut() {
        auth.signOut()
    }
}