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
import org.opencv.core.MatOfPoint2f
import org.opencv.imgproc.Imgproc


@Parcelize
class ArcLengthImage constructor(
    private val _title: String = "Imgproc.arcLength(...)",
    private val _resId: Int = R.drawable.contours,
) : Image(_title, _resId) {

    override fun process(context: Context, src: Mat): Bitmap? {
        //회색조 이미지
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)

        val binaryImg = Mat()
        Imgproc.threshold(graySrc, binaryImg, 0.0, 255.0, Imgproc.THRESH_OTSU)

        val contours = ArrayList<MatOfPoint>()
        val hierarchy = Mat()

        Imgproc.findContours(
            binaryImg,
            contours,
            hierarchy,
            Imgproc.RETR_TREE,
            Imgproc.CHAIN_APPROX_SIMPLE
        )

        var longest = 0.0
        var longestContourIdx = -1;

        for (i in 0 until contours.size) {
            // 윤곽선 길이
            val perimeter = Imgproc.arcLength(MatOfPoint2f(*contours[i].toArray()), true)
            if (perimeter > longest) {
                longest = perimeter
                longestContourIdx = i
            }
        }

        if (longestContourIdx != -1) {
            Imgproc.drawContours(
                src,
                contours,
                longestContourIdx,
                RED,
                5,
                Imgproc.LINE_8,
                hierarchy,
                0
            )
            result = String.format("perimeter = %.2f", longest)
        }

        return BitmapUtil().bitmapFrom(src)
    }

}