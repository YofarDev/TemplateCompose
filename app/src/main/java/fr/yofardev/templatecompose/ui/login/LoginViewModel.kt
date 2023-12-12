package fr.yofardev.templatecompose.ui.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val isRotated = mutableStateOf(false)


   @OptIn(ExperimentalFoundationApi::class)
   fun switchPage(state: PagerState, page: Int, clock: MonotonicFrameClock?) {
       if (clock == null) return;
        viewModelScope.launch(clock) {
            isRotated.value = !isRotated.value
            state.animateScrollToPage(page)
        }
    }



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
