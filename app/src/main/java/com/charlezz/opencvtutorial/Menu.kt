package com.charlezz.opencvtutorial

import android.os.Bundle
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class Menu(
    val title: String,
    val menuDirections: MenuDirections,
    val order:Int = Int.MAX_VALUE
):Parcelable