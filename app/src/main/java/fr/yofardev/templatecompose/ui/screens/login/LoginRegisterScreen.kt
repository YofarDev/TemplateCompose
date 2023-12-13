package fr.yofardev.templatecompose.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.ui.components.AppTextField
import fr.yofardev.templatecompose.ui.components.AppTile
import fr.yofardev.templatecompose.utils.isValidEmail
import fr.yofardev.templatecompose.utils.isValidPassword
import fr.yofardev.templatecompose.viewmodels.LoginViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginRegisterScreen(loginViewModel: LoginViewModel = viewModel()) {
    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
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
    val isRotated = loginViewModel.isRotated.value
    val rotation by animateFloatAsState(
        targetValue = if (isRotated) 360f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 0,
            easing = LinearEasing
        ),
        label = "logoRotation"
    )
    return {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .padding(top = 20.dp)
                .height(140.dp)
                .width(140.dp)
                .rotate(rotation)
        )
        Text("YOFARDEV", fontFamily = rubik, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(32.dp))

        SwappableScreens(loginViewModel)
    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwappableScreens(
    loginViewModel: LoginViewModel = viewModel(),
) {
    val state = rememberPagerState { 2 }
    HorizontalPager(

        state = state
    ) { page ->
        when (page) {
            0 -> LoginScreen(loginViewModel, state)
            1 -> RegisterScreen(loginViewModel, state)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    state: PagerState
) {
    val clock = rememberCoroutineScope().coroutineContext[MonotonicFrameClock]
    Column(Modifier.padding(16.dp)) {
        LoginInputField(loginViewModel)
        Spacer(modifier = Modifier.weight(1f))
        LineWithText()
        Spacer(modifier = Modifier.weight(1f))
        AppTile(stringResource(id = R.string.to_register), icon = R.drawable.email, onTap = {
            loginViewModel.switchPage(state, 1, clock)
        })
    }

}


@Composable
fun LoginInputField(loginViewModel: LoginViewModel) {
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
            AppTextField(
                value = loginViewModel.email,
                placeholder = stringResource(id = R.string.email),
                leadingIcon = Icons.Default.Email,
                onValueChange = { newValue -> loginViewModel.email.value = newValue },
                hasError = loginViewModel.displayErrors.value && loginViewModel.email.value.isBlank() && !loginViewModel.email.value.isValidEmail(),
                errorMessage = stringResource(id = R.string.error_email)
            )

            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(
                value = loginViewModel.password,
                placeholder = stringResource(id = R.string.password),
                leadingIcon = Icons.Default.Lock,
                onValueChange = { newValue -> loginViewModel.password.value = newValue },
                isPassword = true,
                hasError = loginViewModel.displayErrors.value && loginViewModel.password.value.isBlank() && loginViewModel.password.value.isValidPassword(),
                errorMessage = stringResource(id = R.string.error_password)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.forgot_password),
                color = Color.Black.copy(alpha = 0.8f),
                textDecoration = TextDecoration.Underline,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF313352)),
                onClick = { loginViewModel.signIn() }) {
                Text(stringResource(id = R.string.login), color = Color.White)

            }
        }
    }
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


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginRegisterScreen()
}