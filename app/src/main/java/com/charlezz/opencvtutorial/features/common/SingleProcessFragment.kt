package com.charlezz.opencvtutorial.features.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.charlezz.opencvtutorial.R
import com.charlezz.opencvtutorial.databinding.FragmentSingleProcessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleProcessFragment:Fragment() {

    private val viewModel: SingleProcessViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.processor = SingleProcessFragmentArgs.fromBundle(it).processor
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return DataBindingUtil.inflate<FragmentSingleProcessBinding>(inflater, R.layout.fragment_single_process, container, false).let { binding->
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
            binding.root
        }
    }
}