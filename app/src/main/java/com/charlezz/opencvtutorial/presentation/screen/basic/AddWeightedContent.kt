package com.charlezz.opencvtutorial.presentation.screen.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.presentation.component.SliderImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs

/**
 * @author soohwan.ok
 */
@Composable
fun AddWeightedContent() {
    val maxValue = 100f
    val context = LocalContext.current
    val lenna = Utils.loadResource(
        context,
        R.drawable.lenna,
        Imgcodecs.IMREAD_GRAYSCALE
    )

    val runa = Utils.loadResource(
        context,
        R.drawable.runa,
        Imgcodecs.IMREAD_GRAYSCALE
    )

    val dst = Mat()
    var sliderValue by remember { mutableStateOf(50.0f) }
    val alpha = (sliderValue / maxValue).toDouble()
    val beta = 1.0 - alpha
    Core.addWeighted(lenna, alpha, runa, beta, 0.0, dst)

    SliderImageCard(
        value = sliderValue,
        valueRange = 0f.rangeTo(maxValue),
        onValueChange = {
            sliderValue = it
        },
        imageBitmap = dst.toBitmap().asImageBitmap()
    )

}