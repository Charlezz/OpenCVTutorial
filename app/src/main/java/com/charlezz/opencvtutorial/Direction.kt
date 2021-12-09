package com.charlezz.opencvtutorial

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import kotlinx.parcelize.Parcelize


@Parcelize
data class Direction(
    val title:String,
    val id:Int,
    val args:Bundle? = null
):Parcelable {

    companion object{
        fun from(navDirections: NavDirections,title:String = ""):Direction{
            return Direction(title,navDirections.actionId, navDirections.arguments)
        }
    }
}