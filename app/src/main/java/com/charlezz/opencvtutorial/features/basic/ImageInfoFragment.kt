package com.charlezz.opencvtutorial.features.basic

//import com.charlezz.pickle.Config
//import com.charlezz.pickle.PickleSingle
//import com.charlezz.pickle.data.entity.Media
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.CacheUtil
import com.charlezz.opencvtutorial.databinding.FragmentImageInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageInfoFragment : Fragment() {

    @Inject
    lateinit var cacheUtil: CacheUtil

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private var _binding: FragmentImageInfoBinding? = null
    private val binding get() = _binding!!

//    private val pickle = PickleSingle.register(
//        this,
//        object : PickleSingle.Callback {
//            override fun onResult(media: Media?) {
//                media?.getUri()?.let { uri ->
//                    val file = cacheUtil.copyFrom(uri)
//                    val mat = Imgcodecs.imread(file.absolutePath)
//                    val bitmap = bitmapUtil.bitmapFrom(mat)
//                    binding.image.setImageBitmap(bitmap)
//
//                    val info = StringBuilder()
//                        .appendLine("width = ${mat.width()}")
//                        .appendLine("height = ${mat.height()}")
//                        .appendLine("pixels = ${mat.total()}")
//                        .appendLine("type = ${mat.type()}")
//                        .appendLine("channels = ${mat.channels()}")
//                        .toString()
//
//                    binding.info.text = info
//                }
//            }
//        })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageInfoBinding.inflate(
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
        binding.btnSelect.setOnClickListener {
//            pickle.launch(Config.getDefault())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}