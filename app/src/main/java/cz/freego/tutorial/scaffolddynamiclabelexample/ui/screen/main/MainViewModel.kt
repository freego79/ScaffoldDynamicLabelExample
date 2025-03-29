package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel

// ViewModel, který umožní sdílet stavy a případné funkce mezi MainScreen se Scaffold a child Screens
// V tomto example projektu primárně pro nastavení ScaffoldUIState (title, actions a viditelnost
// bottom navigation bar pro aktuální obsah - vnořenou Screen)
class MainViewModel : ViewModel() {
    // stav Scaffoldu držíme skrze MainViewModel nad vším obsahem, co je ve Scaffold
    var scaffoldState by mutableStateOf(ScaffoldUIState())
        private set

    // stav TopBanneru držíme skrze MainViewModel nad vším obsahem, co je ve Scaffold
    var topBannerState by mutableStateOf(TopBannerUIState())
        private set

    // funkce dostpná v obsahu Scaffoldu, kterou si může každá vnořená Screen nastavit jeho vlastnosti
    fun updateScaffoldState(newState: ScaffoldUIState) {
        scaffoldState = newState
    }

    fun showTopBanner(
        title: String?,
        message: String?,
        action1Label: String?,
        action2Label: String?,
        onAction1Click: (() -> Unit)?,
        onAction2Click: (() -> Unit)?,
        onOutsideClick: (() -> Unit)?,
    ) {
        topBannerState = TopBannerUIState(
            visible = true,
            title = title,
            message = message,
            action1Label = action1Label,
            action2Label = action2Label,
            onAction1Click = onAction1Click,
            onAction2Click = onAction2Click,
            onOutsideClick = onOutsideClick,
        )
    }

    fun hideTopBanner() {
        if (topBannerState.visible) topBannerState = topBannerState.copy(visible = false)
    }
}

// CompositionLocal pro sdílení MainViewModel
val LocalMainViewModel = staticCompositionLocalOf<MainViewModel> {
    error("No MainViewModel provided")
}