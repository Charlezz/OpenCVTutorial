package com.charlezz.opencvtutorial

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import androidx.annotation.DrawableRes
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc


open abstract class Image constructor(
    val title: String = "",
    @DrawableRes val resId: Int
) : Parcelable {

    protected var bitmap: Bitmap? = null

    fun createBitmap(context: Context) {
        if (resId != -1) {
            bitmap = BitmapFactory.decodeResource(context.resources, resId)
            val src = Mat()
            Utils.bitmapToMat(bitmap, src)
            Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2BGR)
            bitmap = process(context, src)
        }
    }

    open fun process(context: Context, src: Mat): Bitmap? {
        return bitmap
    }

    fun get(): Bitmap? {
        return bitmap
    }

}