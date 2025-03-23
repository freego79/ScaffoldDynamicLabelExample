package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import cz.freego.tutorial.scaffolddynamiclabelexample.BaseViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.Action
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState

class FavoritesViewModel(
    initialState: FavoritesViewState = FavoritesViewState()
) : BaseViewModel<FavoritesViewState, FavoritesEvent>(initialState), Favorites.Actions {

    // Držíme si stav ScaffoldUIState v lokálním view modelu, protože ho snadno synchronizujeme
    // s hodnotami ViewState derivací a také nám snadno umožňuje nastavit actions přímo na funkce
    // tohoto lokálního view model. Každou změnu stavu ScaffoldUIState sledujeme přes LaunchedEffect,
    // kde ho synchronizujeme se stavem na globálním MainViewModel
    val scaffoldUIState: State<ScaffoldUIState> = derivedStateOf {
        ScaffoldUIState(
            title = "Oblíbené: ${viewState.value.totalCount.value}",
            actions = listOf(
                Action(icon = Icons.Default.Add, label = "+1", onClick = { onIncOnesCounterClicked() }),
                Action(icon = Icons.Default.Email, label = "Message", onClick = { showNextMessage() }),
                Action(icon = Icons.Default.Close, label = "ExitApp", onClick = { performCloseApp() }),
            ),
            showBottomNavigation = viewState.value.navigationVisible.value,
        )
    }

    // -- Actions metody izolované pouze pro Content UI --
    override fun onIncOnesCounterClicked() {
        viewState.value.onesCount.intValue++
        // Nemusíme volat změnu celého ViewState, ale pouze konkrétní hodnotu
        // setState(viewState.value)  // Aktualizace celého stavu
    }

    override fun onIncTensCounterClicked() {
        viewState.value.tensCount.intValue += 10
        // Nemusíme volat změnu celého ViewState, ale pouze konkrétní hodnotu
        // setState(viewState.value)  // Aktualizace celého stavu
    }

    override fun setNavigationVisibility(isVisible: Boolean) {
        viewState.value.navigationVisible.value = isVisible
    }

    override fun resetCounterClicked() {
        setState(FavoritesViewState())
    }

    // -- Ostatní metody --

    private fun showNextMessage() {
        viewState.value.messageCount.intValue++
        sendEvent(FavoritesEvent.ShowToast(viewState.value.messageInfo.value))
    }

    private fun performCloseApp() {
        sendEvent(FavoritesEvent.ExitEvent)
    }
}
