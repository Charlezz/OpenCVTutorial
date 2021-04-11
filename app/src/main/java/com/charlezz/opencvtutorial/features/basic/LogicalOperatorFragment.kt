package com.charlezz.opencvtutorial.features.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.databinding.FragmentLogicalOperatorBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@AndroidEntryPoint
class LogicalOperatorFragment : Fragment() {

    @Inject
    lateinit var bitmapUtil: BitmapUtil
    private var _binding: FragmentLogicalOperatorBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogicalOperatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image1 = Mat.zeros(300, 300, CvType.CV_8UC1).apply {
            Imgproc.rectangle(this, Point(100.0, 100.0), Point(250.0, 250.0), Scalar(255.0), -1)
        }
        val image2 = Mat.zeros(300, 300, CvType.CV_8UC1).apply {
            Imgproc.circle(this, Point(150.0, 150.0), 90, Scalar(255.0), -1)
        }

        binding.image1.setImageBitmap(bitmapUtil.bitmapFrom(image1))
        binding.image2.setImageBitmap(bitmapUtil.bitmapFrom(image2))

        val and = Mat()
        Core.bitwise_and(image1, image2, and)
        binding.and.setImageBitmap(bitmapUtil.bitmapFrom(and))

        val or = Mat()
        Core.bitwise_or(image1, image2, or)
        binding.or.setImageBitmap(bitmapUtil.bitmapFrom(or))

        val xor = Mat()
        Core.bitwise_xor(image1, image2, xor)
        binding.xor.setImageBitmap(bitmapUtil.bitmapFrom(xor))

        val not = Mat()
        Core.bitwise_not(image1, not)
        binding.not.setImageBitmap(bitmapUtil.bitmapFrom(not))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}