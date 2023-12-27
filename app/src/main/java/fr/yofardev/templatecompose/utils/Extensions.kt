package fr.yofardev.templatecompose.utils

import com.firebase.geofire.GeoLocation
import com.google.android.gms.maps.model.LatLng


fun String.isValidEmail(): Boolean {
    // Define a regular expression pattern for a simple email validation
    val emailPattern = Regex("^\\S+@\\S+\\.\\S+$")
    return emailPattern.matches(this)
}

    // Password must be at least 6 characters long
    fun String.isValidPassword(): Boolean {
        return this.length >= 6
    }





fun GeoLocation.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}

fun LatLng.toGeoLocation(): GeoLocation {
    return GeoLocation(this.latitude, this.longitude)
}

