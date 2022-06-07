package com.charlezz.opencvtutorial.presentation.screen.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HSVColorContent() {
    val context = LocalContext.current
    val candies = Utils.loadResource(context, R.drawable.candies)
    Imgproc.cvtColor(candies, candies, Imgproc.COLOR_BGR2HSV)

    var hue by remember { mutableStateOf(50f.rangeTo(80f)) }
    var saturate by remember { mutableStateOf(150f.rangeTo(255.0f)) }
    var value by remember { mutableStateOf(100.0f.rangeTo(255.0f)) }

    val lowerb = Scalar(hue.start.toDouble(), saturate.start.toDouble(), value.start.toDouble())
    val upperb = Scalar(hue.endInclusive.toDouble(), saturate.endInclusive.toDouble(), value.endInclusive.toDouble())

    val mask = Mat()
    Core.inRange(candies, lowerb, upperb, mask)

    val dst = Mat()
    Core.copyTo(candies, dst, mask)

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(
                state = scrollState
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row{
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                text = "H"
            )
            RangeSlider(
                values = hue,
                onValueChange = { hue = it },
                valueRange = 0f.rangeTo(180f)
            )
        }
        Row {
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                text = "S"
            )
            RangeSlider(
                values = saturate,
                onValueChange = { saturate = it },
                valueRange = 0f.rangeTo(255f)
            )

        }
        Row {
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                text = "V"
            )
            RangeSlider(
                values = value,
                onValueChange = { value = it },
                valueRange = 0f.rangeTo(255f)
            )
        }
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = dst.toBitmap(Imgproc.COLOR_HSV2RGB).asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

    }
}