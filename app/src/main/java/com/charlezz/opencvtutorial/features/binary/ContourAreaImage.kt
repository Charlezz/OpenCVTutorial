package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.Scalar_RED
import kotlinx.parcelize.Parcelize
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc

@Parcelize
class ContourAreaImage(
    private val _title: String = "Imgproc.contourArea(contour)",
    private val _resId: Int = R.drawable.polygon,
    private val useLocalBinarization: Boolean = true
) : Image(_title, _resId) {

    override fun process(context: Context, src: Mat): Bitmap? {
        //회색조 이미지
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)

        val binary: Mat = if (useLocalBinarization) {
            val result = LocalBinarizationUtil.process(graySrc)
            result
        } else {
            Imgproc.threshold(graySrc, graySrc,0.0, 255.0, Imgproc.THRESH_OTSU)
            graySrc
        }


        val contours = ArrayList<MatOfPoint>()
        val hierarchy = Mat()

        Imgproc.findContours(
            binary,
            contours,
            hierarchy,
            Imgproc.RETR_TREE,
            Imgproc.CHAIN_APPROX_SIMPLE
        )

        for (i in 0 until contours.size) {
            val contour = contours[i]
            val contourArea = Imgproc.contourArea(contour)
            val moments = Imgproc.moments(contour)
            val centerX = moments.m10 / moments.m00 - 20
            val centerY = moments.m01 / moments.m00 - 5
            val center = Point(centerX, centerY)

            Imgproc.drawContours(src, contours, i, Scalar_RED)
            Imgproc.putText(src, "$contourArea",center,Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, Scalar_RED)
        }
        return BitmapUtil().bitmapFrom(src)
    }

}