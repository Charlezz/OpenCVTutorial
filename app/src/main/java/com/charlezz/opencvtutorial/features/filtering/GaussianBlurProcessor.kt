package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.features.common.Processor
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

/**
 * 가우시안 블러의 특징은 가까운 픽셀에 큰 가중치를 두고,
 * 멀리 있는 픽셀은 작은 가중치를 사용하여 평균을 계산한다.
 */
class GaussianBlurProcessor @Inject constructor() : Processor {
    private val kernelSize = 5.0
    override fun process(src: Mat): Mat {
        val dst = Mat()
        //커널사이즈를 0,0으로 지정하면 sigmaX값에 의해 자동 결정됨.
        Imgproc.GaussianBlur(src, dst, Size(0.0, 0.0), kernelSize)
        return dst
    }
}