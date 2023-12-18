package fr.yofardev.templatecompose.models

import com.firebase.geofire.GeoLocation
import com.google.firebase.Timestamp

data class Publication(
    val title: String,
    val description: String,
    var image: String = "",
    val dateAdded: Timestamp,
    val location: GeoLocation
)