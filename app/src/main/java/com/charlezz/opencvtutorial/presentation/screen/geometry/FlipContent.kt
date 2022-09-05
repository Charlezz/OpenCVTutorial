package com.charlezz.opencvtutorial.presentation.screen.geometry

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */

@Composable
fun FlipContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.runa)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }
    var dst by remember { mutableStateOf(Mat().also { src.copyTo(it) }) }
    FlipContent(
        imageBitmap = dst.toBitmap().asImageBitmap(),
        onFlipClick = { flipCode ->
            val newDst = Mat()
            Core.flip(dst, newDst, flipCode)
            dst = newDst
        }
    )
}

@Composable
private fun FlipContent(
    imageBitmap: ImageBitmap,
    onFlipClick: (Int) -> Unit
) {
    Column {
        Row() {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    onFlipClick(1)
                }
            ) {
                Text(text = "x-axis")
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    onFlipClick(0)
                }
            ) {
                Text(text = "y-axis")
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    onFlipClick(-1)
                }
            ) {
                Text(text = "Both")
            }
        }
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            bitmap = imageBitmap,
            contentDescription = null
        )
    }


}


@Preview
@Composable
private fun FlipContentPreview() {
    FlipContent(
        imageBitmap = ImageBitmap.imageResource(id = R.drawable.runa),
        onFlipClick = {}
    )
}