package com.charlezz.opencvtutorial

import android.view.View
import com.charlezz.opencvtutorial.databinding.ItemImageBinding
import com.xwray.groupie.viewbinding.BindableItem

class ImageItem(
    private val image:Image
) : BindableItem<ItemImageBinding>() {

    override fun bind(viewBinding: ItemImageBinding, position: Int) {

        image.get()?.let {
            viewBinding.image.setImageBitmap(it)
        }
        viewBinding.executePendingBindings()
    }

    override fun getLayout(): Int {
        return R.layout.item_image
    }

    override fun initializeViewBinding(view: View): ItemImageBinding {
        image.createBitmap(view.context)
        return ItemImageBinding.bind(view)
    }

}