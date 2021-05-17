package com.charlezz.opencvtutorial.features.geometry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentRotationBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@AndroidEntryPoint
class RotationFragment : Fragment() {

    @Inject
    lateinit var bitmapUtil:BitmapUtil

    private var _binding:FragmentRotationBinding? = null
    private val binding:FragmentRotationBinding get() = _binding!!

    private val src: Mat by lazy {
        Utils.loadResource(
            requireContext(),
            R.drawable.runa
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRotationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        rotate()
        binding.btn.setOnClickListener {
            rotate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun rotate(){
        val angle = binding.angle.text.toString().toDoubleOrNull()
        val centerPoint = Point(
            src.size().width /2.0,
            src.size().height /2.0,
        )
        angle?.let { a->
            val rotationMat = Imgproc.getRotationMatrix2D(centerPoint, angle, 1.0)
            val dst = Mat()
            Imgproc.warpAffine(src, dst, rotationMat, Size(0.0,0.0))
            binding.imageView.setImageBitmap(bitmapUtil.bitmapFrom(dst))
        }


    }
}