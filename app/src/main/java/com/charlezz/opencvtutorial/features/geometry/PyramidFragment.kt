package com.charlezz.opencvtutorial.features.geometry

//import com.charlezz.pickle.Config
//import com.charlezz.pickle.PickleSingle
//import com.charlezz.pickle.data.entity.Media
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.charlezz.opencvtutorial.*
import com.charlezz.opencvtutorial.databinding.FragmentPyramidBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import javax.inject.Inject

@AndroidEntryPoint
class PyramidFragment : Fragment() {

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    @Inject
    lateinit var adapter: GroupieAdapter

    @Inject
    lateinit var cacheUtil: CacheUtil

    private var _binding: FragmentPyramidBinding? = null
    private val binding: FragmentPyramidBinding get() = _binding!!

//    private val launcher = PickleSingle.register(this, object : PickleSingle.Callback {
//        override fun onResult(media: Media?) {
//            media?.let {
//
//                val file = cacheUtil.copyFrom(it.getUri())
//                var src = Imgcodecs.imread(file.absolutePath)
//                load(src)
//            }
//        }
//    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPyramidBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val src = Utils.loadResource(requireContext(), R.drawable.runa)
        load(src)

        binding.btn.setOnClickListener {
//            launcher.launch(Config.getDefault())
        }
    }

    private fun load(src: Mat) {
        adapter.clear()

        val cpy = Mat()
        src.copyTo(cpy)
        for (count in 1..5) {
            if(count ==1){
                adapter.add(TextItem("다운스케일링 예제(원본)"))
            }else{
                adapter.add(TextItem("다운스케일링 ${count}회차"))
            }
            bitmapUtil.bitmapFrom(cpy)?.let {
                adapter.add(ImageItem(BitmapImage(it)))
            }
            Imgproc.pyrDown(cpy, cpy)
        }

        val downscaled = Mat()
        Imgproc.pyrDown(src, downscaled)

        val upscaledFromDownscaled = Mat()
        Imgproc.pyrUp(downscaled, upscaledFromDownscaled)

        val laplacian = Mat()
        Core.subtract(src, upscaledFromDownscaled,laplacian)

        val restored = Mat()
        Core.add(upscaledFromDownscaled, laplacian, restored)

        adapter.add(TextItem("원본"))
        adapter.add(ImageItem(BitmapImage(bitmapUtil.bitmapFrom(src)!!)))
        adapter.add(TextItem("다운스케일링"))
        adapter.add(ImageItem(BitmapImage(bitmapUtil.bitmapFrom(downscaled)!!)))
        adapter.add(TextItem("다운스케일링 한 것 다시 업스케일링"))
        adapter.add(ImageItem(BitmapImage(bitmapUtil.bitmapFrom(upscaledFromDownscaled)!!)))
        adapter.add(TextItem("라플라시안(다운스케일링 - 업스케일링)"))
        adapter.add(ImageItem(BitmapImage(bitmapUtil.bitmapFrom(laplacian)!!)))
        adapter.add(TextItem("업스케일링 + 라플라시안"))
        adapter.add(ImageItem(BitmapImage(bitmapUtil.bitmapFrom(restored)!!)))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }

}