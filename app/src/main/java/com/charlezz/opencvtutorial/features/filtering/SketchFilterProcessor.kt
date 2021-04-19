package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.features.common.Processor
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

/**
 * 스케치 필터
 * 평탄한 영역은 흰색
 * 엣지 근방에서 어두운 영역을 검정색으로 설정 (밝은 영역은 흰색)
 */
class SketchFilterProcessor @Inject constructor() : Processor {

    override fun process(src: Mat): Mat {

        //회색조 이미지 만들기
        val gray = Mat()
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY)

        // 회색조 이미지를 블러시키기
        val blur = Mat()
        Imgproc.GaussianBlur(gray, blur, Size(0.0, 0.0), 3.0)

        // 엣지만 남고 평탄한 부분은 흰색으로 바뀜
        val dst = Mat()
        Core.divide(gray, blur, dst, 255.0)
        return dst
    }
}