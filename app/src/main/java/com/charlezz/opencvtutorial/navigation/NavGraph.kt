package com.charlezz.opencvtutorial.navigation

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.charlezz.opencvtutorial.presentation.component.OpenCVDrawerContent
import com.charlezz.opencvtutorial.presentation.component.OpenCVScreen
import com.charlezz.opencvtutorial.presentation.screen.home.HomeScreen
import com.charlezz.opencvtutorial.presentation.screen.menu.MenuScreen
import kotlinx.coroutines.launch

private val itemsWithOpenCVScreen: List<Screen> = listOf(
    *Screen.Menu1Basic.screens,
    *Screen.Menu2Filter.screens,
    *Screen.Menu3GeometryTransformation.screens,
    *Screen.Menu4FeatureExtraction.screens,
    *Screen.Menu5Binarization.screens,
    *Screen.Menu6Extraction.screens,
)

/**
 * @author soohwan.ok
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavGraph() {

    val navController = rememberNavController()
    Surface(color = MaterialTheme.colorScheme.background) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val openDrawer = {
            scope.launch {
                drawerState.open()
            }
        }
        val scrollState = rememberScrollState()
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                OpenCVDrawerContent(
                    modifier = Modifier.verticalScroll(state = scrollState),
                    onScreenClick = { screen ->
                        scope.launch {
                            drawerState.close()
                        }
                        navController.navigateTo(screen = screen)
                    }
                )
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route.value,
            ) {

                composable(
                    route = Screen.Home.route.value
                ) {
                    HomeScreen(onMenuClick = { openDrawer() })
                }

                composable(
                    route = Screen.Menu1Basic.route.value
                ) {
                    MenuScreen(
                        navController = navController,
                        onDrawerClick = { openDrawer() },
                        screens = Screen.Menu1Basic.screens.toList(),
                    )
                }

                composable(
                    route = Screen.Menu2Filter.route.value
                ) {
                    MenuScreen(
                        navController = navController,
                        onDrawerClick = { openDrawer() },
                        screens = Screen.Menu2Filter.screens.toList(),
                    )
                }

                composable(
                    route = Screen.Menu3GeometryTransformation.route.value
                ) {
                    MenuScreen(
                        navController = navController,
                        onDrawerClick = { openDrawer() },
                        screens = Screen.Menu3GeometryTransformation.screens.toList(),
                    )
                }

                composable(
                    route = Screen.Menu4FeatureExtraction.route.value
                ) {
                    MenuScreen(
                        navController = navController,
                        onDrawerClick = { openDrawer() },
                        screens = Screen.Menu4FeatureExtraction.screens.toList(),
                    )
                }

                composable(
                    route = Screen.Menu5Binarization.route.value
                ) {
                    MenuScreen(
                        navController = navController,
                        onDrawerClick = { openDrawer() },
                        screens = Screen.Menu5Binarization.screens.toList(),
                    )
                }

                composable(
                    route = Screen.Menu6Extraction.route.value
                ) {
                    MenuScreen(
                        navController = navController,
                        onDrawerClick = { openDrawer() },
                        screens = Screen.Menu6Extraction.screens.toList(),
                    )
                }

                itemsWithOpenCVScreen.forEach { screen ->
                    composableWithOpenCVScreen(
                        navController = navController,
                        screen = screen,
                    )
                }
            }
        }
    }
}

fun NavGraphBuilder.composableWithOpenCVScreen(
    navController: NavHostController,
    screen: Screen,
) {
    composable(
        route = screen.route.value
    ) {
        OpenCVScreen(
            navController = navController,
            topBarTitle = screen.title,
            content = screen.content?:{}
        )
    }
}

fun NavHostController.navigateTo(screen: Screen) {
    navigate(screen.route.value) {
        if (screen == Screen.Home) {
            popUpTo(Screen.Home.route.value) {
                this.inclusive = true
            }
            launchSingleTop = true
        }
    }
}