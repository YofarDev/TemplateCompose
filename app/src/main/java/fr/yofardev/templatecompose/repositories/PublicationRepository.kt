package fr.yofardev.templatecompose.repositories

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
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

    /*************************************************************************
     GET PUBLICATIONS
     *************************************************************************/
    // Get all publications of an user
    suspend fun getUserPublications(userId:String): List<Publication> {
        return try {
            val querySnapshot = publications.whereEqualTo("userId", userId).get().await()
            val publications = querySnapshot.toObjects(Publication::class.java)
            publications.forEachIndexed { index, publication ->
                publication.id = querySnapshot.documents[index].id
            }
            return publications
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Get publication around a position
    suspend fun getPublicationsAround(position: GeoLocation, radius:Double = 20.0): List<Publication> {
        val currentPositionHash = GeoFireUtils.getGeoHashForLocation(position)
        val querySnapshot = publications.whereEqualTo("location.geoHash", currentPositionHash).get().await()
        val publications = querySnapshot.toObjects(Publication::class.java)
        println("non")

        val publicationsAround = mutableListOf<Publication>()
        publications.forEachIndexed { index, publication ->
            publication.id = querySnapshot.documents[index].id
            if (publication.isInsideRadiusOfPosition(position, radius)) {
                publicationsAround.add(publication)
            }
        }
        Log.d("getPublicationsAround()", "hash: $currentPositionHash // getPublicationsAround: ${publicationsAround.size} // before : ${publications.size}")
        println("hash: $currentPositionHash // getPublicationsAround: ${publicationsAround.size} // before : ${publications.size}")
        return publicationsAround
    }



    /*************************************************************************
     ADD PUBLICATION
     *************************************************************************/
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


    /*************************************************************************
     DELETE PUBLICATION
     *************************************************************************/
    suspend fun deletePublication(publication: Publication): Result<Unit> {
        return try {
            publications.document(publication.id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /*************************************************************************
     UPDATE PUBLICATION
     *************************************************************************/
    suspend fun updatePublication(publication: Publication): Result<Unit> {
        return try {
            publications.document(publication.id).set(publication).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}