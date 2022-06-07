package com.charlezz.opencvtutorial.presentation.screen.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs

/**
 * @author soohwan.ok
 */
@Composable
fun BrightnessContent() {
    val context = LocalContext.current
    var sliderValue by remember { mutableStateOf(50.0f) }
    val src: Mat = Utils.loadResource(context, R.drawable.lenna, Imgcodecs.IMREAD_GRAYSCALE)
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = sliderValue,
            valueRange = 0f.rangeTo(100f),
            onValueChange = {
                sliderValue = it
            }
        )
        val additionalBrightness = sliderValue - 50f
        val src2 = Scalar.all(additionalBrightness.toDouble())
        val dst = Mat()
        Core.add(src, src2, dst)
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = dst.toBitmap().asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth

        )
    }

}