package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites

import cz.freego.tutorial.scaffolddynamiclabelexample.BaseEvent

sealed class FavoritesEvent : BaseEvent() {
    data object ExitEvent : FavoritesEvent()
    data class ShowToast(val message: String) : FavoritesEvent()
}