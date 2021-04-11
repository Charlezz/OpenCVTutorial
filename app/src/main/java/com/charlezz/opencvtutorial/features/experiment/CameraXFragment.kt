package com.charlezz.opencvtutorial.features.experiment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.media.Image
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.databinding.FragmentCameraXBinding
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import timber.log.Timber
import java.nio.ByteBuffer
import java.util.concurrent.Executors
import javax.inject.Inject


@AndroidEntryPoint
class CameraXFragment : Fragment() {

    companion object {
        const val PERMISSION_CAMERA = Manifest.permission.CAMERA
    }

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    private var _binding: FragmentCameraXBinding? = null
    private val binding: FragmentCameraXBinding get() = _binding!!

    private val result = MutableLiveData<Bitmap>()
    private val rotation = MutableLiveData<Int>()


    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                startCamera()
            } else {
                Toast.makeText(requireContext(), "카메라 권한 획득 실패", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }

    private val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> by lazy {
        ProcessCameraProvider.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                PERMISSION_CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            permissionLauncher.launch(PERMISSION_CAMERA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraXBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        result.observe(viewLifecycleOwner) { binding.image.setImageBitmap(it) }
        rotation.observe(viewLifecycleOwner) {
            binding.image.rotation = it.toFloat()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private fun startCamera() {
        cameraProviderFuture.addListener({
            // LifecycleOwner에 카메라의 lifecycle을 바인딩하기 위해 사용한다.
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // ImageAnalysis 유즈케이스
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), { imageProxy ->
                val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                if (rotationDegrees != rotation.value) { rotation.postValue(rotationDegrees) }

                imageProxy.image?.let { image -> result.postValue(process(image)) }
                imageProxy.close()
            })

            // 후면 카메라를 기본으로 사용
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // 재바인딩 하기 전 유즈케이스의 바인딩을 해제한다.
                cameraProvider.unbindAll()

                // 카메라에 유즈케이스를 바인딩 한다
                cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, imageAnalysis)

            } catch (exc: Exception) {
                Timber.e(exc, "유즈케이스 바인딩 실패")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }


    private fun process(image: Image): Bitmap {
        val src = imageToMat(image)
        val bgrMat = Mat(image.height, image.width, CvType.CV_8UC4)
        Imgproc.cvtColor(src, bgrMat, Imgproc.COLOR_YUV2GRAY_I420)
        Imgproc.Canny(bgrMat, bgrMat, 100.0, 150.0, 3)
        val rgbaMatOut = Mat()
        Imgproc.cvtColor(bgrMat, rgbaMatOut, Imgproc.COLOR_GRAY2RGBA, 0)
        val bitmap = Bitmap.createBitmap(bgrMat.cols(), bgrMat.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(rgbaMatOut, bitmap)
        return bitmap
    }


    private fun imageToMat(image: Image): Mat {
        var buffer: ByteBuffer
        var rowStride: Int
        var pixelStride: Int
        val width = image.width
        val height = image.height
        var offset = 0
        val planes = image.planes
        val data = ByteArray(image.width * image.height * ImageFormat.getBitsPerPixel(ImageFormat.YUV_420_888) / 8)
        val rowData = ByteArray(planes[0].rowStride)
        for (i in planes.indices) {
            buffer = planes[i].buffer
            rowStride = planes[i].rowStride
            pixelStride = planes[i].pixelStride
            val w = if (i == 0) width else width / 2
            val h = if (i == 0) height else height / 2
            for (row in 0 until h) {
                val bytesPerPixel = ImageFormat.getBitsPerPixel(ImageFormat.YUV_420_888) / 8
                if (pixelStride == bytesPerPixel) {
                    val length = w * bytesPerPixel
                    buffer[data, offset, length]
                    if (h - row != 1) {
                        buffer.position(buffer.position() + rowStride - length)
                    }
                    offset += length
                } else {
                    if (h - row == 1) {
                        buffer[rowData, 0, width - pixelStride + 1]
                    } else {
                        buffer[rowData, 0, rowStride]
                    }
                    for (col in 0 until w) {
                        data[offset++] = rowData[col * pixelStride]
                    }
                }
            }
        }
        val mat = Mat(height + height / 2, width, CvType.CV_8UC1)
        mat.put(0, 0, data)
        return mat
    }
}