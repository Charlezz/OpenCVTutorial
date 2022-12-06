package com.charlezz.opencvtutorial.presentation.screen.extraction

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.RED
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc

@Composable
fun TemplateMatchingContent() {
    val context = LocalContext.current
    val image by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.messi)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    val templateImage by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.messi_face)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }
    val result = Mat()
    Imgproc.matchTemplate(image, templateImage, result, Imgproc.TM_CCOEFF_NORMED)

    val normResult = Mat()
    Core.normalize(result, normResult, 0.0, 255.0, Core.NORM_MINMAX, CvType.CV_8U)

    val minMaxLocResult = Core.minMaxLoc(normResult)

    val pt1 = minMaxLocResult.maxLoc
    val pt2 = Point(pt1.x + templateImage.width(),pt1.y + templateImage.height())
    val finalImage = Mat().also { Core.copyTo(image, it, Mat()) }
    Imgproc.rectangle(finalImage, pt1, pt2, RED, 5)

    LazyColumn {
        item {
            ImageCard(
                title = "Original Image",
                imageBitmap = image.toBitmap().asImageBitmap()
            )
            ImageCard(
                title = "Template Image",
                imageBitmap = templateImage.toBitmap().asImageBitmap()
            )
            ImageCard(
                title = "The result of TM",
                imageBitmap = normResult.toBitmap().asImageBitmap()
            )
            ImageCard(
                title = "Final Result",
                imageBitmap = finalImage.toBitmap().asImageBitmap()
            )
        }
    }
}