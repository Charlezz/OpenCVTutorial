package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import com.charlezz.opencvtutorial.*
import kotlinx.parcelize.Parcelize
import org.opencv.core.Mat
import org.opencv.core.MatOfInt
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc

@Parcelize
class ConvexHullImage(
    private val _title: String = "Imgproc.convexHull(points, hull)",
    private val _resId: Int = R.drawable.polygon,
    private val useLocalBinarization: Boolean = true
) : Image(_title, _resId) {
    override fun process(context: Context, src: Mat): Bitmap? {
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)

        val binarySrc: Mat = if (useLocalBinarization) {
            val result = LocalBinarizationUtil.process(graySrc)
            result
        } else {
            Imgproc.threshold(graySrc, graySrc,0.0, 255.0, Imgproc.THRESH_OTSU)
            graySrc
        }

        val contours = ArrayList<MatOfPoint>()
        val hierarchy = Mat()

        //윤곽선 검출
        Imgproc.findContours(
            binarySrc,
            contours,
            hierarchy,
            Imgproc.RETR_TREE,
            Imgproc.CHAIN_APPROX_SIMPLE
        )

        for (i in 0 until contours.size) {
            val contour = contours[i]
            val hull = MatOfInt()
            // 컨벡스 헐 찾기
            Imgproc.convexHull(contour, hull)

            // 컨벡스 헐 정점만 추려내기
            val contourArray: Array<Point> = contour.toArray()
            val hullPoints: Array<Point?> = arrayOfNulls(hull.rows())
            val hullContourIdxList = hull.toList()
            for (j in hullContourIdxList.indices) {
                hullPoints[j] = contourArray[hullContourIdxList[j]]
            }

            // 처음에 검출한 윤곽선 정보를 토대로 그리기
            Imgproc.drawContours(src, contours, i, RED)
            // 컨벡스 헐 정보를 토대로 그리기
            Imgproc.drawContours(src, listOf(MatOfPoint(*hullPoints)),0, GREEN)

        }
        return BitmapUtil().bitmapFrom(src)
    }

}