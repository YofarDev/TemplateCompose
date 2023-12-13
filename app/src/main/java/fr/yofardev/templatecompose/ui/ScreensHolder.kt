package fr.yofardev.templatecompose.ui

import android.annotation.SuppressLint
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.ui.screens.home.HomeScreen
import fr.yofardev.templatecompose.ui.screens.map.MapScreen
import fr.yofardev.templatecompose.ui.theme.BlueYofardev
import fr.yofardev.templatecompose.viewmodels.UserViewModel

sealed class Screen(val route: String, val titleResId: Int, val icon: ImageVector) {
    data object Home : Screen("home", R.string.tab_home, Icons.Default.Home)
    data object Profile : Screen("map", R.string.tab_map, Icons.Default.Person)
    // Add more screens here
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScreensHolder(userViewModel: UserViewModel = viewModel()) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { AppBottomNavigation(navController) }
    ) {
        NavHost(navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) { HomeScreen(userViewModel) }
            composable(Screen.Profile.route) { MapScreen() }

        }
    }
}

@Composable
fun AppBottomNavigation(navController: NavHostController) {
    BottomNavigation(backgroundColor = BlueYofardev) {
        val screens = listOf(Screen.Home, Screen.Profile) // Add more screens here
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
              //  label = { Text(stringResource(id = screen.titleResId)) },
                selected = currentRoute(navController) == screen.route,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(alpha = 0.4f),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    return navBackStackEntry.value?.destination?.route
}