package com.charlezz.opencvtutorial.presentation.screen.geometry

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.toBitmap
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import timber.log.Timber
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @author soohwan.ok
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DocumentScannerContent() {
    var isTransformed by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val src: Mat by remember {
        mutableStateOf(
            Utils.loadResource(
                context,
                R.drawable.business_card
            ).also { Imgproc.cvtColor(it, it, Imgproc.COLOR_BGR2RGB) }
        )
    }
    val size = src.size()

    val dragSrc: Array<Boolean> = Array(4) { false }

    val displayMetrics = context.resources.displayMetrics
    val displayRatio =
        displayMetrics.widthPixels.toDouble() / displayMetrics.heightPixels.toDouble()
    val imageRatio = size.width / size.height

    val scaleFactor = if (displayRatio < imageRatio) {
        displayMetrics.widthPixels.toDouble() / size.width
    } else {
        displayMetrics.heightPixels.toDouble() / size.height
    }
    val anchor0 by remember { mutableStateOf(Point(150.0, 150.0)) }
    val anchor1 by remember { mutableStateOf(Point(150.0, size.height - 150.0)) }
    val anchor2 by remember { mutableStateOf(Point(size.width - 150.0, size.height - 150.0)) }
    val anchor3 by remember { mutableStateOf(Point(size.width - 150.0, 150.0)) }

    val anchors = arrayOf(
        anchor0,
        anchor1,
        anchor2,
        anchor3,
    )

    var dst by remember { mutableStateOf(src) }

    fun drawROI(): Mat {
        val cpy = Mat()
        src.copyTo(cpy)

        val c1 = Scalar(192.0, 192.0, 255.0)
        val c2 = Scalar(128.0, 128.0, 255.0)

        for (pt in anchors) {

            Imgproc.circle(cpy, pt, 35 * max(1, scaleFactor.toInt()), c1, -1)
        }

        Imgproc.line(cpy, anchors[0], anchors[1], c2, 3 * max(1, scaleFactor.toInt()))
        Imgproc.line(cpy, anchors[1], anchors[2], c2, 3 * max(1, scaleFactor.toInt()))
        Imgproc.line(cpy, anchors[2], anchors[3], c2, 3 * max(1, scaleFactor.toInt()))
        Imgproc.line(cpy, anchors[3], anchors[0], c2, 3 * max(1, scaleFactor.toInt()))

        Core.addWeighted(src, 0.3, cpy, 0.7, 0.0, cpy)

        return cpy
    }
    LaunchedEffect(key1 = Unit) {
        dst = drawROI()
    }
    var ptOld by remember { mutableStateOf(Point()) }
    Column {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            val (imageRef, touchAreaRef) = createRefs()
            Image(
                modifier = Modifier
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                bitmap = dst.toBitmap().asImageBitmap(),
                contentDescription = null,
            )
            Image(
                painter = ColorPainter(Color.Transparent),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(touchAreaRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .pointerInteropFilter { event ->
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                val x = event.x.toDouble() / scaleFactor
                                val y = event.y.toDouble() / scaleFactor

                                for (i in 0 until 4) {
                                    val distance = sqrt(
                                        (x - anchors[i].x).pow(2.0) + (y - anchors[i].y).pow(
                                            2.0
                                        )
                                    )
                                    if (distance < 35.0 * max(1, scaleFactor.toInt())) {
                                        Timber.i("i = $i, distance = $distance")
                                        dragSrc[i] = true
                                        ptOld.x = x
                                        ptOld.y = y
                                        break
                                    }
                                }
                            }
                            MotionEvent.ACTION_MOVE -> {
                                for (i in 0 until 4) {
                                    if (dragSrc[i]) {

                                        val x = event.x.toDouble() / scaleFactor
                                        val y = event.y.toDouble() / scaleFactor
//                                Timber.i("x = $x, y = $y")
                                        val diffX = x - ptOld.x
                                        val diffY = y - ptOld.y
                                        Timber.i("diffX = $diffX, diffY = $diffY")
                                        anchors[i].x += diffX
                                        anchors[i].y += diffY

//                                Timber.i("anchors[i].x = ${anchors[i].x}, anchors[i].x = ${anchors[i].y}")
                                        dst = drawROI()

                                        ptOld.x = x
                                        ptOld.y = y
                                        break
                                    }
                                }
                            }
                            MotionEvent.ACTION_UP -> {
                                anchors.indices.forEach { index ->
                                    dragSrc[index] = false
                                }
                            }
                        }
                        true
                    }
            )
        }
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if(isTransformed){
                    isTransformed = false
                    anchors[0] = Point(150.0, 150.0)
                    anchors[1] = Point(150.0, size.height - 150.0)
                    anchors[2] = Point(size.width - 150.0, size.height - 150.0)
                    anchors[3] = Point(size.width - 150.0, 150.0)
                    dst = drawROI()
                }else{
                    isTransformed = true
                    val dw = 500.0
                    val dh = dw * 600.0 / 388.0  // 명함 사이즈

                    val srcQuad = MatOfPoint2f(
                        anchors[0],
                        anchors[1],
                        anchors[2],
                        anchors[3]
                    )

                    val dstQuad = MatOfPoint2f(
                        Point(0.0, 0.0),
                        Point(0.0, dh - 1),
                        Point(dw - 1, dh - 1),
                        Point(dw - 1, 0.0)
                    )

                    val perspectiveTransform = Imgproc.getPerspectiveTransform(srcQuad, dstQuad)
                    val newDst = Mat()
                    Imgproc.warpPerspective(src, newDst, perspectiveTransform, Size(dw, dh))
                    dst = newDst
                }
            }
        ) {
            Text(if(isTransformed) "Reset" else "Transform")
        }
    }

}