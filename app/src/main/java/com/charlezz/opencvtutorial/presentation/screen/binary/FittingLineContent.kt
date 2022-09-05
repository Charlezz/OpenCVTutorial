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
import kotlin.math.roundToInt

@Composable
fun FittingLineContent() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState())
    ) {
        val resourceIds = listOf(R.drawable.polygon, R.drawable.x)

        resourceIds.forEach { resId ->
            val src by remember {
                mutableStateOf(
                    Utils.loadResource(context, resId)
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
                val line = Mat()
                Imgproc.fitLine(contour2f, line, Imgproc.DIST_L2, 0.0, 0.01, 0.01)

                val vx = line.get(0, 0)[0]
                val vy = line.get(1, 0)[0]
                val x = line.get(2, 0)[0]
                val y = line.get(3, 0)[0]

                val lefty = (-x * vy / vx + y).roundToInt().toDouble()
                val righty = ((src.cols() - x) * vy / vx + y).roundToInt().toDouble()

                val point1 = Point((src.cols() - 1).toDouble(), righty)
                val point2 = Point(0.0, lefty)
                Imgproc.line(dst, point1, point2, getRandomColor(), 2)
            }
            ImageCard(
                title = "Original",
                imageBitmap = src.toBitmap().asImageBitmap()
            )
            ImageCard(
                title = "Fitting Line",
                imageBitmap = dst.toBitmap().asImageBitmap()
            )
        }


    }
}

