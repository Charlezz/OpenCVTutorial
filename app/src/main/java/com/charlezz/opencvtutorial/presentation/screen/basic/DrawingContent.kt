package com.charlezz.opencvtutorial.presentation.screen.basic

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import com.charlezz.opencvtutorial.Scalar_RED
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * @author soohwan.ok
 */

private enum class DrawingMode {
    LINE,
    RECTANGLE,
    CIRCLE
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DrawingContent() {

    var selectedMode by remember { mutableStateOf(DrawingMode.LINE) }

    var initPoint by remember { mutableStateOf(Point(0.0, 0.0)) }
    var imageSize by remember { mutableStateOf(Size(1.0, 1.0)) }
    var mat: Mat by remember { mutableStateOf(Mat.zeros(imageSize, CvType.CV_8UC3)) }

    Column {
        Row {
            Row {
                RadioButton(
                    selected = selectedMode == DrawingMode.LINE,
                    onClick = { selectedMode = DrawingMode.LINE }
                )
                Text(
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                    text = "Line"
                )
            }
            Row(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                RadioButton(
                    selected = selectedMode == DrawingMode.RECTANGLE,
                    onClick = { selectedMode = DrawingMode.RECTANGLE }
                )
                Text(
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                    text = "Rectangle"
                )
            }
            Row(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                RadioButton(
                    selected = selectedMode == DrawingMode.CIRCLE,
                    onClick = { selectedMode = DrawingMode.CIRCLE }
                )
                Text(
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                    text = "Circle"
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged {
                    imageSize = Size(it.width.toDouble(), it.height.toDouble())
                }
                .pointerInteropFilter { event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            initPoint = Point(event.x.toDouble(), event.y.toDouble())
                        }
                        MotionEvent.ACTION_MOVE -> {
                            val newMat = Mat.zeros(imageSize, CvType.CV_8UC3)
                            when (selectedMode) {
                                DrawingMode.LINE -> {
                                    Imgproc.line(
                                        newMat,
                                        initPoint,
                                        Point(event.x.toDouble(), event.y.toDouble()),
                                        Scalar(255.0, 0.0, 0.0)
                                    )
                                }
                                DrawingMode.RECTANGLE -> {
                                    Imgproc.rectangle(
                                        newMat,
                                        initPoint,
                                        Point(event.x.toDouble(), event.y.toDouble()),
                                        Scalar(255.0, 0.0, 0.0)
                                    )
                                }
                                DrawingMode.CIRCLE -> {
                                    val absX = abs(initPoint.x - event.x)
                                    val absY = abs(initPoint.y - event.y)
                                    Imgproc.circle(
                                        newMat,
                                        initPoint,
                                        sqrt(absX * absX + absY * absY).toInt(),
                                        Scalar(255.0, 0.0, 0.0)
                                    )
                                }
                            }
                            mat = newMat
                        }
                        MotionEvent.ACTION_UP -> {

                        }
                        else -> false
                    }
                    true
                }
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = mat.toBitmap().asImageBitmap(),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }
    }
}
