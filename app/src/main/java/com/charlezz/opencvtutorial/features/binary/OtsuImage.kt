package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import kotlinx.parcelize.Parcelize
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

@Parcelize
class OtsuImage(val _title:String, @DrawableRes val drawableResId:Int = R.drawable.lenna, val thresholdVisible:Boolean = true) : Image(_title, drawableResId) {

    override fun process(context: Context, src: Mat): Bitmap? {

        // grayscale로 변환
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)

        // otsu 이진화 적용
        val dst = Mat()
        val threshold = Imgproc.threshold(graySrc, dst, 0.0, 255.0, Imgproc.THRESH_BINARY or Imgproc.THRESH_OTSU)

        // 회색 -> 컬러 변경
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_GRAY2BGR)

        // 계산된 Threshold 출력
        if(thresholdVisible){
            Imgproc.putText(dst, "Threshold = $threshold", Point((dst.cols()/5).toDouble(),100.0), Imgproc.FONT_HERSHEY_COMPLEX, 2.0, Scalar(0.0,0.0,255.0), 3)
        }

        return BitmapUtil().bitmapFrom(dst)

    }
}