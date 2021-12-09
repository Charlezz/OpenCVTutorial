package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.RED
import kotlinx.parcelize.Parcelize
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc


@Parcelize
class MinEnclosingTriangleImage(
    private val _title: String = "Imgproc.minEnclosingTriangle()",
    private val _resId: Int = R.drawable.polygon,
    private val useLocalBinarization: Boolean = true
) : Image(_title, _resId) {
    override fun process(context: Context, src: Mat): Bitmap? {
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
            val triangle = Mat()
            Imgproc.minEnclosingTriangle(contours[i], triangle)

            for(j in 0..2){
                val x1 = triangle.get(j, 0)[0]
                val y1 = triangle.get(j, 0)[1]
                val point1 = Point(x1, y1)

                val x2 = triangle.get((j+1)%3, 0)[0]
                val y2 = triangle.get((j+1)%3, 0)[1]
                val point2 = Point(x2, y2)

                Imgproc.line(src, point1, point2, RED)
            }
        }
        return BitmapUtil().bitmapFrom(src)
    }

}