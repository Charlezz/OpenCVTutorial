package com.charlezz.opencvtutorial.features.basic.arithmetic

import com.charlezz.opencvtutorial.Direction
import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuFragmentDirections


sealed class ArithmeticMenus {
    object Add : Menu(
        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToAddOperationFragment(),
            "덧셈 연산(add)",
        )
    )

    object AddWeighted : Menu(
        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToAddWeightedOperationFragment(),
            "가중치 연산(addWeighted)",
        )
    )

    object Subtract : Menu(
        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToSubtractOperationFragment(),
            "뺄셈 연산(subtract)",
        )
    )

    object AbsDiff : Menu(
        Direction.from(
            MenuFragmentDirections.actionMenuFragmentToAbsDiffOperationFragment(),
            "차이 연산(absdiff)",
        )
    )
}