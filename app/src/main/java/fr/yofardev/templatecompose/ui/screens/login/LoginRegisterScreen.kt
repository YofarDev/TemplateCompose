package fr.yofardev.templatecompose.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.ui.components.AppButton
import fr.yofardev.templatecompose.ui.components.AppLogo
import fr.yofardev.templatecompose.ui.components.AppTextField
import fr.yofardev.templatecompose.ui.components.AppTile
import fr.yofardev.templatecompose.ui.components.LoadingIndicator
import fr.yofardev.templatecompose.ui.theme.rubik
import fr.yofardev.templatecompose.utils.isValidEmail
import fr.yofardev.templatecompose.utils.isValidPassword
import fr.yofardev.templatecompose.viewmodels.UserViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginRegisterScreen(userViewModel: UserViewModel = viewModel()) {
    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = getComponents(userViewModel),
        )
    }
}

@Composable
private fun getComponents(userViewModel: UserViewModel): @Composable() (ColumnScope.() -> Unit) {

    val isRotated = userViewModel.isRotated.value
    val rotation by animateFloatAsState(
        targetValue = if (isRotated) 360f else 0f,
        animationSpec = tween(
            durationMillis = 800,
           // delayMillis = 0,
            easing = EaseInOut
        ),
        label = "logoRotation"
    )
    return {
        Spacer(modifier = Modifier.height(32.dp))
        Box(modifier = Modifier
            .padding(top = 20.dp)
            .rotate(rotation)) {
            AppLogo(size = 140)
        }
        Text("YOFARDEV", fontFamily = rubik, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(32.dp))
        SwappableScreens(userViewModel)

    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwappableScreens(
    userViewModel: UserViewModel = viewModel(),
) {
    val state = rememberPagerState { 2 }
    HorizontalPager(

        state = state
    ) { page ->
        when (page) {
            0 -> LoginScreen(userViewModel, state)
            1 -> RegisterScreen(userViewModel, state)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    userViewModel: UserViewModel = viewModel(),
    state: PagerState
) {
    val clock = rememberCoroutineScope().coroutineContext[MonotonicFrameClock]
    Column(Modifier.padding(16.dp)) {
        LoginInputField(userViewModel)
        Spacer(modifier = Modifier.weight(1f))
        LineWithText()
        Spacer(modifier = Modifier.weight(1f))
        AppTile(stringResource(id = R.string.to_register), icon = R.drawable.email, onTap = {
            userViewModel.switchPage(state, 1, clock)
        })
    }

}


@Composable
fun LoginInputField(userViewModel: UserViewModel) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AppTextField(
                value = userViewModel.emailInput,
                placeholder = stringResource(id = R.string.email),
                leadingIcon = Icons.Default.Email,
                onValueChange = { newValue -> userViewModel.emailInput.value = newValue },
                hasError = userViewModel.displayErrors.value && !userViewModel.emailInput.value.isValidEmail(),
                errorMessage = stringResource(id = R.string.error_email)
            )

            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(
                value = userViewModel.passwordInput,
                placeholder = stringResource(id = R.string.password),
                leadingIcon = Icons.Default.Lock,
                onValueChange = { newValue -> userViewModel.passwordInput.value = newValue },
                isPassword = true,
                hasError = userViewModel.displayErrors.value && !userViewModel.passwordInput.value.isValidPassword(),
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
            if (userViewModel.isLoading.value) LoadingIndicator() else Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                AppButton(text = stringResource(id = R.string.login), onClick = { userViewModel.signIn() })
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