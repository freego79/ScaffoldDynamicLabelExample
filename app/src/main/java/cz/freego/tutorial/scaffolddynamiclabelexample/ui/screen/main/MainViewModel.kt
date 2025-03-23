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

    // funkce dostpná v obsahu Scaffoldu, kterou si může každá vnořená Screen nastavit jeho vlastnosti
    fun updateScaffoldState(newState: ScaffoldUIState) {
        scaffoldState = newState
    }
}

// CompositionLocal pro sdílení MainViewModel
val LocalMainViewModel = staticCompositionLocalOf<MainViewModel> {
    error("No MainViewModel provided")
}