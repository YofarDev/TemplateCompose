package fr.yofardev.templatecompose.ui.screens.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.ui.components.AppButton
import fr.yofardev.templatecompose.ui.components.AppTextField
import fr.yofardev.templatecompose.ui.components.AppTile
import fr.yofardev.templatecompose.ui.components.LoadingIndicator
import fr.yofardev.templatecompose.utils.isValidEmail
import fr.yofardev.templatecompose.utils.isValidPassword
import fr.yofardev.templatecompose.viewmodels.UserViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    userViewModel: UserViewModel = viewModel(), state: PagerState
) {
    val clock = rememberCoroutineScope().coroutineContext[MonotonicFrameClock]
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RegisterInputField(userViewModel)
        Spacer(Modifier.weight(1f))
        AppTile(stringResource(id = R.string.to_login), goBack = true, onTap = {
            userViewModel.switchPage(state, 0, clock)
        })

    }
}

@Composable
fun RegisterInputField(userViewModel: UserViewModel = viewModel()) {
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
                hasError = userViewModel.displayErrors.value &&  !userViewModel.emailInput.value.isValidEmail(),
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
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(
                value = userViewModel.confirmPasswordInput,
                placeholder = stringResource(id = R.string.confirm_password),
                leadingIcon = Icons.Default.Lock,
                onValueChange = { newValue -> userViewModel.confirmPasswordInput.value = newValue },
                isPassword = true,
                hasError = userViewModel.displayErrors.value && userViewModel.passwordInput.value != userViewModel.confirmPasswordInput.value && userViewModel.confirmPasswordInput.value.isValidPassword(),
                errorMessage = stringResource(id = R.string.error_confirm_password)
            )

            Spacer(modifier = Modifier.height(10.dp))
            if (userViewModel.signUpError.intValue != -1) {
                Text(
                    text =  stringResource(id = userViewModel.signUpError.intValue),
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }
            if (userViewModel.isLoading.value) LoadingIndicator() else Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
               AppButton(text = stringResource(id = R.string.register), onClick = { userViewModel.signUp() })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterInputField()
}

