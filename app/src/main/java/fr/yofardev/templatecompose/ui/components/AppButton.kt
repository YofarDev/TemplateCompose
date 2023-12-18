package fr.yofardev.templatecompose.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import fr.yofardev.templatecompose.ui.theme.DarkBlueYofardev

@Composable
fun AppButton(text:String, onClick: () -> Unit) {
    Button(
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlueYofardev),
        onClick = { onClick() }) {
        Text(text, color = Color.White)
    }
}