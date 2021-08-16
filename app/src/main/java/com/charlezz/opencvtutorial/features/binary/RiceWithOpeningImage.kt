package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.DrawableRes
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import kotlinx.parcelize.Parcelize
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import timber.log.Timber

@Parcelize
class RiceWithOpeningImage constructor(
    private val _title: String,
    @DrawableRes private val drawableResId: Int = R.drawable.rice,
    val op: Int,
    val iteration:Int
) : Image(_title, drawableResId) {
    override fun process(context: Context, src: Mat): Bitmap? {
        var graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)
        graySrc = LocalBinarizationUtil.process(graySrc)

        val kernel = Imgproc.getStructuringElement(
            Imgproc.CV_SHAPE_RECT,
            Size(3.0, 3.0)
        )
        for(i in 0 until iteration){
            Imgproc.morphologyEx(graySrc, graySrc, op, kernel)
        }

        val count = Imgproc.connectedComponentsWithStats(graySrc, Mat(),Mat(),Mat())
        result = "Count = $count"
        return BitmapUtil().bitmapFrom(graySrc)
    }


}