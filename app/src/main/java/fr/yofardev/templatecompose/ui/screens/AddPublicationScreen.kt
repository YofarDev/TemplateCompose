package fr.yofardev.templatecompose.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.ui.components.AppButton
import fr.yofardev.templatecompose.ui.components.AppTextField
import fr.yofardev.templatecompose.ui.components.AppTopBar
import fr.yofardev.templatecompose.ui.components.CameraButton
import fr.yofardev.templatecompose.ui.components.LoadingIndicator
import fr.yofardev.templatecompose.utils.GetCurrentLocation
import fr.yofardev.templatecompose.viewmodels.PublicationViewModel
import fr.yofardev.templatecompose.viewmodels.UserViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPublicationScreen(userViewModel: UserViewModel, publicationViewModel: PublicationViewModel) {
    GetCurrentLocation(onLocationUpdated = { latLng ->
        userViewModel.lastKnownPosition.value = latLng
    })
    BackHandler(enabled = true, onBack = { publicationViewModel.hideAddPublicationScreen() })
    Scaffold(
        containerColor = Color.White,
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.add_publication),
                backArrow = true,
                onBackArrowClicked = { publicationViewModel.hideAddPublicationScreen() })
        }) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            CameraButton(publicationViewModel = publicationViewModel)
            Spacer(modifier = Modifier.height(16.dp))
            PublicationInputField(
                userViewModel = userViewModel,
                publicationViewModel = publicationViewModel
            )
        }
    }
}

@Composable
fun PublicationInputField(
    userViewModel: UserViewModel,
    publicationViewModel: PublicationViewModel
) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFd4d6fd),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AppTextField(
                value = publicationViewModel.titleInput,
                placeholder = stringResource(id = R.string.add_publication_title),
                roundedCornerShape = RoundedCornerShape(12.dp),
                onValueChange = { newValue -> publicationViewModel.titleInput.value = newValue },
                hasError = publicationViewModel.displayErrors.value && publicationViewModel.titleInput.value.isNotBlank(),
                errorMessage = stringResource(id = R.string.add_publication_title)
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(
                value = publicationViewModel.descriptionInput,
                placeholder = stringResource(id = R.string.add_publication_description),
                roundedCornerShape = RoundedCornerShape(12.dp),
                minLines = 5,
                onValueChange = { newValue ->
                    publicationViewModel.descriptionInput.value = newValue
                },
                hasError = publicationViewModel.displayErrors.value && publicationViewModel.descriptionInput.value.isNotBlank(),
                errorMessage = stringResource(id = R.string.add_publication_description)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (publicationViewModel.processing.value) LoadingIndicator() else AppButton(
                    text = stringResource(id = R.string.confirm),
                    onClick = { publicationViewModel.addPublication(userViewModel.lastKnownPosition.value) }
                )
            }
        }
    }
}



