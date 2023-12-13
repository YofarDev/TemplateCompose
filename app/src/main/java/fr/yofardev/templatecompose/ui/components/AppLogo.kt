package fr.yofardev.templatecompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.yofardev.templatecompose.R

@Composable
fun AppLogo(size:Int) {
      Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = "App Logo",
        modifier = Modifier
            .height(size.dp)
            .width(size.dp)
    )
}