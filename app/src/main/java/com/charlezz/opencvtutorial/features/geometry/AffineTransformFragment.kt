package com.charlezz.opencvtutorial.features.geometry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.databinding.FragmentAffineTransformBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@AndroidEntryPoint
class AffineTransformFragment : Fragment() {

    private var _binding: FragmentAffineTransformBinding? = null
    private val binding: FragmentAffineTransformBinding get() = _binding!!

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAffineTransformBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val width = 1000
        val src = Mat(width, width, CvType.CV_8UC3)
        src.setTo(Scalar(128.0,128.0,255.0))//회색조 배경
        val columnCount = 8
        val margin = 150
        val columnSpace: Double = ((width-2*margin) / columnCount).toDouble()

        for (row in 0 until columnCount) {

            for (col in 0 until columnCount) {
                val x: Double = row * columnSpace+margin
                val y: Double = col * columnSpace+margin


                val color = if (row % 2 == 0) {
                    if (col % 2 == 0) {
                        255.0
                    } else {
                        0.0
                    }
                } else {
                    if (col % 2 == 0) {
                        0.0
                    } else {
                        255.0
                    }
                }

                Imgproc.rectangle(
                    src,
                    Point(x, y),
                    Point(x + columnSpace, y + columnSpace),
                    Scalar(color,color,color),
                    -1
                )
            }
        }

        val redDotPt = Point(150.0,150.0)
        val blueDotPt = Point(150.0,850.0)
        val greenDotPt =Point(850.0,150.0)

        val pt1 = MatOfPoint2f(
            redDotPt,
            blueDotPt,
            greenDotPt
        )

        Imgproc.circle(src, redDotPt, 10, Scalar(0.0,0.0,255.0),-1)
        Imgproc.circle(src, blueDotPt, 10, Scalar(255.0,0.0,0.0),-1)
        Imgproc.circle(src, greenDotPt, 10, Scalar(0.0,255.0,0.0),-1)

        val pt2 = MatOfPoint2f(
            Point(150.0,150.0),
            Point(150.0,850.0),
            Point(850.0,500.0)
        )

        binding.originalImage.setImageBitmap(bitmapUtil.bitmapFrom(src))

        val affineTransform = Imgproc.getAffineTransform(pt1,pt2)
        val dst = Mat()
        src.copyTo(dst)
        Imgproc.warpAffine(src, dst, affineTransform, Size(0.0,0.0))
        binding.affineImage.setImageBitmap(bitmapUtil.bitmapFrom(dst))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}