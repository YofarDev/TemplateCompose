package fr.yofardev.templatecompose.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.yofardev.templatecompose.ui.theme.BlueYofardev


@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.width(32.dp),
        color = BlueYofardev,
        trackColor = Color.LightGray,
    )


}