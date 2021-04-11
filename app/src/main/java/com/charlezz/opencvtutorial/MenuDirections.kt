package com.charlezz.opencvtutorial

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.charlezz.opencvtutorial.features.basic.BasicMenus
import kotlinx.parcelize.Parcelize


@Parcelize
data class MenuDirections(
    val id:Int,
    val args:Bundle? = null
):Parcelable {

    companion object{
        fun from(navDirections: NavDirections):MenuDirections{
            return MenuDirections(navDirections.actionId, navDirections.arguments)
        }
    }
}