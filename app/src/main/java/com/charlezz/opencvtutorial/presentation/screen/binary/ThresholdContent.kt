package com.charlezz.opencvtutorial.presentation.screen.binary

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.presentation.component.SliderImageCard
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

@Composable
fun ThresholdContent() {

    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.runa)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }

    val graySrc by remember {
        mutableStateOf(Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }

    var threshold by remember { mutableStateOf(0f) }
    var type by remember { mutableStateOf(Imgproc.THRESH_BINARY) }
    val dst = Mat()

    val t = Imgproc.threshold(graySrc, dst, threshold.toDouble(), 255.0, type)
    if(type == Imgproc.THRESH_OTSU){
        threshold = t.toFloat()
    }
    ThresholdContent(
        imageBitmap = dst.toBitmap().asImageBitmap(),
        threshold = threshold,
        onThreshChange = { threshold = it },
        type = type,
        onTypeChange = { type = it },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThresholdContent(
    imageBitmap: ImageBitmap,
    threshold: Float,
    onThreshChange: (Float) -> Unit,
    type: Int,
    onTypeChange: (Int) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = Imgproc.THRESH_BINARY == type,
                onClick = { onTypeChange(Imgproc.THRESH_BINARY) }
            )
            Text(text = "BINARY")
            RadioButton(
                selected = Imgproc.THRESH_BINARY_INV == type,
                onClick = { onTypeChange(Imgproc.THRESH_BINARY_INV) }
            )
            Text(text = "INV")
            RadioButton(
                selected = Imgproc.THRESH_OTSU == type,
                onClick = { onTypeChange(Imgproc.THRESH_OTSU) }
            )
            Text(text = "OTSU")
        }
        SliderImageCard(
            title = "thresh = $threshold",
            value = threshold,
            onValueChange = onThreshChange,
            valueRange =  0f..255f,
            sliderEnabled = Imgproc.THRESH_OTSU != type,
            imageBitmap = imageBitmap
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ThresholdContentPreview() {
    var threshold by remember { mutableStateOf(0f) }
    var type by remember { mutableStateOf(Imgproc.THRESH_BINARY) }

    ThresholdContent(
        imageBitmap = ImageBitmap.imageResource(R.drawable.runa),
        threshold = threshold,
        onThreshChange = { threshold = it },
        type = type,
        onTypeChange = { type = it }
    )
}