package com.charlezz.opencvtutorial.features.arithmetic

import androidx.navigation.NavDirections
import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.features.ArithmeticMenuFragmentDirections

sealed class ArithmeticMenus {
    object Add:Menu("덧셈 연산(add)", ArithmeticMenuFragmentDirections.actionArithmeticFragmentToAddOperationFragment())
    object AddWeighted:Menu("가중치 연산(addWeighted)",ArithmeticMenuFragmentDirections.actionArithmeticFragmentToAddWeightedOperationFragment())
    object Subtract:Menu("뺄셈 연산(subtract)",ArithmeticMenuFragmentDirections.actionArithmeticFragmentToSubtractOperationFragment())
    object AbsDiff:Menu("차이 연산(absdiff)",ArithmeticMenuFragmentDirections.actionArithmeticFragmentToAbsDiffOperationFragment())
}