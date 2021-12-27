package com.charlezz.opencvtutorial.features.detection.grabcut

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.Menu
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.charlezz.opencvtutorial.databinding.FragmentGrabCutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.opencv.core.Point
import org.opencv.core.Rect

@SuppressLint("ClickableViewAccessibility")
@AndroidEntryPoint
class GrabCutFragment : Fragment() {

    private val viewModel: GrabCutViewModel by viewModels()

    private var _binding: FragmentGrabCutBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGrabCutBinding.inflate(
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
        viewModel.reset()
        observeState()
        setImageViewSize()
        binding.image.setOnTouchListener(onTouchListener)
    }

    private fun setImageViewSize() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.aspectRatio.collectLatest { aspectRatio ->
                binding.image.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    this.dimensionRatio = aspectRatio
                }
            }
        }
    }


    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                binding.progress.isVisible =
                    it is GrabCutViewModel.State.Loading
                binding.image.isEnabled =
                    it !is GrabCutViewModel.State.Loading

                if(it.bitmap!= null){
                    binding.image.setImageBitmap(it.bitmap)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.add(0, 0, 0, "리셋").apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            0 -> {
                viewModel.reset()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun calculateRect(
        initX: Double,
        initY: Double,
        v: View,
        event: MotionEvent,
        bitmap: Bitmap
    ): Rect {
        val scaleFactor =
            v.width.toDouble() / bitmap.width.toDouble()

        val pt1 = Point(
            initX / scaleFactor,
            initY / scaleFactor,
        )

        val pt2 = Point(
            event.x.toDouble() / scaleFactor,
            event.y.toDouble() / scaleFactor
        )

        return Rect(pt1, pt2)
    }


    private val onTouchListener = object :
        View.OnTouchListener {

        var initX = 0f
        var initY = 0f

        override fun onTouch(
            v: View,
            event: MotionEvent
        ): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initX = event.x
                    initY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.e("GrabCutFragment","${viewModel.state.value}")
                    if (viewModel.state.value is GrabCutViewModel.State.GrabCut ||
                        viewModel.state.value is GrabCutViewModel.State.DrawCircle) {
                        val scaleFactor =
                            v.width.toDouble() / viewModel.srcBitmap.value.width.toDouble()
                        viewModel.drawCircle(
                            Point(
                                event.x.toDouble() / scaleFactor,
                                event.y.toDouble() / scaleFactor
                            ),
                        )
                    } else {
                        viewModel.drawSelection(
                            calculateRect(
                                initX = initX.toDouble(),
                                initY = initY.toDouble(),
                                v = v,
                                event = event,
                                bitmap = viewModel.srcBitmap.value
                            )
                        )
                    }

                }
                MotionEvent.ACTION_UP -> {
                    val rect = calculateRect(
                        initX = initX.toDouble(),
                        initY = initY.toDouble(),
                        v = v,
                        event = event,
                        bitmap = viewModel.srcBitmap.value
                    )
                    viewModel.grabCut(rect)
                }
            }

            return true
        }
    }

}
