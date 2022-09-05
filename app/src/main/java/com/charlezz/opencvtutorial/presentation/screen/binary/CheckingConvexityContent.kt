package com.charlezz.opencvtutorial.presentation.screen.binary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.charlezz.opencvtutorial.*
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

@Composable
fun CheckingConvexityContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.polygon)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    val graySrc by remember {
        mutableStateOf(Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }

    val binaryImg = Mat()
    Imgproc.threshold(graySrc, binaryImg, 0.0, 255.0, Imgproc.THRESH_OTSU)

    val dst = Mat().also {
        src.copyTo(it)
    }

    val contours = ArrayList<MatOfPoint>()
    val hierarchy = Mat()

    Imgproc.findContours(
        binaryImg,
        contours,
        hierarchy,
        Imgproc.RETR_TREE,
        Imgproc.CHAIN_APPROX_SIMPLE
    )

    for (i in 0 until contours.size) {
        val contour = contours[i]
        val contourArea = Imgproc.contourArea(contour)
        if (contourArea < 100) continue

        val color = getRandomColor()
        // 검출한 윤곽선 그리기
        Imgproc.drawContours(dst, contours, i, color, 2)

        // 컨벡스 검출 정확도를 높이기 위해 윤곽선 근사화
        val contour2f = MatOfPoint2f(*contour.toArray())
        val approxContour = MatOfPoint2f()
        Imgproc.approxPolyDP(
            contour2f,
            approxContour,
            Imgproc.arcLength(contour2f, true) * (0.02),
            true
        )

        // 컨벡스 검사하기
        val isConvex = Imgproc.isContourConvex(MatOfPoint(*approxContour.toArray()))

        // 컨벡스라면 도형위에 Convex라고 마킹, 아니면 Concave로 마킹
        putTextOnCenter(dst, contour, if (isConvex) "Convex" else "Concave", textColor = color)

    }

    Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState())
    ) {
        ImageCard(
            title = "Original",
            imageBitmap = src.toBitmap().asImageBitmap()
        )
        ImageCard(
            title = "Checking Convexity",
            imageBitmap = dst.toBitmap().asImageBitmap()
        )
    }
}

