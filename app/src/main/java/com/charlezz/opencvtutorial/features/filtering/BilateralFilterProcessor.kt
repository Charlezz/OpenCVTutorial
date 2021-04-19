package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.features.common.Processor
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

/**
 * 양방향 필터
 * 엣지 보존 잡음 제거 필터(edge-preserving noise removal filter)
 * 평균값 필터 및 가우시안 필터는 엣지 부근에서도 픽셀 값을 평탄하게 만드는 단점이 있다.
 * 기준 픽셀과 이웃 픽셀과의 거리, 그리고 픽셀 값의 차이를 함께 고려하여 블러링 정도를 조절한다.
 */
class BilateralFilterProcessor @Inject constructor() : Processor {
    override fun process(src: Mat): Mat {
        val dst = Mat()
        Imgproc.bilateralFilter(
            src,
            dst,
            -1, // 필터링에 사용될 이웃 픽셀의 거리(지름). -1을 입력하면 sigmaSpace에 의해 자동 결정
            10.0, // 색 공간에서 필터의 표준 편차
            5.0 // 좌표 공간에서 필터의 표준 편차
        )
        return dst
    }
}