package com.charlezz.opencvtutorial

import androidx.navigation.NavDirections

open class Menu constructor(
    val title: String,
    val direction: NavDirections,
    val order:Int = -1
)