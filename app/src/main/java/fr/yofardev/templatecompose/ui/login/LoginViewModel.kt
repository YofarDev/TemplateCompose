package fr.yofardev.templatecompose.ui.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class LoginViewModel : ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")


    fun handleLogin() {
        Firebase.auth.signInWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    val userState = Firebase.auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                }
            }
    }
}
