package com.charlezz.opencvtutorial

import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import kotlin.random.Random


fun getRandomColor(): Scalar {
    return Scalar(
        Random.nextDouble(0.0, 256.0),
        Random.nextDouble(0.0, 256.0),
        Random.nextDouble(0.0, 256.0)
    )
}

fun Mat.toBitmap(code: Int? = null): Bitmap {
    if (code != null) {
        Imgproc.cvtColor(this, this, code)
    }
    return Bitmap.createBitmap(
        cols(), rows(), Bitmap.Config.ARGB_8888
    ).apply {
        Utils.matToBitmap(this@toBitmap, this)
    }
}

fun putTextOnCenter(
    img: Mat,
    contour: MatOfPoint,
    text: String,
    fontFace: Int = Imgproc.FONT_HERSHEY_SIMPLEX,
    fontScale: Double = 0.8,
    thickness: Int = 2,
    textColor: Scalar = getRandomColor()
) {
    val textSize = Imgproc.getTextSize(text, fontFace, fontScale, thickness, null)
    val moments = Imgproc.moments(contour)
    val centerX = (moments.m10 / moments.m00 - textSize.width / 2)
    val centerY = (moments.m01 / moments.m00 + textSize.height / 2)
    val center = Point(centerX, centerY)
    Imgproc.putText(img, text, center, fontFace, fontScale, textColor, thickness, Imgproc.LINE_AA)

}

fun putTextOnTop(
    img: Mat,
    contour: MatOfPoint,
    text: String,
    fontFace: Int = Imgproc.FONT_HERSHEY_SIMPLEX,
    fontScale: Double = 0.8,
    thickness: Int = 2,
    textColor: Scalar = getRandomColor()
) {
    val boundingRect = Imgproc.boundingRect(contour)
    val textSize = Imgproc.getTextSize(text, fontFace, fontScale, thickness, null)
    val top = Point(
        (boundingRect.x + boundingRect.width / 2).toDouble() - textSize.width / 2,
        boundingRect.y - textSize.height / 2
    )

    Imgproc.putText(img, text, top, fontFace, fontScale, textColor, thickness, Imgproc.LINE_AA)

}

val RED = Scalar(255.0, 0.0, 0.0)
val Scalar_RED = Scalar(0.0, 0.0, 255.0)
val GREEN = Scalar(0.0, 255.0, 0.0)
val BLUE = Scalar(0.0, 0.0, 255.0)
val WHITE = Scalar(255.0, 255.0, 255.0)
val BLACK = Scalar(0.0, 0.0, 0.0)