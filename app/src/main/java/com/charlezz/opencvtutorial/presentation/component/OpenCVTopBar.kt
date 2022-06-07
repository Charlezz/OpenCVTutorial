package com.charlezz.opencvtutorial.presentation.screen.basic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.charlezz.opencvtutorial.R

/**
 * @author soohwan.ok
 */
@Composable
fun OpenCVTopBar(
    title:String = "",
    onMenuClick:()->Unit = {}
) {
    SmallTopAppBar(
        title = {
            Text(
                text = title,
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
        }

    )
}

@Composable
@Preview
fun OpenCVTopBarPreview() {
    OpenCVTopBar(onMenuClick = {})
}