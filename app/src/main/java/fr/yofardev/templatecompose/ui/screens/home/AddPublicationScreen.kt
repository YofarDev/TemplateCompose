package fr.yofardev.templatecompose.ui.screens.home

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.ui.components.AppTopBar
import fr.yofardev.templatecompose.viewmodels.PublicationViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPublicationScreen(publicationViewModel: PublicationViewModel) {

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
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add Publication")
            }
        }
    }
}

