package com.charlezz.opencvtutorial.features.arithmetic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentAbsdiffOperationBinding
import com.charlezz.opencvtutorial.databinding.FragmentBrightnessBinding
import com.charlezz.opencvtutorial.databinding.FragmentSubtractOperationBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import javax.inject.Inject

@AndroidEntryPoint
class AbsDiffOperationFragment :Fragment(){

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private var _binding: FragmentAbsdiffOperationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAbsdiffOperationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cctv1 = Utils.loadResource(
            requireContext(),
            R.drawable.cctv1,
            Imgcodecs.IMREAD_GRAYSCALE
        )

        val cctv2 = Utils.loadResource(
            requireContext(),
            R.drawable.cctv2,
            Imgcodecs.IMREAD_GRAYSCALE
        )

        binding.lenna.setImageBitmap(bitmapUtil.bitmapFrom(cctv1))
        binding.runa.setImageBitmap(bitmapUtil.bitmapFrom(cctv2))


        val dst = Mat()
        Core.absdiff(cctv1, cctv2, dst)
        binding.result.setImageBitmap(bitmapUtil.bitmapFrom(dst))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}