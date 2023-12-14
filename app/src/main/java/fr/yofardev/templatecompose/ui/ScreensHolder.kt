package fr.yofardev.templatecompose.ui


import AnimatedFab
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.ui.components.AppDrawer
import fr.yofardev.templatecompose.ui.components.AppTopBar
import fr.yofardev.templatecompose.ui.screens.home.AddPublicationScreen
import fr.yofardev.templatecompose.ui.screens.home.HomeScreen
import fr.yofardev.templatecompose.ui.screens.map.MapScreen
import fr.yofardev.templatecompose.ui.theme.BlueYofardev
import fr.yofardev.templatecompose.viewmodels.PublicationViewModel
import fr.yofardev.templatecompose.viewmodels.UserViewModel

sealed class Screen(val route: String, val titleResId: Int, val icon: ImageVector) {
    data object Home : Screen("home", R.string.tab_home, Icons.Rounded.Home)
    data object Profile : Screen("map", R.string.tab_map, Icons.Rounded.Map)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScreensHolder(
    userViewModel: UserViewModel = viewModel(),
    publicationViewModel: PublicationViewModel = viewModel()
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (publicationViewModel.isAddPublicationVisible.value) AddPublicationScreen(
        publicationViewModel
    ) else ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            AppDrawer(userViewModel = userViewModel)
        },
    ) {
        Box {
            Scaffold(
                topBar = {
                    AppTopBar(scope, drawerState)
                },
                bottomBar = { AppBottomNavigation(navController, publicationViewModel) },
                // floatingActionButton = { AnimatedFab(publicationViewModel) },
                // floatingActionButtonPosition = FabPosition.Center,


            ) {
                NavHost(navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            userViewModel,
                            publicationViewModel
                        )
                    }
                    composable(Screen.Profile.route) {
                        MapScreen(
                            userViewModel,
                            publicationViewModel
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(bottom = 28.dp),
                contentAlignment = Alignment.BottomCenter) {
                AnimatedFab(publicationViewModel = publicationViewModel)
            }
        }

    }
}

@Composable
fun AppBottomNavigation(
    navController: NavHostController,
    publicationViewModel: PublicationViewModel
) {
    val screens = listOf(Screen.Home, Screen.Profile)
    Box {
        BottomAppBar(backgroundColor = BlueYofardev, contentColor = Color.White) {
            screens.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            screen.icon,
                            contentDescription = null,
                            tint = if (currentRoute(navController) == screen.route) Color.White else Color.White.copy(
                                alpha = 0.4f
                            ),
                            modifier = Modifier.size(32.dp)
                        )
                    },
                    //  label = { Text(stringResource(id = screen.titleResId)) },
                    selected = currentRoute(navController) == screen.route,

                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            //   AnimatedFab(publicationViewModel = publicationViewModel)
        }
    }
}


@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    return navBackStackEntry.value?.destination?.route
}