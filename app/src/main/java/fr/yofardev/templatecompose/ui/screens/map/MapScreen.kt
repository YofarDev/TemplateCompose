package fr.yofardev.templatecompose.ui.screens.map


import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import fr.yofardev.templatecompose.ui.theme.PurpleBlue
import fr.yofardev.templatecompose.viewmodels.PublicationViewModel
import fr.yofardev.templatecompose.viewmodels.UserViewModel

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    userViewModel: UserViewModel = viewModel(),
    publicationViewModel: PublicationViewModel = viewModel()
) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationFinePermissionState =
        rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(locationFinePermissionState.status) {
        when {
            locationFinePermissionState.status.isGranted -> {
                getLocation(userViewModel, fusedLocationClient)
            }
            else -> {
                locationFinePermissionState.launchPermissionRequest()
            }
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize().padding(bottom = 56.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(  modifier = Modifier.fillMaxSize()) {
            MapCompose(userViewModel)
            ButtonGetCurrentLocation(userViewModel)
        }
    }
}

@Composable
fun MapCompose(userViewModel: UserViewModel) {
    GoogleMap(
        cameraPositionState = userViewModel.currentCameraPositionState.value,
        uiSettings = MapUiSettings(
            compassEnabled = false,
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false,
        ),
    ) {
        Marker(
            state = MarkerState(position = userViewModel.lastKnownPosition.value)
        )
    }

}

@Composable
fun ButtonGetCurrentLocation(userViewModel: UserViewModel) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            modifier = Modifier.size(42.dp),
            onClick = { updateCameraToCurrentPosition(userViewModel) },
            containerColor = PurpleBlue.copy(alpha = 0.7f),
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Rounded.LocationOn,
                contentDescription = "Get current location",
                modifier = Modifier.size(28.dp),

            )
        }
    }
}

@SuppressLint("MissingPermission")
fun getLocation(userViewModel: UserViewModel, fusedLocationClient: FusedLocationProviderClient) {
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            location?.let {
                userViewModel.lastKnownPosition.value =
                    LatLng(it.latitude, it.longitude)
                updateCameraToCurrentPosition(userViewModel)
            }
        }
}

fun updateCameraToCurrentPosition(userViewModel: UserViewModel) {
    userViewModel.currentCameraPositionState.value = CameraPositionState(
        position = CameraPosition.fromLatLngZoom(
            userViewModel.lastKnownPosition.value,
            10f
        )
    )
}


