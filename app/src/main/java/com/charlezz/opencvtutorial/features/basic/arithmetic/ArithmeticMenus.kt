package com.charlezz.opencvtutorial.features.basic.arithmetic

import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuDirections
import com.charlezz.opencvtutorial.MenuFragmentDirections


sealed class ArithmeticMenus {
    object Add:Menu("덧셈 연산(add)", MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToAddOperationFragment()))

    object AddWeighted:Menu("가중치 연산(addWeighted)",MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToAddWeightedOperationFragment()))

    object Subtract:Menu("뺄셈 연산(subtract)",MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToSubtractOperationFragment()))

    object AbsDiff:Menu("차이 연산(absdiff)",MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToAbsDiffOperationFragment()))
}