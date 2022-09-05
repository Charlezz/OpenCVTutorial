package com.charlezz.opencvtutorial.presentation.screen.binary

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.SliderImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

@Composable
fun AdaptiveThresholdContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.sudoku)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    val graySrc by remember {
        mutableStateOf(Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }

    val dst = Mat()
    var size by remember { mutableStateOf(3f) }

    Imgproc.adaptiveThreshold(
        graySrc,
        dst,
        255.0,
        Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
        Imgproc.THRESH_BINARY,
        if (size.toInt() % 2 == 0) {
            size.toInt() -1  // Block size must be odd number
        } else {
            size.toInt()
        },
        5.0
    )

    SliderImageCard(
        value = size,
        onValueChange = { size = it },
        valueRange = 3f..500f,
        title = "Block = $size",
        imageBitmap = dst.toBitmap().asImageBitmap()
    )
}