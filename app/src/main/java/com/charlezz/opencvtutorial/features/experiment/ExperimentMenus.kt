package com.charlezz.opencvtutorial.features.experiment

import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuDirections
import com.charlezz.opencvtutorial.MenuFragmentDirections

sealed class ExperimentMenus {
    object CameraX: Menu(
        "CameraX",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToCameraXFragment())
    )
}