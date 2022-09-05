package com.charlezz.opencvtutorial.presentation.screen.filter

import android.content.res.Configuration
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.DoubleSliderImageCard
import com.charlezz.opencvtutorial.presentation.component.SliderImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.photo.Photo

@Composable
fun CartoonContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(
                context,
                R.drawable.lenna,
                Imgcodecs.IMREAD_COLOR
            ).also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) }
        )
    }
    val dst: Mat by remember { mutableStateOf(Mat()) }
    var sigma_s by remember { mutableStateOf(200f) }
    var sigma_r by remember { mutableStateOf(10f) }
    Photo.stylization(src, dst, sigma_s, sigma_r/100f)
    SliderImageCard(
        valueRange = 1f..100f,
        value = sigma_r,
        onValueChange = { sigma_r = it },
        imageBitmap = dst.toBitmap().asImageBitmap()
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CartoonContentPreview() {
    CartoonContent()
}