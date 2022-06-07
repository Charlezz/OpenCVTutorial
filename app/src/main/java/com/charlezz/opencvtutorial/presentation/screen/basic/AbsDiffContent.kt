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
import org.opencv.imgcodecs.Imgcodecs

/**
 * @author soohwan.ok
 */
@Composable
fun AbsDiffContent() {
    val context = LocalContext.current
    val cctv1 = Utils.loadResource(
        context,
        R.drawable.cctv1,
        Imgcodecs.IMREAD_GRAYSCALE
    )

    val cctv2 = Utils.loadResource(
        context,
        R.drawable.cctv2,
        Imgcodecs.IMREAD_GRAYSCALE
    )

    val dst = Mat()
    Core.absdiff(cctv1, cctv2, dst)

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
        Text("cctv1")
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            bitmap = cctv1.toBitmap().asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Text("cctv2")
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            bitmap = cctv2.toBitmap().asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Text("absdiff(cctv1, cctv2)")
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