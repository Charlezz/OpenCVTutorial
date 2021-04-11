package com.charlezz.opencvtutorial

import com.charlezz.opencvtutorial.features.basic.BasicMenus
import com.charlezz.opencvtutorial.features.experiment.ExperimentMenus
import com.charlezz.opencvtutorial.features.filtering.FilteringMenus

sealed class MainMenus {

    object Basic : Menu(
        title = "기본적인 영상 처리 기법",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentSelf(
                BasicMenus::class.nestedClasses
                    .sortedBy { menuKclass -> (menuKclass.objectInstance as Menu).order }
                    .map { menuKclass -> menuKclass.objectInstance as Menu }
                    .toTypedArray()
            )
        ),
        0
    )

    object Filtering : Menu(
        "필터링",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentSelf(
                FilteringMenus::class.nestedClasses
                    .sortedBy { menuKclass -> (menuKclass.objectInstance as Menu).order }
                    .map { menuKclass -> menuKclass.objectInstance as Menu }
                    .toTypedArray()
            )
        ),
        1
    )

    object Experiment:Menu(
        "실험실",
        MenuDirections.from(
            MenuFragmentDirections.actionMenuFragmentSelf(
                ExperimentMenus::class.nestedClasses
                    .sortedBy { menuKclass -> (menuKclass.objectInstance as Menu).order }
                    .map { menuKclass -> menuKclass.objectInstance as Menu }
                    .toTypedArray()
            )
        )
    )
}