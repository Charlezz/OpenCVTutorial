package com.charlezz.opencvtutorial.features.feature

import android.content.Context
import android.graphics.Bitmap
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import kotlinx.parcelize.Parcelize
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

@Parcelize
class SobelImage constructor(
    val _title:String,
    val ddepth: Int,
    val dx: Int,
    val dy: Int,
    val kSize: Int
) : Image(title = _title, resId = R.drawable.lenna) {
    override fun process(context: Context, src: Mat): Bitmap? {
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY)
        val dst = Mat()
        Imgproc.Sobel(src, dst, ddepth, dx, dy, kSize)
        return BitmapUtil().bitmapFrom(dst)
    }
}