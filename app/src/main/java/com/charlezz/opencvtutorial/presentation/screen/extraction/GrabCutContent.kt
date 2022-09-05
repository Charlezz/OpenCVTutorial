package com.charlezz.opencvtutorial.presentation.screen.extraction

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import com.charlezz.opencvtutorial.*
import com.charlezz.opencvtutorial.R
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GrabCutContent() {

    val context = LocalContext.current
    val src by remember {
        mutableStateOf(
            Utils.loadResource(context, R.drawable.runa)
                .also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) })
    }
    val aspectRatio = src.width().toDouble() / src.height().toDouble()

    val graySrc by remember {
        mutableStateOf(Mat().also {
            Core.copyTo(src, it, Mat())
            Imgproc.cvtColor(it, it, Imgproc.COLOR_RGB2GRAY)
        })
    }

    var mask: Mat by remember { mutableStateOf(Mat()) }
    var isGrabCut by remember { mutableStateOf(false) }

    var dst by remember { mutableStateOf(src) }

    var imageSize by remember { mutableStateOf(IntSize(0, 0)) }
    var scaleFactor = imageSize.width.toDouble() / src.width().toDouble()
    var initX by remember { mutableStateOf(0f) }
    var initY by remember { mutableStateOf(0f) }

    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates -> imageSize = layoutCoordinates.size }
                .pointerInteropFilter { event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            initX = event.x
                            initY = event.y
                            if (!isGrabCut) {
                                mask = Mat.zeros(src.height(), src.width(), CvType.CV_8U)
                            }
                        }
                        MotionEvent.ACTION_MOVE -> {
                            if (!isGrabCut) {
                                val newDst = Mat().apply { src.copyTo(this) }
                                val rect =
                                    calculateRect(
                                        initX.toDouble(),
                                        initY.toDouble(),
                                        scaleFactor,
                                        event
                                    )
                                Imgproc.rectangle(newDst, rect, RED, 4)
                                dst = newDst
                            } else {
                                val newDst = Mat().apply { dst.copyTo(this) }
                                val radius = 5
                                Imgproc.circle(
                                    newDst,
                                    Point(
                                        event.x.toDouble() / scaleFactor,
                                        event.y.toDouble() / scaleFactor
                                    ),
                                    radius,
                                    BLUE,
                                    -1
                                )
                                Imgproc.circle(
                                    mask,
                                    Point(
                                        event.x.toDouble() / scaleFactor,
                                        event.y.toDouble() / scaleFactor
                                    ),
                                    radius,
                                    Scalar(Imgproc.GC_BGD.toDouble()),
                                    -1
                                )
                                dst = newDst
                            }

                        }
                        MotionEvent.ACTION_UP -> {
                            val rect = calculateRect(
                                initX = initX.toDouble(),
                                initY = initY.toDouble(),
                                scaleFactor = scaleFactor,
                                event = event,
                            )
                            Imgproc.rectangle(mask, rect, Scalar(Imgproc.GC_PR_FGD.toDouble()), -1)
                            val newSrc = Mat().apply { src.copyTo(this) }
                            dst = grabCut(newSrc, mask, rect)
                            isGrabCut = true
                        }
                    }
                    true
                },
            bitmap = dst.toBitmap().asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        OutlinedButton(
            modifier = Modifier.align(Alignment.TopCenter),
            onClick = {
                isGrabCut = false
                dst = src
            }
        ) {
            Text(text = "RESET")
        }
    }

}

private fun grabCut(rgbSrc: Mat, mask: Mat, rect: Rect): Mat {
    val bgdModel = Mat()
    val fgdModel = Mat()

    Imgproc.grabCut(
        rgbSrc,
        mask,
        rect,
        bgdModel,
        fgdModel,
        1,
        Imgproc.GC_INIT_WITH_MASK
    )

    val source = Mat(
        1,
        1,
        CvType.CV_8U,
        Scalar(Imgproc.GC_PR_FGD.toDouble())
    )
    val foregroundMask = Mat()
    Core.compare(
        mask,
        source,
        foregroundMask,
        Core.CMP_EQ
    )
    val foreground = Mat(
        rgbSrc.size(),
        CvType.CV_8UC3,
        BLACK
    )

    rgbSrc.copyTo(foreground, foregroundMask)

    return foreground
}

private fun calculateRect(
    initX: Double,
    initY: Double,
    scaleFactor: Double,
    event: MotionEvent,
): Rect {
    val pt1 = Point(
        initX / scaleFactor,
        initY / scaleFactor,
    )

    val pt2 = Point(
        event.x.toDouble() / scaleFactor,
        event.y.toDouble() / scaleFactor
    )

    return Rect(pt1, pt2)
}
