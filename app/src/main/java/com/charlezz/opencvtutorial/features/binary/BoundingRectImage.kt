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
import org.opencv.core.Rect
import org.opencv.imgproc.Imgproc

@Parcelize
class BoundingRectImage(
    private val _title: String = "Imgproc.boundingRect(contour)",
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
            val rect:Rect = Imgproc.boundingRect(contours[i])
            Imgproc.rectangle(src, rect, Scalar_RED)
        }
        return BitmapUtil().bitmapFrom(src)
    }

}