package com.charlezz.opencvtutorial.presentation.screen.filter

import android.content.res.Configuration
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.SliderImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.photo.Photo

@Composable
fun SketchContent() {

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

    //회색조 이미지 만들기
    val gray = Mat()
    Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGB2GRAY)

    var sigmaX by remember{ mutableStateOf(3f)}
    // 회색조 이미지를 블러시키기
    val blur = Mat()
    Imgproc.GaussianBlur(gray, blur, Size(0.0, 0.0), sigmaX.toDouble())

    // 엣지만 남고 평탄한 부분은 흰색으로 바뀜
    val dst: Mat by remember { mutableStateOf(Mat()) }
    Core.divide(gray, blur, dst, 255.0)



    SliderImageCard(
        value = sigmaX,
        onValueChange = {sigmaX = it},
        valueRange = 3f..10f,
        imageBitmap = dst.toBitmap().asImageBitmap()
    )
}

