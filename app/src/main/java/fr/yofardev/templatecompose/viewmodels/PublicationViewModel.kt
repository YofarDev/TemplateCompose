package fr.yofardev.templatecompose.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.yofardev.templatecompose.repositories.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublicationViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val isAddPublicationVisible = mutableStateOf(false)
    val isFabExploded = mutableStateOf(false)

    fun displayAddPublicationScreen() {
        viewModelScope.launch {
            isFabExploded.value = true
            delay(300)
            isAddPublicationVisible.value = true

        }
    }

    fun hideAddPublicationScreen() {
        viewModelScope.launch {
            isAddPublicationVisible.value = false
            delay(100)
            isFabExploded.value = false
        }

    }

}