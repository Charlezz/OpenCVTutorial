package com.charlezz.opencvtutorial

import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.CvException
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import timber.log.Timber
import javax.inject.Inject


class BitmapUtil @Inject constructor() {
    fun bitmapFrom(bgrMat: Mat): Bitmap? {
        var bmp: Bitmap? = null
        val rgbMat = Mat()
        Imgproc.cvtColor(bgrMat, rgbMat, Imgproc.COLOR_BGR2RGB)
        try {
            bmp = Bitmap.createBitmap(rgbMat.cols(), rgbMat.rows(), Bitmap.Config.ARGB_8888)
            Utils.matToBitmap(rgbMat, bmp)
        } catch (e: CvException) {
            Timber.e(e)
        }
        return bmp
    }
}