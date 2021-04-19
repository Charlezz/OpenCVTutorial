package com.charlezz.opencvtutorial

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("bitmap")
fun ImageView.setBitmap(bitmap: Bitmap?) {
    bitmap?.let {
        setImageBitmap(it)
    }
}