package com.charlezz.opencvtutorial.features.experiment

import com.charlezz.opencvtutorial.Direction
import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuFragmentDirections

sealed class ExperimentMenus {
    object CameraX: Menu(

        Direction.from(MenuFragmentDirections.actionMenuFragmentToCameraXFragment(),"CameraX",)
    )
}