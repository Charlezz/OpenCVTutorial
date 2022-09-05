package com.charlezz.opencvtutorial.presentation.screen.filter

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */
@Composable
fun MedianFilterContent(
    modifier: Modifier = Modifier
) {
    var checked by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(
                context,
                R.drawable.lenna_salt_pepper,
                Imgcodecs.IMREAD_GRAYSCALE
            )
        )
    }
    val dst: Mat by remember { mutableStateOf(Mat()) }
    Imgproc.medianBlur(src, dst, 5)

    Column {
        Switch(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            checked = checked,
            onCheckedChange = { checked = it }
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            bitmap = (if(checked) dst else src).toBitmap().asImageBitmap() ,
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
    }

}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
private fun MedianFilterContentPreview() {
    MedianFilterContent()
}