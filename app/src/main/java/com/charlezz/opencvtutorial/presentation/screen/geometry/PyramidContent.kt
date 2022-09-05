package com.charlezz.opencvtutorial.presentation.screen.geometry

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun PyramidContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.runa)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    LazyColumn {
        item{
            ImageCard(
                title = "Original",
                imageBitmap = src.toBitmap().asImageBitmap()
            )
        }


        repeat(5) { index->
            item{
                val cpy = Mat()
                src.copyTo(cpy)
                repeat(index+1){
                    Imgproc.pyrDown(cpy, cpy)
                }
                ImageCard(
                    title = "${index+1}time(s) pyrDown",
                    imageBitmap = cpy.toBitmap().asImageBitmap()
                )
            }
        }

        val downscaled = Mat()
        Imgproc.pyrDown(src, downscaled)
        val upscaledFromDownscaled = Mat()
        Imgproc.pyrUp(downscaled, upscaledFromDownscaled)

        item {
            ImageCard(
                title = "pyrDown and pyrUp",
                imageBitmap = upscaledFromDownscaled.toBitmap().asImageBitmap()
            )
        }


        val laplacian = Mat()
        Core.subtract(src, upscaledFromDownscaled,laplacian)

        val restored = Mat()
        Core.add(upscaledFromDownscaled, laplacian, restored)

        item{
            ImageCard(
                title = "pyrDown and pyrUp (restored)",
                imageBitmap = restored.toBitmap().asImageBitmap()
            )
        }

    }
}