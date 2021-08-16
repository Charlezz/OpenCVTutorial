package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import kotlinx.parcelize.Parcelize
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

@Parcelize
class LocalBinarizationImage(
    private val _title: String,
    private val drawableResId: Int,
    private val columns: Int,
    private val rows: Int
) : Image(_title, drawableResId) {
    override fun process(context: Context, src: Mat): Bitmap? {
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)
        val dst = LocalBinarizationUtil.process(graySrc)
        return BitmapUtil().bitmapFrom(dst)
    }
}