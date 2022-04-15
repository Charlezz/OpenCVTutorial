package com.charlezz.opencvtutorial.features.detection

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.charlezz.opencvtutorial.BLUE
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.RED
import kotlinx.parcelize.Parcelize
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import kotlin.random.Random

/**
 * @author soohwan.ok
 */
@Parcelize
class MatchShapeImage constructor(
    private val _title: String,
    private val objectResId: Int,
    private val bgResId: Int
) : Image(title = _title, resId = bgResId) {


    override fun process(context: Context, bgSrc: Mat): Bitmap? {
        val objBitmap = BitmapFactory.decodeResource(context.resources, objectResId)
        val objSrc = Mat() // 검출하고자 하는 객체 이미지
        Utils.bitmapToMat(objBitmap, objSrc)
        Imgproc.cvtColor(objSrc, objSrc, Imgproc.COLOR_RGB2GRAY)
        val binObjSrc = Mat()  // 이진화된 객체 이미지
        Imgproc.threshold(objSrc, binObjSrc, 128.0, 255.0, Imgproc.THRESH_BINARY_INV)
        val objContours = ArrayList<MatOfPoint>() // 객체 이미지의 윤곽선 정보
        val objHierarchy = Mat()
        Imgproc.findContours(
            binObjSrc,
            objContours,
            objHierarchy,
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_NONE
        )
        val objPts = objContours.firstOrNull() ?: return null //윤곽선을 못찾았으면 종료

        val grayBgSrc = Mat() // 검출하고자 하는 객체가 포함된 이미지
        Imgproc.cvtColor(bgSrc, grayBgSrc, Imgproc.COLOR_BGR2GRAY)
        val binBgSrc = Mat()  // 이진화된 이미지
        Imgproc.threshold(grayBgSrc, binBgSrc, 128.0, 255.0, Imgproc.THRESH_BINARY_INV)

        val bgContours = ArrayList<MatOfPoint>() // 이미지의 윤곽선 정보
        val bgHierarchy = Mat()

        Imgproc.findContours(
            binBgSrc,
            bgContours,
            bgHierarchy,
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_NONE
        )

        bgContours.forEachIndexed { index, pts ->
            if (Imgproc.contourArea(pts) > 1000) {
                val rect = Imgproc.boundingRect(pts)  // 검출한 객체의 감싸는 사각형
                Imgproc.rectangle(bgSrc, rect, BLUE, 1) // 파랑색 사각형으로 객체를 감싼다.

                // matchShape는 두 윤곽선의 사이의 거리(차이)를 반환
                val dist = Imgproc.matchShapes(
                    objPts, // 찾고자 하는 객체의 윤곽선
                    pts, // 검출한 객체의 윤곽선
                    Imgproc.CONTOURS_MATCH_I3, // 매칭 방식
                    0.0 // (사용되지 않음)
                )

                // 0.1보다 낮은 차이를 보여줄 때 객체를 찾았다고 판단한다.
                val found = dist < 0.1
                if (found) {
                    // 찾은 객체는 빨간 선으로 두텁께 다시 그린다
                    Imgproc.rectangle(bgSrc, rect, RED, 2)
                }

                // dist값을 출력함
                Imgproc.putText(
                    bgSrc,
                    "${dist}",
                    Point(rect.x.toDouble(), rect.y.toDouble() - 3),
                    Imgproc.FONT_HERSHEY_SIMPLEX,
                    1.0,
                    if (found) RED else BLUE
                )
            }
        }

        return BitmapUtil().bitmapFrom(bgSrc)
    }
}