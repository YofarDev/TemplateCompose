package fr.yofardev.templatecompose.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.yofardev.templatecompose.R


@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    Box(
        modifier = Modifier.background(color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = getComponents(loginViewModel),
        )
    }
}

@Composable
private fun getComponents(loginViewModel: LoginViewModel): @Composable() (ColumnScope.() -> Unit) {
    val rubik = FontFamily(
        Font(R.font.rubik, FontWeight.Normal),
    )
    return {
        Image(
            painter = painterResource(R.drawable.logo), // Replace "logo" with your actual logo resource
            contentDescription = "App Logo",
            modifier = Modifier
                .padding(top = 20.dp)
                .height(140.dp)
                .width(140.dp)
        )
        Text("YOFARDEV", fontFamily = rubik, fontWeight = FontWeight.Bold, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(32.dp))

        LoginField(loginViewModel)
        Spacer(modifier = Modifier.weight(1f))
        LineWithText()
        Spacer(modifier = Modifier.weight(1f))
        AppTile("Créer un compte", onTap = {})
    }


}

@Composable
fun LoginField(loginViewModel: LoginViewModel) {

    return Box(
        modifier = Modifier
            .background(
                color = Color(0xFFd4d6fd),
                shape = RoundedCornerShape(50.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top
        ) {
            CustomTextField(
                value = loginViewModel.email,
                placeholder = "Email",
                leadingIcon = Icons.Default.Email,
                onValueChange = { newValue -> loginViewModel.email.value = newValue }
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                value = loginViewModel.password,
                placeholder = "Password",
                leadingIcon = Icons.Default.Lock,
                onValueChange = { newValue -> loginViewModel.password.value = newValue },
                isPassword = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Mot de pass oublié?",
                color = Color.Black.copy(alpha = 0.8f),
                textDecoration = TextDecoration.Underline,
                 fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF313352)),
                onClick = { /* Handle login */ }) {
                Text("Se connecter", color = Color.White)

            }
        }
    }
}

@Composable
fun CustomTextField(
    value: MutableState<String>,
    placeholder: String,
    leadingIcon: ImageVector,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    var passwordVisibility by remember { mutableStateOf(isPassword) }

    TextField(
        value = value.value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(50.dp))
            .clip(RoundedCornerShape(50.dp)),
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
                        if (passwordVisibility) com.google.android.material.R.drawable.design_ic_visibility_off else com.google.android.material.R.drawable.design_ic_visibility
                    ),
                    contentDescription = "Toggle password visibility"
                )
            }
        },
        placeholder = { Text(placeholder) },
        visualTransformation = if (passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun LineWithText() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            color = Color.Black.copy(alpha = 0.8f),
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
        )
        Text(
            text = "ou",
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Black.copy(alpha = 0.8f)
        )
        Divider(
            color = Color.Black.copy(alpha = 0.8f),
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
        )
    }
}

@Composable
fun AppTile(title: String, onTap: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.Gray)
            .clickable(onClick = onTap)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.email),
                contentDescription = null,
                modifier = Modifier.height(20.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}