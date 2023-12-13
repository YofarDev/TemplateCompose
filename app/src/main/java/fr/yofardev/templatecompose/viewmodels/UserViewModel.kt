package fr.yofardev.templatecompose.viewmodels

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.yofardev.templatecompose.models.User
import fr.yofardev.templatecompose.repositories.UserRepository
import fr.yofardev.templatecompose.utils.isValidEmail
import fr.yofardev.templatecompose.utils.isValidPassword
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val currentUser = mutableStateOf<User?>(null)

    // Ui state
    val displayErrors = mutableStateOf(false)
    val isRotated = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val signUpError = mutableStateOf("")

    // Input fields
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")


   @OptIn(ExperimentalFoundationApi::class)
   fun switchPage(state: PagerState, page: Int, clock: MonotonicFrameClock?) {
       if (clock == null) return
        viewModelScope.launch(clock) {
            displayErrors.value = false
            isLoading.value = false
            isRotated.value = !isRotated.value
            state.animateScrollToPage(page)
        }
    }

    fun signUp() {

        if (!email.value.isValidEmail() || !password.value.isValidPassword() || password.value != confirmPassword.value)
        {
            displayErrors.value = true
            return
        }
        isLoading.value = true
        viewModelScope.launch {
            val firebaseResult = userRepository.signUp(email.value, password.value)
            currentUser.value = firebaseResult.user
            if (firebaseResult.error != null) {
                signUpError.value = firebaseResult.getMessage()
            }
            isLoading.value = false

        }
    }


    fun signIn() {
        if (!email.value.isValidEmail() || !password.value.isValidPassword())
        {
            displayErrors.value = true
            return
        }
        isLoading.value = true
        viewModelScope.launch {
            currentUser.value = userRepository.signIn(email.value, password.value)
            isLoading.value = false
        }
    }

    fun signOut(){
        viewModelScope.launch {
            userRepository.signOut()
            currentUser.value = null

        }
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            currentUser.value = userRepository.getCurrentUser()
        }
    }

}
