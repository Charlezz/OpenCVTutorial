package com.charlezz.opencvtutorial

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class Menu(
    val direction: Direction,
    val order:Int = Int.MAX_VALUE
):Parcelable