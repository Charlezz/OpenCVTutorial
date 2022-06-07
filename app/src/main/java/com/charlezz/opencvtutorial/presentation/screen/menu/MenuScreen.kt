package com.charlezz.opencvtutorial.presentation.screen.menu

/**
 * @author soohwan.ok
 */

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.charlezz.opencvtutorial.navigation.Screen
import com.charlezz.opencvtutorial.navigation.navigateTo

/**
 * @author soohwan.ok
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    navController: NavHostController,
    onDrawerClick: () -> Unit,
    screens: List<Screen> = emptyList(),
) {
    Scaffold(
        topBar = {
            MenuTopBar(onMenuClick = onDrawerClick)
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier.padding(paddingValues)
            ) {
                MenuContent(
                    screens = screens,
                    onMenuClick = { screen -> navController.navigateTo(screen) }
                )
            }
        }
    )
}

