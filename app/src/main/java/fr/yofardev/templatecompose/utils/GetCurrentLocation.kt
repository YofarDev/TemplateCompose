package fr.yofardev.templatecompose.utils

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetCurrentLocation(onLocationUpdated: (LatLng) -> Unit) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationFinePermissionState =
        rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(locationFinePermissionState.status) {
        when {
            locationFinePermissionState.status.isGranted -> {
                getLocation(fusedLocationClient, onLocationUpdated)
            }
            else -> {
                locationFinePermissionState.launchPermissionRequest()
            }
        }
    }
}


@SuppressLint("MissingPermission")
private fun getLocation(fusedLocationClient: FusedLocationProviderClient, onLocationUpdated: (LatLng) -> Unit) {
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            location?.let {
               onLocationUpdated(LatLng(it.latitude, it.longitude))
            }
        }
}
