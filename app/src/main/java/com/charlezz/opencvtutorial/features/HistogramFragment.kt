package com.charlezz.opencvtutorial.features

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentHistogramBinding
import com.github.mikephil.charting.data.*
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@AndroidEntryPoint
class HistogramFragment : Fragment() {

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private var _binding: FragmentHistogramBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistogramBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchMaterial.setOnCheckedChangeListener { buttonView, isChecked ->
            process(binding.radioGroup.checkedRadioButtonId, isChecked)
        }
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            process(checkedId, binding.switchMaterial.isChecked)
        }
    }

    private fun process(id:Int, isColor:Boolean){
        val src = Utils.loadResource(requireContext(), R.drawable.lenna, if(isColor) Imgcodecs.IMREAD_UNCHANGED else Imgcodecs.IMREAD_GRAYSCALE)
        val type:Type = findType(id)
        type.apply(src, isColor)
        binding.image.setImageBitmap(bitmapUtil.bitmapFrom(src))
        if(isColor){
            drawColorGraph(src)
        }else{
            drawGrayGraph(src)
        }

    }

    private fun drawGrayGraph(src:Mat){
        binding.image.setImageBitmap(bitmapUtil.bitmapFrom(src))

        val hist = Mat()
        Imgproc.calcHist(
            listOf(src),// 입력 영상
            MatOfInt(0),// 채널
            Mat(),// 마스크
            hist,// 계산된 히스토그램
            MatOfInt(256),// histSize: 히스토그램 각 차원의 크기를 나타내는 리스트(빈(bin)의 개수)
            MatOfFloat(0f, 255f) // ranges: 히스토그램 각 차원의 최솟값과 최댓값으로 구성
        )

        val histData = FloatArray(hist.total().toInt())
        hist.get(0, 0, histData)

        val barEntryList = ArrayList<Entry>()
        for (i in 0 until 256) {
            barEntryList.add(Entry(i.toFloat(), histData[i]))
        }
        val dataSet = LineDataSet(barEntryList, "Histogram")
        dataSet.color = Color.GRAY
        dataSet.valueTextColor = Color.GRAY
        binding.chart.data = LineData(dataSet)
        binding.chart.invalidate()
    }

    private fun drawColorGraph(src:Mat) {
        val bgrPlanes = ArrayList<Mat>()

        Core.split(src, bgrPlanes)

        /**
         * 파랑 채널에 대한 히스토그램 생성
         */
        val blueHist = Mat()

        Imgproc.calcHist(
            bgrPlanes,// 입력 영상
            MatOfInt(0),// 채널
            Mat(),// 마스크
            blueHist,// 계산된 히스토그램
            MatOfInt(256),// histSize: 히스토그램 각 차원의 크기를 나타내는 리스트(빈(bin)의 개수)
            MatOfFloat(0f, 255f) // ranges: 히스토그램 각 차원의 최솟값과 최댓값으로 구성
        )

        val blueHistData = FloatArray(blueHist.total().toInt())
        blueHist.get(0, 0, blueHistData)

        val blueEntryList = ArrayList<Entry>()

        for (i in 0 until 256) {
            blueEntryList.add(Entry(i.toFloat(), blueHistData[i]))
        }
        val blueDataSet = LineDataSet(blueEntryList, "Blue")
        blueDataSet.color = Color.BLUE
        blueDataSet.valueTextColor = Color.BLUE

        /**
         * 녹색 채널에 대한 히스토그램 생성
         */
        val greenHist = Mat()

        Imgproc.calcHist(
            bgrPlanes,
            MatOfInt(1),
            Mat(),
            greenHist,
            MatOfInt(256),
            MatOfFloat(0f, 255f)
        )

        val greenHistData = FloatArray(greenHist.total().toInt())
        greenHist.get(0, 0, greenHistData)

        val greenEntryList = ArrayList<Entry>()
        for (i in 0 until 256) {
            greenEntryList.add(Entry(i.toFloat(), greenHistData[i]))
        }

        val greenDataSet = LineDataSet(greenEntryList, "Green")
        greenDataSet.color = Color.GREEN
        greenDataSet.valueTextColor = Color.GREEN

        /**
         * 빨강 채널에 대한 히스토그램 생성
         */

        val redHist = Mat()
        Imgproc.calcHist(
            bgrPlanes,// 입력 영상
            MatOfInt(2),// 채널
            Mat(),// 마스크
            redHist,// 계산된 히스토그램
            MatOfInt(256),// histSize: 히스토그램 각 차원의 크기를 나타내는 리스트(빈(bin)의 개수)
            MatOfFloat(0f, 255f) // ranges: 히스토그램 각 차원의 최솟값과 최댓값으로 구성

        )

        val redHistData = FloatArray(redHist.total().toInt())
        redHist.get(0, 0, redHistData)

        val redEntryList = ArrayList<Entry>()
        for (i in 0 until 256) {
            redEntryList.add(Entry(i.toFloat(), redHistData[i]))
        }
        val redDataSet = LineDataSet(redEntryList, "Red")
        blueDataSet.color = Color.RED
        blueDataSet.valueTextColor = Color.RED

        binding.chart.data = LineData(blueDataSet, greenDataSet, redDataSet)
        binding.chart.invalidate()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    enum class Type (val id:Int){
        ORIGINAL(R.id.original) {
            override fun apply(src:Mat, isColor:Boolean) {

            }
        },
        NORMALIZE(R.id.normalize) {
            override fun apply(src:Mat, isColor:Boolean) {
                if(isColor){
                    Core.normalize(src, src, 0.0, 255.0, Core.NORM_MINMAX)
                }else{
                    Core.normalize(src, src, 0.0, 255.0, Core.NORM_MINMAX)
                }

            }
        },
        EQUALIZE(R.id.equalize) {
            override fun apply(src:Mat, isColor:Boolean) {
                if(isColor){
                    val srcYCrCB = Mat()
                    Imgproc.cvtColor(src,srcYCrCB, Imgproc.COLOR_BGR2YCrCb)

                    // 채널 분리
                    val srcPlanes = ArrayList<Mat>()
                    Core.split(srcYCrCB, srcPlanes)

                    // Y 채널에 대해서만 히스토그램 평활화 수행
                    Imgproc.equalizeHist(srcPlanes[0], srcPlanes[0])

                    // 다시 채널 합치기
                    val dstYCrCb = Mat()
                    Core.merge(srcPlanes, dstYCrCb)
                    Imgproc.cvtColor(dstYCrCb, src, Imgproc.COLOR_YCrCb2BGR)
                }else{
                    Imgproc.equalizeHist(src, src)
                }

            }
        };

        abstract fun apply(src:Mat,isColor:Boolean)
    }

    fun findType(id:Int):Type{
        return Type.values().first { type -> type.id == id }
    }

}