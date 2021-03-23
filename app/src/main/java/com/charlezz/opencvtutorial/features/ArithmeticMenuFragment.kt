package com.charlezz.opencvtutorial.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.charlezz.opencvtutorial.BitmapUtil
import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuItem
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentArtithmeticBinding
import com.charlezz.opencvtutorial.databinding.FragmentBrightnessBinding
import com.charlezz.opencvtutorial.features.arithmetic.ArithmeticMenus
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class ArithmeticMenuFragment : Fragment(), MenuItem.Navigator {

    private var _binding: FragmentArtithmeticBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var bitmapUtil: BitmapUtil

    @Inject
    lateinit var adapter:GroupieAdapter

    @Inject
    lateinit var linearLayoutManagerProvider: Provider<LinearLayoutManager>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtithmeticBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = linearLayoutManagerProvider.get()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        adapter.clear()
        adapter.addAll(
            ArithmeticMenus::class.nestedClasses
                .map { kClass-> kClass.objectInstance}
                .filterIsInstance(Menu::class.java)
                .map { MenuItem(it, this) }
                .toList()
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun navigateTo(directions: NavDirections) {
        findNavController().navigate(directions)
    }
}