package com.charlezz.opencvtutorial

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import org.opencv.android.Utils

@Parcelize
class BitmapImage constructor(private val _title:String,@DrawableRes val id:Int) : Image(_title,id) {

    constructor(bitmap: Bitmap):this("",-1){
        this.bitmap = bitmap
    }
}