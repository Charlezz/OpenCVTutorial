package com.charlezz.opencvtutorial.features.common

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentSliderBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

@AndroidEntryPoint
class SliderProcessFragment : Fragment() {

    private var _binding: FragmentSliderBinding? = null
    private val binding: FragmentSliderBinding get()= _binding!!

    private lateinit var processor:SliderProcessor

    private lateinit var src:Mat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            processor = SliderProcessFragmentArgs.fromBundle(it).processor
            val resId = SliderProcessFragmentArgs.fromBundle(it).image
            val mode = SliderProcessFragmentArgs.fromBundle(it).imreadMode
            src = Utils.loadResource(requireContext(), resId, mode)

        }?:throw IllegalArgumentException()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSliderBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.slider1.addOnChangeListener { slider, value, fromUser ->
            process()
        }
        binding.slider2.addOnChangeListener { slider, value, fromUser ->
            process()
        }
        binding.slider3.addOnChangeListener { slider, value, fromUser ->
            process()
        }

        binding.slider1.valueFrom = processor.valueFrom1
        binding.slider2.valueFrom = processor.valueFrom2
        binding.slider3.valueFrom = processor.valueFrom3

        binding.slider1.valueTo = processor.valueTo1
        binding.slider2.valueTo = processor.valueTo2
        binding.slider3.valueTo = processor.valueTo3

        binding.slider1.visibility = if(processor.slider1Visible) View.VISIBLE else View.GONE
        binding.slider2.visibility = if(processor.slider2Visible) View.VISIBLE else View.GONE
        binding.slider3.visibility = if(processor.slider3Visible) View.VISIBLE else View.GONE


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun process() {
        val value1 = binding.slider1.value
        val value2 = binding.slider2.value
        val value3 = binding.slider3.value

        val dst = processor.process(src, value1, value2, value3)
        BitmapUtil().bitmapFrom(dst)?.let {
            binding.image.setImageBitmap(it)
        }

    }
}