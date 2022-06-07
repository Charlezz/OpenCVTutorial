package com.charlezz.opencvtutorial.presentation.screen.menu

import androidx.compose.material.icons.Icons
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
fun MenuTopBar(onMenuClick:()->Unit) {
    SmallTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
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
    MenuTopBar(onMenuClick = {})
}