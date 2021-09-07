package com.charlezz.opencvtutorial.features.binary

import android.content.Context
import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Image
import com.charlezz.opencvtutorial.R
import kotlinx.parcelize.Parcelize
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.LINE_8
import kotlin.random.Random


@Parcelize
class ContourImage(
    private val _title: String = "외곽선 검출",
    private val _resId: Int = R.drawable.rice
) : Image(_title, _resId) {

    override fun process(context: Context, src: Mat): Bitmap? {
        //회색조 이미지
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)

        val binary = LocalBinarizationUtil.process(graySrc)

        val contours = ArrayList<MatOfPoint>()
        val hierarchy = Mat()

        Imgproc.findContours(
            binary,
            contours,
            hierarchy,
            Imgproc.RETR_TREE,
            Imgproc.CHAIN_APPROX_SIMPLE
        )
        Imgproc.cvtColor(binary, binary, Imgproc.COLOR_GRAY2BGR)
        for (i in 0 until contours.size) {
            val color =
                Scalar(Random.nextDouble(256.0), Random.nextDouble(256.0), Random.nextDouble(256.0))
            Imgproc.drawContours(binary, contours, i, color, 3, LINE_8, hierarchy)
        }
        return BitmapUtil().bitmapFrom(binary)
    }

}