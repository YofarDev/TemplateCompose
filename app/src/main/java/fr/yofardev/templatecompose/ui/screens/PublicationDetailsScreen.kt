package fr.yofardev.templatecompose.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.yofardev.templatecompose.models.Publication
import fr.yofardev.templatecompose.ui.components.AppTopBar
import fr.yofardev.templatecompose.viewmodels.PublicationViewModel
import fr.yofardev.templatecompose.viewmodels.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PublicationDetailsScreen(
    userViewModel: UserViewModel,
    publicationViewModel: PublicationViewModel,
    publication: Publication
) {
    BackHandler(enabled = true, onBack = { publicationViewModel.hideAddPublicationScreen() })
    Scaffold(
        containerColor = Color.White,
        topBar = {
            AppTopBar(
                title = publication.title,
                backArrow = true,
                onBackArrowClicked = { publicationViewModel.hideAddPublicationScreen() })
        }) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            PublicationImage(publication = publication)
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun PublicationImage(publication: Publication) {
    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = publication.image,
            contentScale = ContentScale.FillWidth,
            contentDescription = "Publication image",
        )
    }
}