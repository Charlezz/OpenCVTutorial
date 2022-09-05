package com.charlezz.opencvtutorial.presentation.screen.binary

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
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

@Composable
fun LabelingContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.rice)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    val graySrc by remember {
        mutableStateOf(Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }

    val binarized = Mat()
    Imgproc.adaptiveThreshold(
        graySrc,
        binarized,
        255.0,
        Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
        Imgproc.THRESH_BINARY,
        299,
        5.0
    )

    val labels = Mat()
    val stats = Mat()
    val centroids = Mat()
    val objectCount = Imgproc.connectedComponentsWithStats(
        binarized,
        labels,
        stats,
        centroids
    )

    val dst = Mat()
    src.copyTo(dst)

    // 객체 바운딩 박스 그리기
    var riceGrains = 0
    for (index in 1 until stats.rows()) {
        val x = stats.row(index).get(0, 0)[0].toInt()
        val y = stats.row(index).get(0, 1)[0].toInt()
        val width = stats.row(index).get(0, 2)[0].toInt()
        val height = stats.row(index).get(0, 3)[0].toInt()
        val area = stats.row(index).get(0, 4)[0].toInt()

        if(area > 100L){ // filtering small grains
            riceGrains++
            Imgproc.rectangle(
                dst,
                Rect(x, y, width, height),
                Scalar(0.0, 0.0, 255.0),
                3
            )

            // 무게중심 점찍기
            val centerX = centroids.row(index).get(0,0)[0].toInt()
            val centerY = centroids.row(index).get(0,1)[0].toInt()
            Imgproc.circle(dst, Point(centerX.toDouble(), centerY.toDouble()), 5, Scalar(255.0,0.0,0.0),5)
        }
    }

    LazyColumn{
        item {
            ImageCard(
                title = "Original",
                imageBitmap = src.toBitmap().asImageBitmap()
            )
        }
        item {
            ImageCard(
                title = "Found $riceGrains rice grains roughly",
                imageBitmap = dst.toBitmap().asImageBitmap()
            )
        }
    }
}
