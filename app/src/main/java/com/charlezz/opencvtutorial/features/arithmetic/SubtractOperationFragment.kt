package com.charlezz.opencvtutorial.features.arithmetic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentSubtractOperationBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@AndroidEntryPoint
class SubtractOperationFragment :Fragment(){

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private var _binding: FragmentSubtractOperationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubtractOperationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lenna = Utils.loadResource(
            requireContext(),
            R.drawable.lenna,
            Imgcodecs.IMREAD_GRAYSCALE
        )

        val hole = Utils.loadResource(
            requireContext(),
            R.drawable.hole,
            Imgcodecs.IMREAD_GRAYSCALE
        )

        binding.image1.setImageBitmap(bitmapUtil.bitmapFrom(lenna))
        binding.image2.setImageBitmap(bitmapUtil.bitmapFrom(hole))


        val dst = Mat()
        Imgproc.resize(hole, hole, Size(512.0,512.0))
        Core.subtract(lenna, hole, dst)

        binding.result.setImageBitmap(bitmapUtil.bitmapFrom(dst))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}