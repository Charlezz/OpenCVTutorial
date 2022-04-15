package com.charlezz.opencvtutorial.features.detection

import com.charlezz.opencvtutorial.*
import com.charlezz.opencvtutorial.features.feature.SobelImage

sealed class DetectionMenus {

    object GrabCut : Menu(
        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToGrabCutFragment(),
            "그랩컷",
        ),
        1
    )

    object MatchShape : Menu(
        direction = Direction.from(
            MenuFragmentDirections.actionMenuFragmentToImageListFragment(
                arrayOf(
                    BitmapImage ("찾고자 하는 객체", R.drawable.spades),
                    MatchShapeImage("객체 찾기", R.drawable.spades, R.drawable.symbols),
                )
            ),title = "matchShape 예제",
        ),
        order = 2
    )
}