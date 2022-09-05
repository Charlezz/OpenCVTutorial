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
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import kotlin.random.Random

@Composable
fun ContourContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.milkdrop)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    val graySrc by remember {
        mutableStateOf(Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }
    val binarizedSrc = Mat()
    Imgproc.threshold(graySrc, binarizedSrc,0.0, 255.0, Imgproc.THRESH_OTSU)

    val contours = ArrayList<MatOfPoint>()
    val hierarchy = Mat()

    Imgproc.findContours(
        binarizedSrc,
        contours,
        hierarchy,
        Imgproc.RETR_TREE,
        Imgproc.CHAIN_APPROX_SIMPLE
    )
    // 알록달록하게 그리기
    val dst = Mat()
    src.copyTo(dst)
    for (i in 0 until contours.size) {
        val color = Scalar(Random.nextDouble(256.0), Random.nextDouble(256.0), Random.nextDouble(256.0))
        Imgproc.drawContours(dst, contours, i, color, 3, Imgproc.LINE_8, hierarchy, 0)
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
                title = "Drawing contours",
                imageBitmap = dst.toBitmap().asImageBitmap()
            )
        }
    }
}