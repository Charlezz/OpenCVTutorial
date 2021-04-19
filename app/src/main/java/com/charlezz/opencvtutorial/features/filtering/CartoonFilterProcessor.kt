package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.features.common.Processor
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

/**
 * 카툰 필터
 * 입력 영상의 색상을 단순화 시키고, 엣지 부분을 검정색으로 강조한다.
 */
class CartoonFilterProcessor @Inject constructor() : Processor {

    override fun process(src: Mat): Mat {

        val width = src.width()
        val height = src.height()

        // 이미지의 크기를 줄이면 효과적으로 뭉개고, 연산량을 빨리 하는 효과가 있음.
        val resizedSrc = Mat()
        Imgproc.resize(src, resizedSrc, Size(width / 8.0, height / 8.0))

        // 블러 적용
        val blur = Mat()
        Imgproc.bilateralFilter(resizedSrc, blur, -1, 20.0, 7.0)

        // 엣지 검출한 뒤, 이미지를 반전시킨다.
        val edge = Mat()
        Imgproc.Canny(resizedSrc, edge, 100.0, 150.0)
        Core.bitwise_not(edge, edge)
        Imgproc.cvtColor(edge, edge, Imgproc.COLOR_GRAY2BGR)

        //블러시킨 이미지와 반전된 edge를 and연산자로 합치면 edge부분은 검정색으로 나오고, 나머지는 많이 뭉개지고 블러처리된 이미지로 나옴, 카툰효과
        val dst = Mat()
        Core.bitwise_and(blur, edge, dst)
        Imgproc.resize(dst, dst, Size(width.toDouble(), height.toDouble()), 1.0, 1.0, Imgproc.INTER_NEAREST)
        return dst
    }
}