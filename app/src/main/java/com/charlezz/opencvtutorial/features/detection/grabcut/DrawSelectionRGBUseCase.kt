package com.charlezz.opencvtutorial.features.detection.grabcut

import com.charlezz.opencvtutorial.RED
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

class DrawSelectionRGBUseCase @Inject constructor() {
    val dst = Mat()
    operator fun invoke(
        rgbSrc:Mat,
        rect:Rect,
        color:Scalar = RED,
        thickness:Int = 4,
    ):Mat{
        Imgproc.cvtColor(rgbSrc, dst, Imgproc.COLOR_RGB2BGR)

        Imgproc.rectangle(
            dst,
            rect,
            color,
            thickness
        )
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGR2RGB)
        return dst
    }
}