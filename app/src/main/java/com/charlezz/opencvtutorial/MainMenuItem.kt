package com.charlezz.opencvtutorial

import android.view.View
import androidx.annotation.NavigationRes
import androidx.navigation.NavDirections
import com.charlezz.opencvtutorial.databinding.ItemMainMenuBinding
import com.xwray.groupie.viewbinding.BindableItem

class MainMenuItem(
    val menu: MainMenu,
    val navigator: Navigator
) : BindableItem<ItemMainMenuBinding>() {
    override fun bind(viewBinding: ItemMainMenuBinding, position: Int) {
        viewBinding.item = this
        viewBinding.executePendingBindings()
    }

    override fun getLayout(): Int {
        return R.layout.item_main_menu
    }

    override fun initializeViewBinding(view: View): ItemMainMenuBinding {
        return ItemMainMenuBinding.bind(view)
    }

    interface Navigator {
        fun navigateTo(directions: NavDirections)
    }
}