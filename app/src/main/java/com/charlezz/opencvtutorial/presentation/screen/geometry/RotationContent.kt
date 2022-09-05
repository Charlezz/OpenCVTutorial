package com.charlezz.opencvtutorial.presentation.screen.geometry

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */
@Composable
fun RotationContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.runa)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }
    var dst by remember { mutableStateOf(Mat().also { src.copyTo(it) }) }

    RotationContent(
        imageBitmap = dst.toBitmap().asImageBitmap(),
        onRotateClick = { angle->
            val newDst = Mat()
            val centerPoint = Point(
                src.size().width /2.0,
                src.size().height /2.0,
            )
            val rotationMat = Imgproc.getRotationMatrix2D(centerPoint, angle, 1.0)
            Imgproc.warpAffine(src, newDst, rotationMat, Size(0.0,0.0))
            dst = newDst
        }
    )
}

@Composable
private fun RotationContent(
    imageBitmap:ImageBitmap,
    onRotateClick:(Double)->Unit,
) {
    var angle by remember { mutableStateOf("0") }

    Column {
        Row {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = angle,
                onValueChange = { angle = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
            )
            OutlinedButton(onClick = {
                onRotateClick(angle.toDoubleOrNull()?:0.0)
            }) {
                Text(text = "Rotate")
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
private fun RotationContentPreview() {
    RotationContent(
        imageBitmap = ImageBitmap.imageResource(id = R.drawable.runa),
        onRotateClick = {}
    )
}