package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.features.common.Processor
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

/**
 * 언샤프 마스크(Unsharp mask) 필터링
 * 날카롭지 않은(Unsharp)영상, 즉, 블러가 적용된 영상을 사용하여 역으로 날카로운 영상을 생성한다.
 *
 * 컬러 영상의 경우 YCrCb 색공간에서 밝기에 해당하는 Y값에만 필터를 적용한다.
 */
class SharpeningProcessor @Inject constructor() : Processor {
    private val kernelSize = 5.0
    override fun process(src: Mat): Mat {
        val blurredSrc = Mat()
        Imgproc.GaussianBlur(src, blurredSrc, Size(0.0, 0.0), kernelSize)
        val dst = Mat()
        Core.addWeighted(src, 1.5, blurredSrc, -0.5, 0.0, dst)
        return dst
    }
}