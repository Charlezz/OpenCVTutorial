package com.charlezz.opencvtutorial.features.arithmetic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentAddOperationBinding
import com.charlezz.opencvtutorial.databinding.FragmentBrightnessBinding
import com.charlezz.opencvtutorial.databinding.FragmentSubtractOperationBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@AndroidEntryPoint
class AddOperationFragment :Fragment(){

    @Inject
    lateinit var bitmapUtil:BitmapUtil

    private var _binding: FragmentAddOperationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddOperationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lenna = Utils.loadResource(
            requireContext(),
            R.drawable.lenna,
            Imgcodecs.IMREAD_GRAYSCALE
        )

        val runa = Utils.loadResource(
            requireContext(),
            R.drawable.runa,
            Imgcodecs.IMREAD_GRAYSCALE
        )

        binding.lenna.setImageBitmap(bitmapUtil.bitmapFrom(lenna))
        binding.runa.setImageBitmap(bitmapUtil.bitmapFrom(runa))


        val sum = Mat()
        Core.add(lenna, runa, sum)

        binding.result.setImageBitmap(bitmapUtil.bitmapFrom(sum))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}