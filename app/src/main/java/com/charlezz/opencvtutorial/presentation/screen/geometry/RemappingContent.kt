package com.charlezz.opencvtutorial.presentation.screen.geometry

import android.graphics.Bitmap
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.ImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import kotlin.math.sin

/**
 * @author soohwan.ok
 */
@Composable
private fun RemappingContent(
    src: ImageBitmap,
    transformed: ImageBitmap
) {
    LazyColumn {
        item {
            ImageCard(
                title = "Original",
                imageBitmap = src
            )
        }
        item {
            ImageCard(
                title = "Remapping",
                imageBitmap = transformed
            )
        }
    }
}

@Composable
fun RemappingContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.runa)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    var transformed by remember {
        mutableStateOf(
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888, true)
        )
    }

    LaunchedEffect(key1 = Unit) {
        val mapX = Mat(src.size(), CvType.CV_32F)
        val mapY = Mat(src.size(), CvType.CV_32F)

        for (i in 0 until src.rows()) {
            for (j in 0 until src.cols()) {
                mapX.put(i, j, j.toDouble())
                mapY.put(i, j, i + 10 * sin(j / 10.0))
            }
        }

        val dst = Mat()
        Imgproc.remap(src, dst, mapX, mapY, Imgproc.INTER_CUBIC, Core.BORDER_DEFAULT)

        transformed = dst.toBitmap()
    }

    RemappingContent(
        src = src.toBitmap().asImageBitmap(),
        transformed = transformed.asImageBitmap()
    )

}

@Preview
@Composable
private fun RemappingContentPreview() {
    RemappingContent(
        src = ImageBitmap.imageResource(id = R.drawable.runa),
        transformed = ImageBitmap.imageResource(id = R.drawable.runa)
    )

}