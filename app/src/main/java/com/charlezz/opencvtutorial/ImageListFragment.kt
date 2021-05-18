package com.charlezz.opencvtutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.charlezz.opencvtutorial.databinding.FragmentImageListBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageListFragment : Fragment() {

    @Inject
    lateinit var adapter: GroupieAdapter

    private var _binding: FragmentImageListBinding? = null
    private val binding: FragmentImageListBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.clear()
        for(image in ImageListFragmentArgs.fromBundle(requireArguments()).images){
            adapter.add(ImageItem(image))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}