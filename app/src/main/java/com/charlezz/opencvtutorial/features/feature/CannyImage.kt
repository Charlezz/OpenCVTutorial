package com.charlezz.opencvtutorial.features.feature

import android.content.Context
import android.graphics.Bitmap
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import kotlinx.parcelize.Parcelize
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

@Parcelize
class CannyImage constructor(
    val _title:String,
    val min: Double,
    val max: Double
) :
    Image(title = _title, resId = R.drawable.lenna) {
    override fun process(context: Context, src: Mat): Bitmap? {
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY)
        val edge = Mat()
        Imgproc.Canny(src, edge, min, max)
        return BitmapUtil().bitmapFrom(edge)
    }

}