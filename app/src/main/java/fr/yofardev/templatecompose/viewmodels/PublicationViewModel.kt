package fr.yofardev.templatecompose.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.geofire.GeoLocation
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.yofardev.templatecompose.models.Publication
import fr.yofardev.templatecompose.repositories.PublicationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublicationViewModel @Inject constructor(private val publicationRepository: PublicationRepository) : ViewModel() {

    // Ui state
    val isAddPublicationVisible = mutableStateOf(false)
    val isFabExploded = mutableStateOf(false)
    val displayErrors = mutableStateOf(false)
    val processing = mutableStateOf(false)


    // Input fields
    val titleInput = mutableStateOf("")
    val descriptionInput = mutableStateOf("")
    val imageBitmap = mutableStateOf<ImageBitmap?>(null)

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




    fun addPublication(position: LatLng){
        if (titleInput.value.isEmpty() || descriptionInput.value.isEmpty() || imageBitmap.value == null) {
            displayErrors.value = true
            return
        }

        val publication = Publication(
            title = titleInput.value,
            description = descriptionInput.value,
            dateAdded = Timestamp.now(),
            location = GeoLocation(position.latitude, position.longitude)
        )

        viewModelScope.launch {
            publicationRepository.addPublication(publication, imageBitmap.value!!)
        }
    }

}