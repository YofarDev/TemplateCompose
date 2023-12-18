package fr.yofardev.templatecompose.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublicationViewModel @Inject constructor() : ViewModel() {

    // Ui state
    val isAddPublicationVisible = mutableStateOf(false)
    val isFabExploded = mutableStateOf(false)
    val displayErrors = mutableStateOf(false)


    // Input fields
    val titleInput = mutableStateOf("")
    val descriptionInput = mutableStateOf("")

    val hasAcceptedPermission = mutableStateOf(false)

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




    fun addPublication(){}

}