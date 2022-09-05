package com.charlezz.opencvtutorial.presentation.screen.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
fun HoughLinesPContent() {

    var threshold by remember { mutableStateOf(224f) }
    var minLineLength by remember { mutableStateOf(138f) }
    var maxLineGap by remember { mutableStateOf(4.6f) }

    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.building)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    val graySrc by remember {
        mutableStateOf(Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }

    // 캐니 엣지 검출
    val edge = Mat()
    Imgproc.Canny(graySrc, edge, 50.0, 100.0)

    // 허프 선 변환 함수로 선 검출하기
    val lines = Mat()
    Imgproc.HoughLinesP(
        edge,
        lines,
        1.0, // 1픽셀 간격
        Math.PI / 180.0, // 1도 간격
        threshold.toInt(),
        minLineLength.toDouble(),
        maxLineGap.toDouble()
    )

    // 엣지 영상을 컬러 영상으로 변환
    Imgproc.cvtColor(edge, edge, Imgproc.COLOR_GRAY2RGB)


    val dst = Mat()
    Core.copyTo(src, dst, Mat())
    // 검출한 선 그리기
    for (x in 0 until lines.rows()) {
        val l: DoubleArray = lines.get(x, 0)
        Imgproc.line(
            dst,
            Point(l[0], l[1]), // 시작점 좌표
            Point(l[2], l[3]), // 끝점 좌표
            Scalar(0.0, 0.0, 255.0),
            3
        )
    }

    Column {
        Text(text = "threshold = ${threshold.toInt()}")
        Slider(value = threshold, onValueChange = { threshold=it }, valueRange = 50f..250f)
        Text(text = "minLineLength = ${minLineLength.toDouble()}")
        Slider(value = minLineLength, onValueChange = { minLineLength=it }, valueRange = 50f..200f)
        Text(text = "maxLineGap = ${maxLineGap.toDouble()}")
        Slider(
            value = maxLineGap,
            onValueChange = { maxLineGap=it },
            valueRange = 1f..50f,
        )
        ImageCard(
            title = "Hough LinesP Transform",
            imageBitmap = dst.toBitmap().asImageBitmap()
        )
    }
}