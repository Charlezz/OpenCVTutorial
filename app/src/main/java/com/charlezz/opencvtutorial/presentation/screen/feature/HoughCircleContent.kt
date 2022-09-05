package com.charlezz.opencvtutorial.presentation.screen.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import kotlin.math.roundToInt

@Composable
fun HoughCircleContent() {

    var minRadius by remember { mutableStateOf(20f) }
    var maxRadius by remember { mutableStateOf(50f) }

    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.coin)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    val graySrc by remember {
        mutableStateOf(Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }

    // 좀 더 정확한 검출을 위해 잡음 제거를 위한 가우시안 블러처리
    val blurred = Mat()
    Imgproc.GaussianBlur(graySrc, blurred, Size(0.0, 0.0), 1.0)

    // 허프 원 변환을 통한 원 검출
    val circles = Mat()
    Imgproc.HoughCircles(
        blurred,
        circles,
        Imgproc.HOUGH_GRADIENT,
        1.0, // 입력 영상과 누적배열 비율
        50.0, // 검출된 원 중심점들의 최소 거리
        150.0, // Canny 에지 검출기의 높은 임계값
        40.0, // 누적 배열에서 원 검출을 위한 임계값
        minRadius.toInt(), // 원의 최소 반지름
        maxRadius.toInt() // 원의 최대 반지름
    )

    val dst = Mat().also { Core.copyTo(src, it, Mat()) }

    // 검출한 원에 덧그리기
    for (i in 0 until circles.cols()) {
        val circle = circles.get(0, i) // 검출된 원
        val centerX = circle[0] // 원의 중심점 X좌표
        val centerY = circle[1] //원의 중심점 Y좌표
        val radius = circle[2].roundToInt().toInt() // 원의 반지름

        val center = Point(
            centerX.roundToInt().toDouble(),
            centerY.roundToInt().toDouble()
        )

        val centerColor = Scalar(0.0, 0.0, 255.0)
        Imgproc.circle(dst, center, 3, centerColor, 3)

        val circleColor = Scalar(255.0, 0.0, 255.0)
        Imgproc.circle(dst, center, radius, circleColor, 3)
    }

    Column {
        Text(text = "minRadius = ${minRadius.toDouble()}")
        Slider(
            value = minRadius,
            onValueChange = { minRadius=it },
            valueRange = 0f..100f
        )
        Text(text = "maxRadius = ${maxRadius.toDouble()}")
        Slider(
            value = maxRadius,
            onValueChange = { maxRadius=it },
            valueRange = 0f..100f,
        )
        ImageCard(
            title = "Hough Circle Transform",
            imageBitmap = dst.toBitmap().asImageBitmap()
        )
    }
}