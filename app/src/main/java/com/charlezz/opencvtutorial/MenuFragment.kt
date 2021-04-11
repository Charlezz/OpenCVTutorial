package com.charlezz.opencvtutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.charlezz.opencvtutorial.databinding.FragmentMenuBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment(), MenuItem.Navigator {

    @Inject
    lateinit var adapter: GroupieAdapter

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null){
            if(MenuFragmentArgs.fromBundle(requireArguments()).menus!=null){
                adapter.addAll(
                    MenuFragmentArgs.fromBundle(requireArguments()).menus!!
                        .map { menu -> MenuItem(menu, this) }
                )
            }else{
                adapter.addAll(
                    MainMenus::class.nestedClasses
                        .filter { menuKclass-> menuKclass.objectInstance is Menu}
                        .sortedBy { menuKclass-> (menuKclass.objectInstance as Menu).order }
                        .map{ kClass-> MenuItem((kClass.objectInstance as Menu), this) }
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }

    override fun navigateTo(menu: Menu) {
        findNavController().navigate(menu.menuDirections.id, menu.menuDirections.args)
    }
}