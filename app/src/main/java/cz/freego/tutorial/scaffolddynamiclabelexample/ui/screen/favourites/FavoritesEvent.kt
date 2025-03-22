package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites

sealed class FavoritesEvent {
    object ExitEvent : FavoritesEvent()
    data class ShowToast(val message: String) : FavoritesEvent()
    data class SetNavigationVisibility(val isVisible: Boolean) : FavoritesEvent()
}