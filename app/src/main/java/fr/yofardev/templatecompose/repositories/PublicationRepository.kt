package fr.yofardev.templatecompose.repositories

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import fr.yofardev.templatecompose.models.Publication
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class PublicationRepository @Inject constructor() {
    private val publications = Firebase.firestore.collection(("publications"))

    suspend fun addPublication(
        publication: Publication,
        imageBytes: ImageBitmap
    ): Result<Publication> {
        return try {
            uploadImage(imageBytes) { imagePath ->
                publication.image = imagePath
                publications.add(publication).await()
            }
            Result.success(publication)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun uploadImage(
        imageBitmap: ImageBitmap,
        onSuccess: suspend (imagePath: String) -> Unit
    ) {
        val bitmap: Bitmap = imageBitmap.asAndroidBitmap()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val byteArray: ByteArray = stream.toByteArray()
        val ref =
            Firebase.storage.getReference("images/${Firebase.auth.currentUser?.uid}/${System.currentTimeMillis()}-photo.jpg")
        ref.putBytes(byteArray).await()
        onSuccess(ref.downloadUrl.await().toString())
    }

}