package fr.yofardev.templatecompose.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.models.Publication
import fr.yofardev.templatecompose.ui.theme.rubik
import fr.yofardev.templatecompose.utils.GetCurrentLocation
import fr.yofardev.templatecompose.viewmodels.PublicationViewModel
import fr.yofardev.templatecompose.viewmodels.UserViewModel

@Composable
fun HomeScreen(
    userViewModel: UserViewModel = viewModel(),
    publicationViewModel: PublicationViewModel = viewModel()
) {
    GetCurrentLocation(onLocationUpdated = { currentPosition ->
        userViewModel.lastKnownPosition.value = currentPosition
        publicationViewModel.getCurrentUserPublications(userViewModel.currentUser.value!!.id)
        publicationViewModel.getPublicationsAround(currentPosition)
    })

    Surface(modifier = Modifier.fillMaxSize()) {
        if (publicationViewModel.currentUserPublications.value.isEmpty()) EmptyScreen() else PublicationListScreen(
            publicationViewModel
        )
    }
}

@Composable
fun EmptyScreen() {
    Column(horizontalAlignment = CenterHorizontally, modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.yofardev1),
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .align(CenterHorizontally),
            alpha = 0.5f
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = R.string.home_add_first),
            color = Color.Black.copy(alpha = 0.5f),
            fontFamily = rubik,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.weight(2f))
    }
}

@Composable
fun PublicationListScreen(publicationViewModel: PublicationViewModel) {
    Column {
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = R.string.home_last_publications_current_user),
            fontSize = 20.sp,
            fontFamily = rubik,
            color = Color.Black.copy(alpha = 0.5f),
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        PublicationList(publicationViewModel.currentUserPublications.value)
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = R.string.home_publications_around),
            fontSize = 20.sp,
            fontFamily = rubik,
            color = Color.Black.copy(alpha = 0.5f),
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        PublicationList(publicationViewModel.publications.value)
    }
}

@Composable
fun PublicationList(publications: List<Publication>) {
    Column {
        publications.forEach { publication ->
            PublicationItem(publication)
        }
    }
}

@Composable
fun PublicationItem(publication: Publication) {
    Row{
       AsyncImage(
            model = publication.image,
            contentDescription = "Publication image",
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop)



        Text(
            text = publication.title,
            fontSize = 20.sp,
            fontFamily = rubik,
            color = Color.Black.copy(alpha = 0.5f),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}