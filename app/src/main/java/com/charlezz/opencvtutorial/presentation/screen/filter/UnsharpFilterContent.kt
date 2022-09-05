package com.charlezz.opencvtutorial.presentation.screen.filter

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
 * 언샤프 마스크(Unsharp mask) 필터링
 * 날카롭지 않은(Unsharp)영상.
 * 블러가 적용된 영상을 사용하여 역으로 날카로운 영상을 생성한다.
 *
 * 컬러 영상의 경우 YCrCb 색공간에서 밝기에 해당하는 Y값에만 필터를 적용한다.
 * @author soohwan.ok
 */
@Composable
fun UnsharpFilterContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(
                context,
                R.drawable.lenna,
                Imgcodecs.IMREAD_GRAYSCALE
            )
        )
    }
    var kernelSize by remember { mutableStateOf(0f) }
    val blurredSrc: Mat by remember { mutableStateOf(Mat()) }

    val dst = Mat()
    if (kernelSize.toInt() == 0) {
        Core.copyTo(src, dst, Mat())
    } else {
        Imgproc.GaussianBlur(
            src,
            blurredSrc,
            Size(0.0, 0.0),
            (3f + kernelSize.toInt() * 2).toDouble()
        )
        Core.addWeighted(src, 1.5, blurredSrc, -0.5, 0.0, dst)
    }

    SliderImageCard(
        value = kernelSize,
        onValueChange = { kernelSize = it },
        valueRange = 0f..15f,
        imageBitmap = dst.toBitmap().asImageBitmap()
    )

}