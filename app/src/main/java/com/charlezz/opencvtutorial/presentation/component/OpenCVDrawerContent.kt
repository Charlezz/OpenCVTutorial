package com.charlezz.opencvtutorial.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.navigation.Screen

/**
 * @author soohwan.ok
 */
private data class DrawerItem(
    val icon: ImageVector,
    val screen: Screen
)

private val drawerItems = listOf(
    DrawerItem(Icons.Filled.Home, Screen.Home),
    DrawerItem(Icons.Filled.List, Screen.Menu1Basic),
    DrawerItem(Icons.Filled.List, Screen.Menu2Filter),
    DrawerItem(Icons.Filled.List, Screen.Menu3GeometryTransformation),
    DrawerItem(Icons.Filled.List, Screen.Menu4FeatureExtraction),
    DrawerItem(Icons.Filled.List, Screen.Menu5Binarization),
    DrawerItem(Icons.Filled.List, Screen.Menu6Extraction),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenCVDrawerContent(
    modifier: Modifier = Modifier,
    onScreenClick: (Screen) -> Unit = {}
) {

    var selectedItem: Screen by remember { mutableStateOf(Screen.Home) }

    Column(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "App icon"
        )
        drawerItems.forEach { item ->
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "Home"
                    )
                },
                label = {
                    Text(text = item.screen.title)
                },
                selected = selectedItem == item.screen,
                onClick = {
                    selectedItem = item.screen
                    onScreenClick(item.screen)
                }
            )
        }
    }
}

@Preview
@Composable
fun OpenCVDrawerPreview() {
    OpenCVDrawerContent()
}