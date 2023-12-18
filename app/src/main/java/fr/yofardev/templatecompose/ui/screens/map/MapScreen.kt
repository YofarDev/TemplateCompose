package fr.yofardev.templatecompose.ui.screens.map


import android.annotation.SuppressLint
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.ui.components.MapMarker
import fr.yofardev.templatecompose.ui.theme.PurpleBlue
import fr.yofardev.templatecompose.utils.GetCurrentLocation
import fr.yofardev.templatecompose.viewmodels.PublicationViewModel
import fr.yofardev.templatecompose.viewmodels.UserViewModel

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    userViewModel: UserViewModel = viewModel(),
    publicationViewModel: PublicationViewModel = viewModel()
) {
    GetCurrentLocation(onLocationUpdated = { latLng ->
        userViewModel.lastKnownPosition.value = latLng
        updateCameraToCurrentPosition(userViewModel)
    })

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
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
        MapMarker(
            context = LocalContext.current,
            position = userViewModel.lastKnownPosition.value,
            title = "",
            iconResourceId = R.drawable.marker
        )
    }
}

@Composable
fun ButtonGetCurrentLocation(userViewModel: UserViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.BottomEnd
    ) {
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


fun updateCameraToCurrentPosition(userViewModel: UserViewModel) {
    userViewModel.currentCameraPositionState.value = CameraPositionState(
        position = CameraPosition.fromLatLngZoom(
            userViewModel.lastKnownPosition.value,
            10f
        )
    )
}



