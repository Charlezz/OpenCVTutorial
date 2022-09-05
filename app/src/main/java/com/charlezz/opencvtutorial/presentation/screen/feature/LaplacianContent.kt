package com.charlezz.opencvtutorial.presentation.screen.feature

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
fun LaplacianContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.runa)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }
    val graySrc by remember {
        mutableStateOf( Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }
    val dst = Mat()
    Imgproc.Laplacian(graySrc, dst, -1, 3)

    LazyColumn {
        item {
            ImageCard(
                title = "Original",
                imageBitmap = src.toBitmap().asImageBitmap()
            )
        }
        item {
            ImageCard(
                title = "Laplacian (ksize = 3)",
                imageBitmap = dst.toBitmap().asImageBitmap()
            )
        }

    }

}