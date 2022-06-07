package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import com.charlezz.opencvtutorial.*
import kotlinx.parcelize.Parcelize
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

@Parcelize
class ConvexityDefectsImage(
    private val _title: String = "Imgproc.convexHull(points, hull)",
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
        //윤곽선 검출
        Imgproc.findContours(
            binary,
            contours,
            hierarchy,
            Imgproc.RETR_TREE,
            Imgproc.CHAIN_APPROX_SIMPLE
        )

        for (i in 0 until contours.size) {
            // 윤곽선 근사화, (근사화 하지 않으면 의도했던 결과보다 많은 볼록결함이 발견된다)
            val contour2f = MatOfPoint2f(*contours[i].toArray())
            val approxContour = MatOfPoint2f()
            Imgproc.approxPolyDP(
                contour2f,
                approxContour,
                Imgproc.arcLength(contour2f, true)*(0.005),
                true
            )
            val convexHull = MatOfInt()
            // 컨벡스 헐 찾기
            val contour = MatOfPoint(*approxContour.toArray())
            Imgproc.convexHull(contour, convexHull)

            // 컨벡스 헐 정점만 추려내기
            val contourArray: Array<Point> = contour.toArray()
            val hullPoints: Array<Point?> = arrayOfNulls<Point>(convexHull.rows())
            val hullContourIdxList = convexHull.toList()
            for (j in hullContourIdxList.indices) {
                hullPoints[j] = contourArray[hullContourIdxList[j]]
            }

            // 처음에 검출한 윤곽선 정보를 토대로 그리기
            Imgproc.drawContours(src, contours, i, Scalar_RED)
            // 컨벡스 헐 정보를 토대로 그리기
            Imgproc.drawContours(src, listOf(MatOfPoint(*hullPoints)),0, GREEN)

            // 볼록결함 찾기
            val convexDefects = MatOfInt4()
            Imgproc.convexityDefects(contour, convexHull, convexDefects)

            val convexDefeatList = convexDefects.toList()
            val vertexArray = contour.toArray()

            for(j in 0 until convexDefeatList.size step 4){
                val start = vertexArray[convexDefeatList[j]]
                val end = vertexArray[convexDefeatList[j+1]]
                // 컨벡스 헐의 시작점(start)과 끝점(end) 사이의 볼록결함
                val def = vertexArray[convexDefeatList[j+2]]
                Imgproc.circle(src, def, 5, BLUE, 2)
            }
        }
        return BitmapUtil().bitmapFrom(src)
    }

}