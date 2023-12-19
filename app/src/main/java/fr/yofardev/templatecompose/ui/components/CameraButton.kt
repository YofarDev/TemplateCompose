package fr.yofardev.templatecompose.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.ManagedActivityResultLauncher
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.viewmodels.PublicationViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraButton(publicationViewModel: PublicationViewModel) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val takePicture =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                /*val bitmap =
                    BitmapFactory.decodeFile(publicationViewModel.photoFile.value?.absolutePath)
                publicationViewModel.imageBitmap.value = bitmap?.asImageBitmap()*/
                checkOrientation(publicationViewModel)
            }
        }

    LaunchedEffect(cameraPermissionState.status) {
        when {
            cameraPermissionState.status.isGranted -> {
                if (publicationViewModel.hasAcceptedPermission.value) {
                    takePicture(
                        context = context,
                        publicationViewModel = publicationViewModel,
                        takePicture = takePicture
                    )
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
                   takePicture(
                            context = context,
                            publicationViewModel = publicationViewModel,
                            takePicture = takePicture
                        )
                } else {
                    cameraPermissionState.launchPermissionRequest()
                    publicationViewModel.hasAcceptedPermission.value = true
                }

            }
    ) {
        if (publicationViewModel.imageBitmap.value == null) {
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
                Text(
                    stringResource(id = R.string.add_publication_add_image),
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = BitmapPainter(publicationViewModel.imageBitmap.value!!),
                contentScale = ContentScale.FillWidth,
                contentDescription = "Taken picture"
            )
        }
    }

}

fun createImageFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}

fun checkOrientation(publicationViewModel: PublicationViewModel){
    publicationViewModel.photoFile.value?.let { file ->
        val exif = ExifInterface(file.absolutePath)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )

        var bitmap = BitmapFactory.decodeFile(file.absolutePath)
        bitmap = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> bitmap.rotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> bitmap.rotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> bitmap.rotate(270f)
            else -> bitmap
        }

        publicationViewModel.imageBitmap.value = bitmap?.asImageBitmap()
    }
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

@OptIn(DelicateCoroutinesApi::class)
fun takePicture(
    context: Context,
    publicationViewModel: PublicationViewModel,
    takePicture: ManagedActivityResultLauncher<Uri, Boolean>
) {
    GlobalScope.launch {
        publicationViewModel.photoFile.value = createImageFile(context)
        publicationViewModel.photoFile.value?.let { file ->
            try {
                val photoUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )
                takePicture.launch(photoUri)
               // Rotate the image if needed
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}

