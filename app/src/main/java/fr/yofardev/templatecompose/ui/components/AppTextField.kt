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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.R

@Composable
fun AppTextField(
    value: MutableState<String>,
    placeholder: String,
    leadingIcon: ImageVector,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    hasError:Boolean = false,
    errorMessage:String = ""
) {
    var passwordVisibility by remember { mutableStateOf(isPassword) }
    var modifier = Modifier
        .fillMaxWidth()
        .background(Color.White, shape = RoundedCornerShape(50.dp))
        .clip(RoundedCornerShape(50.dp))
    if (hasError) {
        modifier = modifier.border(1.dp, Color.Red, RoundedCornerShape(50.dp))
    }

    Column {
        TextField(
            value = value.value,
            onValueChange = onValueChange,
            modifier = modifier,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = { Icon(leadingIcon, contentDescription = null) },
            trailingIcon = {
                if (isPassword) IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        painter = painterResource(
                            if (passwordVisibility) R.drawable.design_ic_visibility_off else R.drawable.design_ic_visibility
                        ),
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