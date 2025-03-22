package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.Action
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    val favoritesViewState: FavoritesViewState = FavoritesViewState()
) : ViewModel(), Favorites.Actions {

    private val _eventFlow = MutableSharedFlow<FavoritesEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val scaffoldUIState = mutableStateOf(
        ScaffoldUIState(
            title = "Oblíbené: ${favoritesViewState.totalCount.value}",
            actions = listOf(
                Action(
                    icon = Icons.Default.Add,
                    label = "+1",
                    onClick = { onIncOnesCounterClicked() }
                ),
                Action(
                    icon = Icons.Default.Email,
                    label = "Message",
                    onClick = { showToastMessage("Hello!") }
                ),
                Action(
                    icon = Icons.Default.Close,
                    label = "ExitApp",
                    onClick = { performCloseApp() }
                ),
            )
        )
    )

    // -- Actions metody izolované pouze pro Content UI --
    override fun onIncOnesCounterClicked() {
        favoritesViewState.onesCount.intValue ++
    }

    override fun onIncTensCounterClicked() {
        favoritesViewState.tensCount.intValue += 10
    }

    override fun setNavigationVisibility(isVisible: Boolean) {
        sendEvent(FavoritesEvent.SetNavigationVisibility(isVisible))
    }

    // -- Ostatní metody --

    fun showToastMessage(message: String) {
        sendEvent(FavoritesEvent.ShowToast(message))
    }

    fun performCloseApp() {
        sendEvent(FavoritesEvent.ExitEvent)
    }

    private fun sendEvent(favoritesEvent: FavoritesEvent) {
        viewModelScope.launch {
            _eventFlow.emit(favoritesEvent)
        }
    }
}
