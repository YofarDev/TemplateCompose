package fr.yofardev.templatecompose.ui.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.ui.components.AppTile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    loginViewModel: LoginViewModel = viewModel(), state:PagerState
) {
    val clock = rememberCoroutineScope().coroutineContext[MonotonicFrameClock]
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RegisterInputField(loginViewModel)
        Spacer(Modifier.weight(1f))
        AppTile("Page de connexion", goBack = true, onTap = {
            loginViewModel.switchPage(state, 0, clock)
        })

    }
}

@Composable
fun RegisterInputField(loginViewModel: LoginViewModel = viewModel()) {
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
                placeholder = "Mot de passe",
                leadingIcon = Icons.Default.Lock,
                onValueChange = { newValue -> loginViewModel.password.value = newValue },
                isPassword = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                value = loginViewModel.confirmPassword,
                placeholder = "Confirmation",
                leadingIcon = Icons.Default.Lock,
                onValueChange = { newValue -> loginViewModel.confirmPassword.value = newValue },
                isPassword = true
            )

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF313352)),
                onClick = { /* Handle login */ }) {
                Text("S'inscrire", color = Color.White)

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterInputField()
}

