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
import org.opencv.imgproc.Imgproc

@Composable
fun LocalBinarizationContent(
    rows: Int = 4,
    columns: Int = 4
) {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.sudoku)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    val graySrc by remember {
        mutableStateOf(Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }


    LazyColumn {
        item {

            ImageCard(
                title = "Original",
                imageBitmap = src.toBitmap().asImageBitmap()
            )
        }
        item {
            val otsu = Mat()
            Imgproc.threshold(graySrc, otsu, 0.0, 255.0, Imgproc.THRESH_OTSU)
            ImageCard(
                title = "Otsu",
                imageBitmap = otsu.toBitmap().asImageBitmap()
            )
        }
        item {
            val copiedSrc = Mat()
            graySrc.copyTo(copiedSrc)

            val width = copiedSrc.width()
            val height = copiedSrc.height()

            for (row in 0 until rows) {
                for (column in 0 until columns) {
                    val submat = copiedSrc.submat(
                        height / rows * row,
                        height / rows * (row + 1),
                        width / columns * column,
                        width / columns * (column + 1)
                    )
                    Imgproc.threshold(
                        submat,
                        submat,
                        0.0,
                        255.0,
                        Imgproc.THRESH_BINARY or Imgproc.THRESH_OTSU
                    )
                }
            }

            ImageCard(
                title = "Local Binarization",
                imageBitmap = copiedSrc.toBitmap().asImageBitmap()
            )
        }
    }
}