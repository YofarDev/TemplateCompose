package fr.yofardev.templatecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTextField(
    value: MutableState<String>,
    placeholder: String,
    leadingIcon: ImageVector? = null,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    hasError: Boolean = false,
    errorMessage: String = "",
    minLines: Int = 1,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(50.dp)
) {
    var passwordVisibility by remember { mutableStateOf(isPassword) }
    var modifier = Modifier
        .fillMaxWidth()
        .background(Color.White, shape = roundedCornerShape)
        .clip(roundedCornerShape)
    if (hasError) {
        modifier = modifier.border(1.dp, Color.Red, roundedCornerShape)
    }

    Column {
        TextField(
            value = value.value,
            onValueChange = onValueChange,
            minLines = minLines ?: 1,
            modifier = modifier,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
               leadingIcon =  if (leadingIcon != null)  {
                   { Icon(leadingIcon, contentDescription = null) } } else {
                   null
               },
            trailingIcon = {
                if (isPassword) IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        if (passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },

            placeholder = { Text(placeholder) },
            visualTransformation = if (passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None
        )

        if (hasError) {
            Text(text = errorMessage, color = Color.Red, fontSize = 12.sp)
        }
    }
}