package com.charlezz.opencvtutorial.presentation.screen.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */
@Composable
fun SubtractContent() {
    val context = LocalContext.current
    val lenna = Utils.loadResource(
        context,
        R.drawable.lenna,
        Imgcodecs.IMREAD_GRAYSCALE
    )

    val hole = Utils.loadResource(
        context,
        R.drawable.hole,
        Imgcodecs.IMREAD_GRAYSCALE
    )

    val dst = Mat()
    Imgproc.resize(hole, hole, Size(512.0,512.0))
    Core.subtract(lenna, hole, dst)

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Lenna")
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            bitmap = lenna.toBitmap().asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Text("Hole")
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            bitmap = hole.toBitmap().asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Text("Lenna - Hole")
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            bitmap = dst.toBitmap().asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}