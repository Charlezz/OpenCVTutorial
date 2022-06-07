package com.charlezz.opencvtutorial

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.charlezz.opencvtutorial.navigation.SetupNavGraph
import com.charlezz.opencvtutorial.ui.theme.OpenCVTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author soohwan.ok
 */

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenCVTheme {
                SetupNavGraph()
            }
        }
    }
}