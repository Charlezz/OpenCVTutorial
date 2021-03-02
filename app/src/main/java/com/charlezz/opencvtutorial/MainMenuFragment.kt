package com.charlezz.opencvtutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.charlezz.opencvtutorial.databinding.FragmentMainBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainMenuFragment : Fragment() {

    @Inject
    lateinit var adapter: GroupieAdapter

    private val viewModel: MainMenuViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.addAll(viewModel.items)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigateEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(it)
        }
    }

    override fun onDestroyView() {
        binding.recyclerView.adapter = null
        super.onDestroyView()
        _binding = null
    }
}