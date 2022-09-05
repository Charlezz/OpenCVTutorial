package com.charlezz.opencvtutorial.presentation.screen.filter

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.SliderImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */
@Composable
fun GaussianBlurContent(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val src by remember { mutableStateOf(Utils.loadResource(context, R.drawable.lenna, Imgcodecs.IMREAD_GRAYSCALE)) }
    var kernelSize by remember { mutableStateOf(0f) }
    val dst: Mat by remember { mutableStateOf(Mat()) }

    if(kernelSize.toInt() ==0){
        Core.copyTo(src, dst, Mat())
    }else{
        Imgproc.GaussianBlur(src, dst, Size(0.0, 0.0), (3f+kernelSize.toInt()*2).toDouble())
    }

    SliderImageCard(
        value = kernelSize,
        onValueChange = { kernelSize = it },
        valueRange = 0f..15f,
        imageBitmap = dst.toBitmap().asImageBitmap()
    )
}