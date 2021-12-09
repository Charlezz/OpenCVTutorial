package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.RED
import kotlinx.parcelize.Parcelize
import org.opencv.core.*
import org.opencv.imgproc.Imgproc


@Parcelize
class MinAreaRectImage(
    private val _title: String = "Imgproc.minAreaRect(contour)",
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
            val contour2f = MatOfPoint2f(*contours[i].toArray())
            val rotatedRect:RotatedRect = Imgproc.minAreaRect(contour2f)
            val points: Array<Point> = arrayOf(Point(),Point(),Point(),Point())
            rotatedRect.points(points)
            for (j in points.indices) {
                Imgproc.line(src, points[j], points[(j + 1) % 4], RED)
            }
        }
        return BitmapUtil().bitmapFrom(src)
    }

}