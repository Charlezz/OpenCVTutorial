package com.charlezz.opencvtutorial.presentation.screen.feature

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

@Composable
fun HoughLineContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.building)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    val graySrc by remember {
        mutableStateOf( Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }

    // 캐니 엣지 검출
    val edge = Mat()
    Imgproc.Canny(graySrc, edge, 50.0, 100.0)

    // 허프 선 변환 함수로 선 검출하기
    val lines = Mat()
    Imgproc.HoughLines(
        edge,
        lines,
        1.0, // 1픽셀 간격
        Math.PI / 180.0, // 1도 간격
        280
    )

    // 엣지 영상을 컬러 영상으로 변환
    Imgproc.cvtColor(edge, edge, Imgproc.COLOR_GRAY2RGB)

    // 검출한 선 그리기
    for (x in 0 until lines.rows()) {
        val rho = lines.get(x, 0)[0]
        val theta = lines.get(x, 0)[1]

        val a = cos(theta)
        val b = sin(theta)

        val x0 = a * rho
        val y0 = b * rho

        val pt1 = Point(round(x0 + 5000*(-b)), round(y0 + 5000*(a)))
        val pt2 = Point(round(x0 - 5000*(-b)), round(y0 - 5000*(a)))

        Imgproc.line(edge, pt1, pt2, Scalar(0.0,0.0,255.0), 3)
    }

    LazyColumn {
        item {
            ImageCard(
                title = "Original",
                imageBitmap = src.toBitmap().asImageBitmap()
            )
        }
        item {
            ImageCard(
                title = "Hough Lines Transform",
                imageBitmap = edge.toBitmap().asImageBitmap()
            )
        }

    }
}