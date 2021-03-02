package com.charlezz.opencvtutorial

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.navigation.NavDirections

enum class MainMenu(
    val title: String,
    val direction: NavDirections
) {

    IMAGE_INFO(
        "Image Information",
        MainMenuFragmentDirections.actionMainFragmentToImageInfoFragment()
    )
}