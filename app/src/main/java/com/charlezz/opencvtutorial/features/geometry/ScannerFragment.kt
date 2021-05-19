package com.charlezz.opencvtutorial.features.geometry

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentScannerBinding
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt


@AndroidEntryPoint
class ScannerFragment : Fragment() {

    private var _binding: FragmentScannerBinding? = null
    private val binding: FragmentScannerBinding get() = _binding!!

    private val src: Mat by lazy {
        Utils.loadResource(requireContext(), R.drawable.business_card)
    }

    private val size: Size by lazy {
        src.size()
    }

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    var anchors: Array<Point> = arrayOf(
        Point(),
        Point(),
        Point(),
        Point(),
    )

    val dragSrc: Array<Boolean> = Array(4) { false }

    val sf: Double by lazy {
        val displayMetrics =
            requireContext().resources.displayMetrics
        val displayRatio =
            displayMetrics.widthPixels.toDouble() / displayMetrics.heightPixels.toDouble()
        val imageRatio = size.width / size.height

        if (displayRatio < imageRatio) {
            displayMetrics.widthPixels / size.width
        } else {
            displayMetrics.heightPixels / size.height
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val set = ConstraintSet()
        set.clone(binding.constraintLayout)
        set.setDimensionRatio(
            binding.imageView.id,
            "${src.size().width}:${src.size().height}"
        )
        set.applyTo(binding.constraintLayout)

        //앵커 초기 포지션
        anchors[0] = Point(40.0, 40.0)
        anchors[1] = Point(40.0, size.height - 40.0)
        anchors[2] = Point(size.width - 40.0, size.height - 40.0)
        anchors[3] = Point(size.width - 40.0, 40.0)

        for (pt in anchors) {
            Timber.i(pt.toString())
        }

        binding.imageView.setOnTouchListener(object :
            View.OnTouchListener {
            val ptOld = Point()
            override fun onTouch(
                v: View,
                event: MotionEvent
            ): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {

                        val x = event.x.toDouble() / sf
                        val y = event.y.toDouble() / sf

                        Timber.i("ACTION_DOWN: x = $x, y = $y")

                        for (i in 0 until 4) {
                            val distance = sqrt(
                                (x - anchors[i].x).pow(2.0) + (y - anchors[i].y).pow(
                                    2.0
                                )
                            )
                            if (distance < 35.0*max(1, sf.toInt())) {
                                Timber.i("i = $i, distance = $distance")
                                dragSrc[i] = true
                                ptOld.x = x
                                ptOld.y = y
                                break
                            }
                        }
                    }

                    MotionEvent.ACTION_MOVE -> {
                        Timber.i("ACTION_MOVE")
                        for (i in 0 until 4) {
                            if (dragSrc[i]) {

                                val x = event.x.toDouble() / sf
                                val y = event.y.toDouble() / sf
                                Timber.i("x = $x, y = $y")
                                val diffX = x - ptOld.x
                                val diffY = y - ptOld.y

                                anchors[i].x += diffX
                                anchors[i].y += diffY

                                drawROI()

                                ptOld.x = x
                                ptOld.y = y
                                break
                            }
                        }
                    }

                    MotionEvent.ACTION_UP -> {
                        for (i in 0 until 4) {
                            dragSrc[i] = false
                        }
                    }
                }
                return true
            }

        })
        drawROI()
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        super.onCreateOptionsMenu(menu, inflater)
        val menuItem = menu.add(0, 0, 0, "완료")
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.groupId == 0 && item.itemId == 0) {
            val dw = 500.0
            val dh = dw *  600.0/388.0  // 명함 사이즈

            val srcQuad = MatOfPoint2f(
                anchors[0],
                anchors[1],
                anchors[2],
                anchors[3]
            )

            val dstQuad = MatOfPoint2f(
                Point(0.0,0.0),
                Point(0.0, dh-1),
                Point(dw-1, dh-1),
                Point(dw-1, 0.0)
            )

            val perspectiveTransform = Imgproc.getPerspectiveTransform(srcQuad, dstQuad)
            val dst = Mat()
            Imgproc.warpPerspective(src, dst, perspectiveTransform,Size(dw, dh))
            bitmapUtil.bitmapFrom(dst)?.let {
                binding.imageView.setImageBitmap(it)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun drawROI(): Mat {
        val cpy = Mat()
        src.copyTo(cpy)

        val c1 = Scalar(192.0, 192.0, 255.0)
        val c2 = Scalar(128.0, 128.0, 255.0)

        for (pt in anchors) {

            Imgproc.circle(cpy, pt, 35*max(1, sf.toInt()), c1, -1)
        }

        Imgproc.line(cpy, anchors[0], anchors[1], c2, 3*max(1, sf.toInt()))
        Imgproc.line(cpy, anchors[1], anchors[2], c2, 3*max(1, sf.toInt()))
        Imgproc.line(cpy, anchors[2], anchors[3], c2, 3*max(1, sf.toInt()))
        Imgproc.line(cpy, anchors[3], anchors[0], c2, 3*max(1, sf.toInt()))

        Core.addWeighted(src, 0.3, cpy, 0.7, 0.0, cpy)

        bitmapUtil.bitmapFrom(cpy)?.let {
            binding.imageView.setImageBitmap(it)
        }
        return cpy
    }
}