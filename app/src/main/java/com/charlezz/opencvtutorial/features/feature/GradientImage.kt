package com.charlezz.opencvtutorial.features.feature

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.core.math.MathUtils
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import kotlinx.parcelize.Parcelize
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfFloat
import org.opencv.imgproc.Imgproc
import kotlin.math.max
import kotlin.math.min

@Parcelize
class GradientImage constructor() : Image(title = "그라디언트와 에지 검출", resId = R.drawable.lenna) {

    override fun process(context: Context, src: Mat): Bitmap? {
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY)

        //x방향으로 소벨필터 적용
        val dx = Mat()
        Imgproc.Sobel(src, dx, CvType.CV_32F, 1, 0, 3)

        //y방향으로 소벨필터 적용
        val dy = Mat()
        Imgproc.Sobel(src, dy, CvType.CV_32F, 0, 1, 3)

        //위의 두개의 행렬을 이용하여 그라디언트 크기 구하기
        var mag = Mat()
        Core.magnitude(dx, dy, mag)

        // Float형으로 계산된 결과기 때문에 비트맵으로 표현 할 수 있는 타입으로 변경
        mag.convertTo(mag, CvType.CV_8UC1)

        // 뚜렷한 엣지만 검출하기 위해 밝은 픽셀만 검출
        Imgproc.threshold(mag, mag, 70.0, 255.0, Imgproc.THRESH_BINARY)

        return BitmapUtil().bitmapFrom(mag)
    }
}