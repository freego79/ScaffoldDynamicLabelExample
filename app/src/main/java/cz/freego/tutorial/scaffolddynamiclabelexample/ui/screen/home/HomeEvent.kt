package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.home

import cz.freego.tutorial.scaffolddynamiclabelexample.BaseEvent

sealed class HomeEvent : BaseEvent() {
    data class ShowToast(val message: String) : HomeEvent()
    data object HideTopBanner : HomeEvent()
    data class ShowTopBanner(
        val title: String? = null,
        val message: String? = null,
        val action1Label: String? = null,
        val action2Label: String? = null,
        val onAction1Click: (() -> Unit)? = null,
        val onAction2Click: (() -> Unit)? = null,
        val onOutsideClick: (() -> Unit)? = null,
    ) : HomeEvent()
}