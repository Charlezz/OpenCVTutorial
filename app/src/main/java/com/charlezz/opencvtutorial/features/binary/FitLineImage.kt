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
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc
import kotlin.math.roundToInt


@Parcelize
class FitLineImage(
    private val _title: String = "Imgproc.fitLine(...)",
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
            Imgproc.threshold(graySrc, graySrc, 0.0, 255.0, Imgproc.THRESH_OTSU)
            graySrc
        }


        val contours = ArrayList<MatOfPoint>()
        val hierarchy = Mat()

        Imgproc.findContours(
            binary,
            contours,
            hierarchy,
            Imgproc.RETR_CCOMP,
            Imgproc.CHAIN_APPROX_SIMPLE
        )

        for (i in 0 until contours.size) {
            val contour2f = MatOfPoint2f(*contours[i].toArray())
            val line = Mat()
            Imgproc.fitLine(contour2f, line, Imgproc.DIST_L2, 0.0, 0.01, 0.01)

            val vx = line.get(0, 0)[0]
            val vy = line.get(1, 0)[0]
            val x = line.get(2, 0)[0]
            val y = line.get(3, 0)[0]

            val lefty = (-x * vy / vx + y).roundToInt().toDouble()
            val righty = ((src.cols() - x) * vy / vx + y).roundToInt().toDouble()

            val point1 = Point((src.cols() - 1).toDouble(), righty)
            val point2 = Point(0.0, lefty)
            Imgproc.line(src, point1, point2, Scalar_RED)

        }
        return BitmapUtil().bitmapFrom(src)
    }

}