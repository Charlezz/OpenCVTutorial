package com.charlezz.opencvtutorial.features.filtering

import com.charlezz.opencvtutorial.Menu
import com.charlezz.opencvtutorial.MenuDirections
import com.charlezz.opencvtutorial.MenuFragmentDirections
import com.charlezz.opencvtutorial.features.basic.arithmetic.ArithmeticMenus

sealed class FilteringMenus {

    object Filter2D : Menu(
        "기본적인 필터 적용",
        MenuDirections.from(MenuFragmentDirections.actionMenuFragmentToFilter2dFragment()),
        0
    )

}