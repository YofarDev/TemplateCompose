package fr.yofardev.templatecompose.viewmodels

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.yofardev.templatecompose.models.User
import fr.yofardev.templatecompose.repositories.UserRepository
import fr.yofardev.templatecompose.utils.isValidEmail
import fr.yofardev.templatecompose.utils.isValidPassword
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val singapore = LatLng(1.3521, 103.8198)
    val currentUser = mutableStateOf<User?>(null)
    val lastKnownPosition = mutableStateOf(singapore)
    val currentCameraPositionState = mutableStateOf(
        CameraPositionState(
            position = CameraPosition.fromLatLngZoom(
                singapore, 10f
            )
        )
    )

    // Ui state
    val isInitializing = mutableStateOf(true)
    val displayErrors = mutableStateOf(false)
    val isRotated = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val signUpError = mutableIntStateOf(-1)

    // Input fields
    val emailInput = mutableStateOf("")
    val passwordInput = mutableStateOf("")
    val confirmPasswordInput = mutableStateOf("")


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

        if (!emailInput.value.isValidEmail() || !passwordInput.value.isValidPassword() || passwordInput.value != confirmPasswordInput.value) {
            displayErrors.value = true
            return
        }
        isLoading.value = true
        viewModelScope.launch {
            val firebaseResult = userRepository.signUp(emailInput.value, passwordInput.value)
            currentUser.value = firebaseResult.user
            if (firebaseResult.error != null) {
                signUpError.intValue = firebaseResult.getErrorStringInt()
            }
            isLoading.value = false

        }
    }


    fun signIn() {
        if (!emailInput.value.isValidEmail() || !passwordInput.value.isValidPassword()) {
            displayErrors.value = true
            return
        }
        isLoading.value = true
        viewModelScope.launch {
            currentUser.value = userRepository.signIn(emailInput.value, passwordInput.value)
            isLoading.value = false
        }
    }

    fun signOut() {
        viewModelScope.launch {
            isInitializing.value = true
            userRepository.signOut()
            currentUser.value = null
            delay(300)
            isInitializing.value = false
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            currentUser.value = userRepository.getCurrentUser()
            isInitializing.value = false
        }
    }

}
