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
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc

@Parcelize
class CheckConvexImage(
    private val _title: String = "Imgproc.isContourConvex(contour)",
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
            //윤곽선 중점 찾기
            val contour = contours[i]
            val moments = Imgproc.moments(contour)
            val centerX = moments.m10 / moments.m00 - 60
            val centerY = moments.m01 / moments.m00 + 10
            val center = Point(centerX, centerY)

            // 검출한 윤곽선 그리기
            Imgproc.drawContours(src, contours, i, RED)

            // 컨벡스 검출 정확도를 높이기 위해 윤곽선 근사화
            val contour2f = MatOfPoint2f(*contours[i].toArray())
            val approxContour = MatOfPoint2f()
            Imgproc.approxPolyDP(
                contour2f,
                approxContour,
                Imgproc.arcLength(contour2f, true)*(0.02),
                true
            )

            // 컨벡스 검사하기
            val isConvex = Imgproc.isContourConvex(MatOfPoint(*approxContour.toArray()))

            // 컨벡스라면 도형위에 Convex라고 마킹, 아니면 Concave로 마킹
            if(isConvex){
                Imgproc.putText(src, "Convex",center,Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, RED)
            }else{
                Imgproc.putText(src, "Concave",center,Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, RED)
            }

        }
        return BitmapUtil().bitmapFrom(src)
    }

}