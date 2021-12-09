package com.charlezz.opencvtutorial

import com.charlezz.opencvtutorial.features.basic.BasicMenus
import com.charlezz.opencvtutorial.features.binary.BinarizationMenus
import com.charlezz.opencvtutorial.features.feature.FeatureMenus
import com.charlezz.opencvtutorial.features.filtering.FilteringMenus
import com.charlezz.opencvtutorial.features.geometry.GeometryMenus

sealed class MainMenus {

    object Basic : Menu(
        Direction.from(
            MenuFragmentDirections.actionMenuFragmentSelf(
                BasicMenus::class.nestedClasses
                    .sortedBy { menuKclass -> (menuKclass.objectInstance as Menu).order }
                    .map { menuKclass -> menuKclass.objectInstance as Menu }
                    .toTypedArray()
            ),
            "기본적인 영상 처리 기법",
        ),
        0
    )

    object Filtering : Menu(
        Direction.from(
            MenuFragmentDirections.actionMenuFragmentSelf(

                FilteringMenus::class.nestedClasses
                    .sortedBy { menuKclass -> (menuKclass.objectInstance as Menu).order }
                    .map { menuKclass -> menuKclass.objectInstance as Menu }
                    .toTypedArray()
            ),
            "필터링",
        ),
        1
    )

    object GeometryTransformation : Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentSelf(

                GeometryMenus::class.nestedClasses
                    .sortedBy { menuKclass -> (menuKclass.objectInstance as Menu).order }
                    .map { menuKclass -> menuKclass.objectInstance as Menu }
                    .toTypedArray()
            ),
            "기하학적 변환",
        ),
        2
    )

    object Feature : Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentSelf(

                FeatureMenus::class.nestedClasses
                    .sortedBy { menuKclass -> (menuKclass.objectInstance as Menu).order }
                    .map { menuKclass -> menuKclass.objectInstance as Menu }
                    .toTypedArray()
            ),
            "영상의 특징 추출",
        ),
        3
    )

    object Binary : Menu(

        Direction.from(
            MenuFragmentDirections.actionMenuFragmentSelf(

                BinarizationMenus::class.nestedClasses
                    .sortedBy { menuKclass -> (menuKclass.objectInstance as Menu).order }
                    .map { menuKclass -> menuKclass.objectInstance as Menu }
                    .toTypedArray()
            ),
            "영상의 이진화",
        ),
        4
    )

//    object Experiment:Menu(
//        "실험실",
//        MenuDirections.from(
//            MenuFragmentDirections.actionMenuFragmentSelf(
//                ExperimentMenus::class.nestedClasses
//                    .sortedBy { menuKclass -> (menuKclass.objectInstance as Menu).order }
//                    .map { menuKclass -> menuKclass.objectInstance as Menu }
//                    .toTypedArray()
//            )
//        )
//    )
}