package fr.yofardev.templatecompose.models

import com.firebase.geofire.GeoLocation
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import fr.yofardev.templatecompose.utils.getDistanceBetweenTwoPoints

data class Publication(
    var id:String = "",
    val userId:String,
    val title: String,
    val description: String,
    var image: String = "",
    val dateAdded: Timestamp,
    val location: Location,
){
    // No-argument constructor
    constructor() : this("", "", "", "", "", Timestamp.now(), Location())


    fun getLatLng(): LatLng {
        return LatLng(this.location.latitude, this.location.longitude)
    }

    fun getGeoLocation(): GeoLocation {
        return GeoLocation(this.location.latitude, this.location.longitude)
    }

    fun isInsideRadiusOfPosition(center: GeoLocation, radius: Double): Boolean {
        return getDistanceBetweenTwoPoints(center, this.getGeoLocation()) <= radius
    }


}

data class Location(val latitude:Double, val longitude: Double, val geoHash: String){
    // No-argument constructor
    constructor() : this(0.0, 0.0, "")
}


