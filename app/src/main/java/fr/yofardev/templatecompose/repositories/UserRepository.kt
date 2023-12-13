package fr.yofardev.templatecompose.repositories

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import fr.yofardev.templatecompose.models.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val users = Firebase.firestore.collection(("users"))

    suspend fun signUp(email: String, password: String): User? {
        // Call the Firebase authentication API
        val   authResult = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        if (authResult.user == null) {
            return null
        }

        // Create a new user object
        val user = User(authResult.user!!.uid, email)

        // Add the new user object in the database
        users.document(user.id).set(user).await()

        return user
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
}