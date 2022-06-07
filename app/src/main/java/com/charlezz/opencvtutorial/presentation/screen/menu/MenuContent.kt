package com.charlezz.opencvtutorial.presentation.screen.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.navigation.Screen

/**
 * @author soohwan.ok
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuContent(
    modifier: Modifier = Modifier,
    screens: List<Screen> = emptyList(),
    onMenuClick: (Screen) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(state = scrollState)
    ) {

        screens.forEach { screen ->
            OutlinedCard(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = {
                    onMenuClick(screen)
                }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = screen.title
                )
            }
        }

    }
}

@Composable
@Preview
fun MenuContentPreview() {
    MenuContent()
}

