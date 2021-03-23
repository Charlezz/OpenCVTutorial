package com.charlezz.opencvtutorial.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentHsvColorBinding
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@AndroidEntryPoint
class HSVColorFragment:Fragment() {
    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private var _binding:FragmentHsvColorBinding? = null
    private val binding get() = _binding!!

    private val candies by lazy {
        val src = Utils.loadResource(requireContext(), R.drawable.candies)
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2HSV)
        src
    }
    private val dst = Mat()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHsvColorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showImage()
        binding.rangeH.addOnChangeListener(listener)
        binding.rangeS.addOnChangeListener(listener)
        binding.rangeV.addOnChangeListener(listener)
    }

    val listener = RangeSlider.OnChangeListener { slider, value, fromUser ->
        showImage()
    }

    private fun showImage(){
        val h = binding.rangeH.values
        val s = binding.rangeS.values
        val v = binding.rangeV.values

        val lowerb = Scalar(h[0].toDouble(),s[0].toDouble(),v[0].toDouble())
        val upperb = Scalar(h[1].toDouble(),s[1].toDouble(),v[1].toDouble())

        Core.inRange(candies, lowerb, upperb, dst)

        binding.dst.setImageBitmap(bitmapUtil.bitmapFrom(dst))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}