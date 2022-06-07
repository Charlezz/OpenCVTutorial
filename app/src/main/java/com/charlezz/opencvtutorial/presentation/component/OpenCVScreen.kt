package com.charlezz.opencvtutorial.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.charlezz.opencvtutorial.presentation.screen.basic.OpenCVTopBar

/**
 * @author soohwan.ok
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenCVScreen(
    navController:NavHostController,
    topBarTitle:String = "",
    content:@Composable ()->Unit = {}
) {
    Scaffold(
        topBar = {
            OpenCVTopBar(topBarTitle){
                navController.navigateUp()
            }
        },
        content = {
            Surface(
                modifier = Modifier.padding(it),
                content = content
            )
        }
    )
}

@Preview
@Composable
fun ImageInfoScreenPreview() {
    OpenCVScreen(rememberNavController())
}