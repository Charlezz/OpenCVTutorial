package com.charlezz.opencvtutorial.presentation.screen.filter

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
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.presentation.component.SliderImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs.IMREAD_GRAYSCALE
import org.opencv.imgproc.Imgproc

/**
 * 평균 값 필터(Mean Filter)
 * 영상의 특정 좌표 값을 주변 픽셀 값들의 산술 평균으로 설정
 * @author soohwan.ok
 */
@Composable
fun Filter2DContent() {
    val context = LocalContext.current
    val src by remember { mutableStateOf(Utils.loadResource(context, R.drawable.lenna, IMREAD_GRAYSCALE)) }
    var kernelSize by remember { mutableStateOf(0f) }
    val dst: Mat by remember { mutableStateOf(Mat()) }

    if(kernelSize.toInt()==0){
        Core.copyTo(src, dst, Mat())
    }else{
        val kSize = 3+kernelSize.toInt()*2
        val kernel = Mat(kSize, kSize, CvType.CV_32F)
        for (i in 0 until kSize) {
            for (j in 0 until kSize) {
                kernel.put(i, j, 1.0 / (kSize * kSize).toDouble())
            }
        }
        Imgproc.filter2D(src, dst, -1, kernel)
    }

    SliderImageCard(
        value = kernelSize,
        onValueChange = { kernelSize = it },
        valueRange = 0f..15f,
        imageBitmap = dst.toBitmap().asImageBitmap()
    )
}

@Preview
@Composable
private fun Filter2DContentPreview() {
    Filter2DContent()
}