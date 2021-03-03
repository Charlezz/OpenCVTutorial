package com.charlezz.opencvtutorial

import android.graphics.Bitmap
import android.view.View
import com.charlezz.opencvtutorial.databinding.ItemImageBinding
import com.xwray.groupie.viewbinding.BindableItem
import org.opencv.core.Mat

class ImageItem(
    private val bitmap: Bitmap
) : BindableItem<ItemImageBinding>() {
    override fun bind(viewBinding: ItemImageBinding, position: Int) {
        viewBinding.image.setImageBitmap(bitmap)
        viewBinding.executePendingBindings()
    }

    override fun getLayout(): Int {
        return R.layout.item_image
    }

    override fun initializeViewBinding(view: View): ItemImageBinding {
        return ItemImageBinding.bind(view)
    }

}