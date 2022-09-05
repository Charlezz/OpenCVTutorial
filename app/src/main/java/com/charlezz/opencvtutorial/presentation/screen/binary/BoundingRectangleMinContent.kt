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
fun BoundingRectangleMinContent() {
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

        val contour2f = MatOfPoint2f(*contours[i].toArray())
        val rotatedRect: RotatedRect = Imgproc.minAreaRect(contour2f)
        val points: Array<Point> = arrayOf(Point(),Point(),Point(),Point()).apply {
            rotatedRect.points(this)
        }
        val color = getRandomColor()
        for (j in points.indices) {
            Imgproc.line(dst, points[j], points[(j + 1) % 4], color, 2)
        }
    }

    Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState())
    ) {
        ImageCard(
            title = "Original",
            imageBitmap = src.toBitmap().asImageBitmap()
        )
        ImageCard(
            title = "Bounding Rectangle with minimum area",
            imageBitmap = dst.toBitmap().asImageBitmap()
        )
    }
}

