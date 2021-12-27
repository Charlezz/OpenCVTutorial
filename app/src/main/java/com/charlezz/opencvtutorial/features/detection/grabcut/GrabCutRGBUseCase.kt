package com.charlezz.opencvtutorial.features.detection.grabcut

import com.charlezz.opencvtutorial.BLACK
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

class GrabCutRGBUseCase @Inject constructor(){

    operator fun invoke(rgbSrc:Mat, mask:Mat, rect: Rect) :Mat{
        val bgdModel = Mat()
        val fgdModel = Mat()

        Imgproc.grabCut(
            rgbSrc,
            mask,
            rect,
            bgdModel,
            fgdModel,
            1,
            Imgproc.GC_INIT_WITH_MASK
        )

        val source = Mat(
            1,
            1,
            CvType.CV_8U,
            Scalar(Imgproc.GC_PR_FGD.toDouble())
        )
        val foregroundMask = Mat()
        Core.compare(
            mask,
            source,
            foregroundMask,
            Core.CMP_EQ
        )
        val foreground = Mat(
            rgbSrc.size(),
            CvType.CV_8UC3,
            BLACK
        )

        rgbSrc.copyTo(foreground, foregroundMask)

        return foreground
    }
}