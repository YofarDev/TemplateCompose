package fr.yofardev.templatecompose.repositories

import android.util.Log
import androidx.compose.ui.res.stringResource
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.models.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val users = Firebase.firestore.collection(("users"))

    suspend fun signUp(email: String, password: String): FirebaseResult {
        // Call the Firebase authentication API
        var user: User? = null
        var errorString: String? = null
        try {
            val authResult = Firebase.auth.createUserWithEmailAndPassword(email, password).await()

            if (authResult.user == null) {
                return FirebaseResult(null, "User is null")
            }

            // Create our own user object to store in the database
            user = User(authResult.user!!.uid, email)

            // Insert the user into the database
            users.document(user.id).set(user).await()
        } catch (e: Exception) {
            errorString = e.message
            Log.e("UserRepository", "Error signing up", e)
        }
        return FirebaseResult(user, errorString)
    }

    suspend fun signIn(email: String, password: String): User? {
        // Call the Firebase authentication API
        val authResult = Firebase.auth.signInWithEmailAndPassword(email, password).await()

        if (authResult.user == null) {
            return null
        }

        // Get the user object from the database and return it
        return users.document(authResult.user!!.uid).get().await()
            .toObject(User::class.java)!!
    }

    suspend fun signOut() {
        Firebase.auth.signOut()
    }

    suspend fun getCurrentUser(): User? {
        val currentUser = Firebase.auth.currentUser ?: return null
        return users.document(currentUser.uid).get().await()
            .toObject(User::class.java)!!
    }
}

data class FirebaseResult
    (
    val user: User? = null,
    val error: String? = null
){
        fun getMessage():String{
            return when (error){
                "The email address is already in use by another account." -> "Cette adresse email est déjà utilisée"
                else -> "Une erreur est survenue"
            }
        }
    }
