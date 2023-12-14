package fr.yofardev.templatecompose.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.yofardev.templatecompose.viewmodels.PublicationViewModel
import fr.yofardev.templatecompose.viewmodels.UserViewModel

@Composable
fun HomeScreen(
    userViewModel: UserViewModel = viewModel(),
    publicationViewModel: PublicationViewModel = viewModel()
) {
   Surface( modifier = Modifier.fillMaxSize(),
       color = Color.Gray){
        Column {
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}