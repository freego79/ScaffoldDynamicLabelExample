package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.mutableStateOf
import cz.freego.tutorial.scaffolddynamiclabelexample.BaseViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.Action
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState

class FavoritesViewModel(
    initialState: FavoritesViewState = FavoritesViewState()
) : BaseViewModel<FavoritesViewState, FavoritesEvent>(initialState), Favorites.Actions {

    // výchozí state scaffold UI (v případě string resources nebudeme zatahovat do ViewModel Context,
    // ale v případě potřeby si je předáme do ViewModelu z Composable. Není to moc šikovné, ale
    // benefit je větší v tom, držet stav scaffoldu na ViewModelu, z důvodu přežití rotace obrazovky)
    val scaffoldUIState = mutableStateOf(
        ScaffoldUIState(
            title = "Oblíbené: ${viewState.value.totalCount.value}",
            actions = listOf(
                Action(icon = Icons.Default.Add, label = "+1", onClick = { onIncOnesCounterClicked() }),
                Action(icon = Icons.Default.Email, label = "Message", onClick = { showToastMessage("Hello!") }),
                Action(icon = Icons.Default.Close, label = "ExitApp", onClick = { performCloseApp() }),
            )
        )
    )

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
        sendEvent(FavoritesEvent.SetNavigationVisibility(isVisible))
    }

    // -- Ostatní metody --

    private fun showToastMessage(message: String) {
        sendEvent(FavoritesEvent.ShowToast(message))
    }

    private fun performCloseApp() {
        sendEvent(FavoritesEvent.ExitEvent)
    }
}
