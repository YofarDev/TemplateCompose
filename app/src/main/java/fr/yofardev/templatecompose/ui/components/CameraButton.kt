package fr.yofardev.templatecompose.ui.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.viewmodels.PublicationViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraButton(publicationViewModel: PublicationViewModel) {

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val takePicture =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
           publicationViewModel.imageBitmap.value = bitmap?.asImageBitmap()
        }

    LaunchedEffect(cameraPermissionState.status) {
        when {
            cameraPermissionState.status.isGranted -> {
                if (publicationViewModel.hasAcceptedPermission.value) {
                    takePicture.launch(null)
                    publicationViewModel.hasAcceptedPermission.value = false
                }
            }
         
            else -> {
                publicationViewModel.hasAcceptedPermission.value = false
            }
        }
    }

    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .background(
                color = Color.LightGray.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                if (cameraPermissionState.status.isGranted) {
                    takePicture.launch(null)
                } else {
                    cameraPermissionState.launchPermissionRequest()
                    publicationViewModel.hasAcceptedPermission.value = true
                }

            }
    ) {
        if ( publicationViewModel.imageBitmap.value == null) {
            Column(
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Image,
                    contentDescription = "Take picture",
                    modifier = Modifier
                        .size(42.dp)
                )
                Text(stringResource(id = R.string.add_publication_add_image), fontWeight = FontWeight.Bold)
            }
        } else {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = BitmapPainter( publicationViewModel.imageBitmap.value!!),
                contentScale = ContentScale.FillWidth,
                contentDescription = "Taken picture"
            )
        }
    }

}

