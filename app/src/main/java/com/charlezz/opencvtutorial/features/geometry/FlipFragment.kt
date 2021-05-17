package com.charlezz.opencvtutorial.features.geometry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentFlipBinding
import com.charlezz.opencvtutorial.databinding.FragmentRotationBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@AndroidEntryPoint
class FlipFragment : Fragment() {

    @Inject
    lateinit var bitmapUtil:BitmapUtil

    private var _binding:FragmentFlipBinding? = null
    private val binding:FragmentFlipBinding get() = _binding!!

    lateinit var src: Mat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        src = Utils.loadResource(
            requireContext(),
            R.drawable.runa
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlipBinding.inflate(inflater, container, false)
        binding.imageView.setImageBitmap(bitmapUtil.bitmapFrom(src))
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.flip1.setOnClickListener { flip(1) }
        binding.flip2.setOnClickListener { flip(0) }
        binding.flip3.setOnClickListener { flip(-1) }
    }

    private fun flip(flipCode:Int){
        Core.flip(src, src, flipCode)
        binding.imageView.setImageBitmap(bitmapUtil.bitmapFrom(src))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}