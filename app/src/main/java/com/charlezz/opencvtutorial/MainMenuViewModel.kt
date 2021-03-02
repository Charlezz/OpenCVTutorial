package com.charlezz.opencvtutorial

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    app: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(app), MainMenuItem.Navigator {

    val navigateEvent = SingleLiveEvent<NavDirections>()

    val items: List<MainMenuItem> = MainMenu.values().map { MainMenuItem(it, this) }

    override fun navigateTo(directions: NavDirections) {
        navigateEvent.value = directions
    }
}