package com.charlezz.opencvtutorial.presentation.screen.binary

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.RED
import com.charlezz.opencvtutorial.presentation.component.SliderImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

@Composable
fun ContourApproximationContent() {
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
    var value by remember {
        mutableStateOf(0f)
    }
    for (i in 0 until contours.size) {
        val contour = contours[i]
        val contour2f = MatOfPoint2f(*contour.toArray())
        val approxCurve = MatOfPoint2f()
        Imgproc.approxPolyDP(
            contour2f,
            approxCurve,
            Imgproc.arcLength(contour2f, true) * (0.005 * value),
            true
        )

        val points = approxCurve.toList()
        val color = Scalar(255.0, 0.0, 255.0)
        for (j in points.indices) {
            Imgproc.line(dst, points[j], points[(j + 1) % points.size], color, 2)
        }
    }



    SliderImageCard(
        value = value,
        onValueChange = { value = it },
        valueRange = 0f..10f,
        imageBitmap = dst.toBitmap().asImageBitmap()
    )
}