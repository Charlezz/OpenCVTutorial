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
fun AddContent() {
    val context = LocalContext.current
    val lenna = Utils.loadResource(
        context,
        R.drawable.lenna,
        Imgcodecs.IMREAD_GRAYSCALE
    )

    val runa = Utils.loadResource(
        context,
        R.drawable.runa,
        Imgcodecs.IMREAD_GRAYSCALE
    )

    val sum = Mat()
    Core.add(lenna, runa, sum)

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
        Text("Runa")
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            bitmap = runa.toBitmap().asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Text("Lenna + Runa")
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            bitmap = sum.toBitmap().asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}