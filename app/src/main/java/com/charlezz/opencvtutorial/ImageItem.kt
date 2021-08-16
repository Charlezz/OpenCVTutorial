package com.charlezz.opencvtutorial

import android.view.View
import com.charlezz.opencvtutorial.databinding.ItemImageBinding
import com.xwray.groupie.viewbinding.BindableItem

class ImageItem(
    private val image:Image
) : BindableItem<ItemImageBinding>() {

    override fun bind(viewBinding: ItemImageBinding, position: Int) {

        image.bitmap?.let {
            viewBinding.image.setImageBitmap(it)
            viewBinding.title.text = image.title
            viewBinding.title.visibility = if(image.title.isEmpty()) View.GONE else View.VISIBLE
            viewBinding.result.text = image.result
            viewBinding.result.visibility = if(image.result.isNullOrEmpty()) View.GONE else View.VISIBLE
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