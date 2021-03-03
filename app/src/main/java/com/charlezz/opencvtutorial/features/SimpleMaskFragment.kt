package com.charlezz.opencvtutorial.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.CacheUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentSimpleMaskBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import javax.inject.Inject

@AndroidEntryPoint
class SimpleMaskFragment : Fragment() {

    @Inject
    lateinit var cacheUtil: CacheUtil

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private var _binding: FragmentSimpleMaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimpleMaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 레나 이미지 불러오기
        val lennaMat = Utils.loadResource(requireContext(), R.drawable.lenna)
        binding.lenna.setImageBitmap(bitmapUtil.bitmapFrom(lennaMat))

        // 마스크 이미지는 CV8U 또는 CV8U_1C로 불러온다.
        // 0이 아닌 픽셀에 대해서만 연산 수행
        val maskMat = Utils.loadResource(requireContext(), R.drawable.mask_circle, CvType.CV_8U)
        binding.mask.setImageBitmap(bitmapUtil.bitmapFrom(maskMat))

        // 마스킹 결과물을 담을 Mat 만들기
        val dst = Mat(lennaMat.rows(), lennaMat.cols(), CvType.CV_8UC3)
        Core.copyTo(lennaMat, dst, maskMat)
        binding.maskedLenna.setImageBitmap(bitmapUtil.bitmapFrom(dst))

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}