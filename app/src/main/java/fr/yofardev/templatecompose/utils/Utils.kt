package fr.yofardev.templatecompose.utils

import com.firebase.geofire.GeoLocation
import com.google.android.gms.maps.model.LatLng
import java.lang.Math.toRadians
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

// Get distance between two points
fun getDistanceBetweenTwoPoints(point1: GeoLocation, point2: GeoLocation): Double {
    val radius = 6371e3
    val lat1 = toRadians(point1.latitude)
    val lat2 = toRadians(point2.latitude)
    val deltaLat = toRadians(point2.latitude - point1.latitude)
    val deltaLng = toRadians(point2.longitude - point1.longitude)
    val a = sin(deltaLat / 2) * sin(deltaLat / 2) +
            cos(lat1) * cos(lat2) *
            sin(deltaLng / 2) * sin(deltaLng / 2)
    val c = 2 * atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
    return radius * c
}