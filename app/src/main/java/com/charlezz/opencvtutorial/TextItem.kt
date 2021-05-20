package com.charlezz.opencvtutorial

import android.view.View
import androidx.annotation.ColorRes
import com.charlezz.opencvtutorial.databinding.ItemTextBinding
import com.xwray.groupie.viewbinding.BindableItem

class TextItem(
    private val text:String,
    @ColorRes private val colorResId:Int = R.color.WT
) :BindableItem<ItemTextBinding>(){
    override fun bind(viewBinding: ItemTextBinding, position: Int) {
        viewBinding.textView.text = text
        viewBinding.textView.setBackgroundResource(colorResId)
        viewBinding.executePendingBindings()
    }

    override fun getLayout(): Int = R.layout.item_text

    override fun initializeViewBinding(view: View): ItemTextBinding {
        return ItemTextBinding.bind(view)
    }
}