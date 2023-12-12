package fr.yofardev.templatecompose


import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import fr.yofardev.templatecompose.ui.home.HomeScreen
import fr.yofardev.templatecompose.ui.login.LoginRegisterScreen
import fr.yofardev.templatecompose.ui.login.LoginScreen
import fr.yofardev.templatecompose.ui.login.LoginViewModel
import fr.yofardev.templatecompose.ui.theme.TemplateComposeTheme


class MainActivity : ComponentActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        firebaseAuth = Firebase.auth
        setContent {
            MyApp()
        }
    }


    @Composable
    fun MyApp() {

        val navController = rememberNavController()
        TemplateComposeTheme {
            Surface(color = Color.White) {
                NavHost(
                    navController = navController,
                    startDestination = if (firebaseAuth.currentUser != null) "homeScreen" else "loginScreen"
                ) {
                    composable("loginScreen") {
                        LoginRegisterScreen(loginViewModel)
                    }
                    composable("homeScreen") {
                        HomeScreen()
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