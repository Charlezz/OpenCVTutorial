package com.charlezz.opencvtutorial.presentation.screen.geometry

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import kotlin.system.measureTimeMillis

/**
 * @author soohwan.ok
 */
@Composable
fun ResizeContent() {
    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.runa)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }
    var dst by remember { mutableStateOf(Mat().also { src.copyTo(it) }) }

    val types = listOf(
        "INTER_LINEAR",
        "INTER_NEAREST",
        "INTER_CUBIC",
        "INTER_LANCZOS4"
    )
    var selectedTypeIndex by remember { mutableStateOf(0) }
    var width by remember { mutableStateOf("512") }
    var height by remember { mutableStateOf("512") }
    var expanded by remember { mutableStateOf(false) }
    var measuredTime by remember { mutableStateOf("") }

    ConstraintLayout {
        val (inputRef, dropdownRef, resizeRef, imageRef, timeRef) = createRefs()
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .constrainAs(inputRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = width,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "width") },
                onValueChange = { width = it }
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "*",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = height,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "height") },
                onValueChange = { height = it }
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = { expanded = true }
            ) {
                Text(text = types[selectedTypeIndex])
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )

            }
            Spacer(modifier = Modifier.size(10.dp))
        }

        OutlinedButton(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .constrainAs(resizeRef) {
                    top.linkTo(inputRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onClick = {
                try {
                    measuredTime = measureTimeMillis {
                        val size = Size(width.toDouble(), height.toDouble())
                        val interpolation = when (types[selectedTypeIndex]) {
                            "INTER_LINEAR" -> Imgproc.INTER_LINEAR
                            "INTER_NEAREST" -> Imgproc.INTER_NEAREST
                            "INTER_CUBIC" -> Imgproc.INTER_CUBIC
                            "INTER_LANCZOS4" -> Imgproc.INTER_LANCZOS4
                            else -> Imgproc.INTER_NEAREST
                        }
                        Imgproc.resize(src, dst, size, 0.0, 0.0, interpolation)
                    }.toString()

                } catch (e: NumberFormatException) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }

            }
        ) {
            Text(text = "Resize")
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .constrainAs(imageRef) {
                    top.linkTo(resizeRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            bitmap = dst.toBitmap().asImageBitmap(),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .constrainAs(timeRef) {
                    top.linkTo(imageRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            text = if(measuredTime.isNotEmpty()) "measured ${measuredTime}ms" else "",
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopEnd)
                .constrainAs(dropdownRef) {
                    top.linkTo(inputRef.top)
                    end.linkTo(inputRef.end)
                }
        ) {

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                types.forEachIndexed { index, title ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            selectedTypeIndex = index
                        },
                        text = {
                            Text(text = title)
                        }
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun ResizeContentPreview() {
    ResizeContent()
}