package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.profile

import cz.freego.tutorial.scaffolddynamiclabelexample.BaseEvent

sealed class ProfileEvent : BaseEvent() {
    data object NavigateBack : ProfileEvent()
    data class NavigateToProfile(val navigationLevel: Int) : ProfileEvent()
}