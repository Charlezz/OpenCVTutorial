package com.charlezz.opencvtutorial

import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Scalar


val RED = Scalar(0.0,0.0,255.0)
val GREEN = Scalar(0.0,255.0,0.0)
val BLUE = Scalar(255.0,0.0,0.0)
val WHITE = Scalar(255.0,255.0,255.0)
val BLACK = Scalar(0.0,0.0,0.0)

fun Mat.toBitmap():Bitmap{
    return Bitmap.createBitmap(
        cols(), rows(), Bitmap.Config.ARGB_8888
    ).apply{
        Utils.matToBitmap(this@toBitmap,this)
    }
}