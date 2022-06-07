package com.charlezz.opencvtutorial.presentation.screen.basic

import android.view.MotionEvent
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * @author soohwan.ok
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BackProjectContent() {

    val context = LocalContext.current
    val src by remember { mutableStateOf(Utils.loadResource(context, R.drawable.runa)) }
    var dst by remember { mutableStateOf(src) }
    var isDone by remember { mutableStateOf(false) }
    var initPoint by remember { mutableStateOf(Point(0.0, 0.0)) }
    var imageSize by remember { mutableStateOf(Size(0.0, 0.0)) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            enabled = isDone,
            onClick = {
                isDone = false
                dst = src
            }
        ) {
            Text(text = if (isDone) "Reset" else "Drag to select area")
        }
        Image(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
                .aspectRatio(
                    ratio = src
                        .width()
                        .toFloat() / src
                        .height()
                        .toFloat()
                )
                .onSizeChanged {
                    imageSize = Size(it.width.toDouble(), it.height.toDouble())
                }
                .pointerInteropFilter { event ->
                    if (isDone) {
                        return@pointerInteropFilter true
                    }
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            Timber.d("ACTION_DOWN")
                            initPoint = Point(event.x.toDouble(), event.y.toDouble())
                        }
                        MotionEvent.ACTION_MOVE -> {
                            Timber.d("ACTION_MOVE")
                            val newDst = Mat()
                            src.copyTo(newDst)

                            val ratio = src
                                .width()
                                .toDouble() / imageSize.width

                            Imgproc.rectangle(
                                newDst,
                                Point(initPoint.x * ratio, initPoint.y * ratio),
                                Point(event.x.toDouble() * ratio, event.y.toDouble() * ratio),
                                Scalar(0.0, 0.0, 255.0)
                            )
                            dst = newDst
                        }
                        MotionEvent.ACTION_UP -> {
                            Timber.d("ACTION_UP")
                            if (abs(initPoint.x - event.x) < 1.0f ||
                                abs(initPoint.y - event.y) < 1.0f
                            ) {
                                Toast
                                    .makeText(
                                        context,
                                        "Drag to select area again",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                                return@pointerInteropFilter true
                            }

                            val x = min(max(event.x, 0f), imageSize.width.toFloat())
                            val y = min(max(event.y, 0f), imageSize.height.toFloat())

                            val ratio = src.width() / imageSize.width
                            val srcWithRoi = Mat(
                                src,
                                Rect(
                                    Point(initPoint.x * ratio, initPoint.y * ratio),
                                    Point(x.toDouble() * ratio, y.toDouble() * ratio)
                                )
                            )
                            val srcYCrCb = Mat()


                            Imgproc.cvtColor(srcWithRoi, srcYCrCb, Imgproc.COLOR_BGR2YCrCb)

                            val channels = MatOfInt(1, 2)
                            val ranges = MatOfFloat(0f, 256f, 0f, 256f)
                            val hist = Mat()

                            Imgproc.calcHist(
                                listOf(srcWithRoi),
                                channels,
                                Mat(),
                                hist,
                                MatOfInt(128, 128),
                                ranges
                            )

                            val backProject = Mat()
                            Imgproc.calcBackProject(
                                listOf(src),
                                channels,
                                hist,
                                backProject,
                                ranges,
                                1.0
                            )
                            val newDst = Mat()
                            Core.copyTo(src, newDst, backProject)
                            isDone = true
                            dst = newDst
                        }
                    }
                    true
                }
                .align(Alignment.CenterHorizontally)
            ,
            bitmap = dst.toBitmap(Imgproc.COLOR_BGR2RGBA).asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}