package com.charlezz.opencvtutorial.features.detection

import com.charlezz.opencvtutorial.Direction
import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuFragmentDirections

sealed class DetectionMenus {

    object GrabCut : Menu(
        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToGrabCutFragment(),
            "그랩컷",
        ),
        1
    )
}