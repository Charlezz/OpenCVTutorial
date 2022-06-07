package com.charlezz.opencvtutorial.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.charlezz.opencvtutorial.presentation.screen.menu.MenuTopBar

/**
 * @author soohwan.ok
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onMenuClick: () -> Unit) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            MenuTopBar(onMenuClick = onMenuClick)
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hello World")
        }
    }


}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(onMenuClick = {})
}