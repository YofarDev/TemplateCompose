package fr.yofardev.templatecompose.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.MutableLiveData
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
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PublicationViewModel @Inject constructor(private val publicationRepository: PublicationRepository) : ViewModel() {

    // Ui state
    val isAddPublicationVisible = mutableStateOf(false)
    val isFabExploded = mutableStateOf(false)
    val displayErrors = mutableStateOf(false)
    val processing = mutableStateOf(false)
    val hasPublicationBeenAdded: MutableLiveData<Boolean?> = MutableLiveData(null)


    // Input fields
    val titleInput = mutableStateOf("")
    val descriptionInput = mutableStateOf("")
    val photoFile = mutableStateOf<File?>(null)
    val imageBitmap = mutableStateOf<ImageBitmap?>(null)

    val hasAcceptedPermission = mutableStateOf(false)

    fun displayAddPublicationScreen() {
        viewModelScope.launch {
            initInputs()
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

    fun initInputs(){
        titleInput.value = ""
        descriptionInput.value = ""
        photoFile.value = null
        imageBitmap.value = null
    }




    fun addPublication(userId:String, position: LatLng){
        if (titleInput.value.isEmpty() || descriptionInput.value.isEmpty() || imageBitmap.value == null) {
            displayErrors.value = true
            return
        }

        processing.value = true

        val publication = Publication(
            userId = userId,
            title = titleInput.value,
            description = descriptionInput.value,
            dateAdded = Timestamp.now(),
            location = GeoLocation(position.latitude, position.longitude)
        )

        viewModelScope.launch {
           val result = publicationRepository.addPublication(publication, imageBitmap.value!!)
            processing.value = false
            hasPublicationBeenAdded.value = result.isSuccess
        }
    }

}