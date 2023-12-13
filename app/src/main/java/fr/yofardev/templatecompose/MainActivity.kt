 package fr.yofardev.templatecompose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.yofardev.templatecompose.ui.screens.home.HomeScreen
import fr.yofardev.templatecompose.ui.screens.login.LoginRegisterScreen
import fr.yofardev.templatecompose.ui.theme.TemplateComposeTheme
import fr.yofardev.templatecompose.viewmodels.UserViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCurrentUser()
        setContent {
            MyApp()
        }
    }

    private fun getCurrentUser() {
        userViewModel.getCurrentUser()
    }


    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        TemplateComposeTheme {
            Surface(color = Color.White) {
                NavHost(
                    navController = navController,
                    startDestination = if (userViewModel.currentUser.value != null) "homeScreen" else "loginScreen"
                ) {
                    composable("loginScreen") {
                        LoginRegisterScreen(userViewModel)
                    }
                    composable("homeScreen") {
                        HomeScreen(userViewModel)
                    }
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun HomeAppPreview() {
        MyApp()
    }
}