package com.charlezz.opencvtutorial.presentation.screen.geometry

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

/**
 * @author soohwan.ok
 */
@Composable
fun WarpAffineContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.runa)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }
    var dst by remember { mutableStateOf(Mat().also { src.copyTo(it) }) }

    var a00 by remember { mutableStateOf("1") }
    var a01 by remember { mutableStateOf("0") }
    var b00 by remember { mutableStateOf("0") }

    var a10 by remember { mutableStateOf("0") }
    var a11 by remember { mutableStateOf("1") }
    var b10 by remember { mutableStateOf("0") }


    Column {
        Row {
            Spacer(modifier = Modifier.size(10.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = a00,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "a00") },
                onValueChange = { a00 = it }
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = a01,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "a01") },
                onValueChange = { a01 = it }
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = b00,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "b00") },
                onValueChange = { b00 = it }
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row {
            Spacer(modifier = Modifier.size(10.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = a10,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "a10") },
                onValueChange = { a10 = it }
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = a11,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "a11") },
                onValueChange = { a11 = it }
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = b10,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "b10") },
                onValueChange = { b10 = it }
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
        OutlinedButton(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            onClick = {
                try {
                    val M = Mat(2, 3, CvType.CV_32F).apply {
                        put(
                            0, 0,
                            floatArrayOf(
                                a00.toFloat(),
                                a01.toFloat(),
                                b00.toFloat(),
                                a10.toFloat(),
                                a11.toFloat(),
                                b10.toFloat(),
                            )
                        )
                    }
                    val newDst = Mat()
                    Imgproc.warpAffine(
                        src,
                        newDst,
                        M,
                        Size(0.0, 0.0),
                    )
                    dst = newDst
                }catch (e: NumberFormatException){
                    Toast.makeText(context, "Check the numbers in the matrix", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Transform")
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
            ,
            bitmap = dst.toBitmap().asImageBitmap(),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun WarpAffineContentPreview() {
    WarpAffineContent()
}