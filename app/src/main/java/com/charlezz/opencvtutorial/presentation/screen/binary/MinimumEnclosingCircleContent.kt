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
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.Scalar_RED
import com.charlezz.opencvtutorial.getRandomColor
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

@Composable
fun MinimumEnclosingCircleContent() {
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

        val center = Point()
        val radius = floatArrayOf(0f)
        val contour2f = MatOfPoint2f(*contours[i].toArray())
        Imgproc.minEnclosingCircle(contour2f, center, radius)
        Imgproc.circle(dst, center, radius[0].toInt(), getRandomColor(), 2)
    }

    Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState())
    ) {
        ImageCard(
            title = "Original",
            imageBitmap = src.toBitmap().asImageBitmap()
        )
        ImageCard(
            title = "Minimum Enclosing Circle",
            imageBitmap = dst.toBitmap().asImageBitmap()
        )
    }
}

