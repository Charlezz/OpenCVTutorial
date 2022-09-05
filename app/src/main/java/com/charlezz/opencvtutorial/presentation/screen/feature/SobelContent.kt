package com.charlezz.opencvtutorial.presentation.screen.feature

import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */
@Composable
fun SobelContent() {
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

    LazyColumn {
        item {
            ImageCard(
                title = "Original",
                imageBitmap = src.toBitmap().asImageBitmap()
            )
        }
        item {
            val sobelImage by remember { mutableStateOf(Mat().also { mat->Imgproc.Sobel(graySrc, mat, -1,1,0,3) }) }
            ImageCard(
                title = "dx=1, dy=0, ksize=3",
                imageBitmap = sobelImage.toBitmap().asImageBitmap()
            )
        }
        item {
            val sobelImage by remember { mutableStateOf(Mat().also { mat->Imgproc.Sobel(graySrc, mat, -1,0,1,3) }) }
            ImageCard(
                title = "dx=0, dy=1, ksize=3",
                imageBitmap = sobelImage.toBitmap().asImageBitmap()
            )
        }
    }

}
