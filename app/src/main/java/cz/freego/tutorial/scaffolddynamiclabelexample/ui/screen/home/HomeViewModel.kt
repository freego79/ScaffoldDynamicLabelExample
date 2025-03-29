package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.home

import cz.freego.tutorial.scaffolddynamiclabelexample.BaseViewModel

class HomeViewModel: BaseViewModel<HomeViewState, HomeEvent>(HomeViewState()), Home.Actions {

    override fun showMessage(message: String) {
        sendEvent(HomeEvent.ShowToast(message))
    }

    override fun hideTopBanner() {
        sendEvent(HomeEvent.HideTopBanner)
    }

    override fun showTopBanner(
        title: String?,
        message: String?,
        action1Label: String?,
        action2Label: String?,
        onAction1Click: (() -> Unit)?,
        onAction2Click: (() -> Unit)?,
        onOutsideClick: (() -> Unit)?,
    ) {
        sendEvent(
            HomeEvent.ShowTopBanner(
                title = title,
                message = message,
                action1Label = action1Label,
                action2Label = action2Label,
                onAction1Click = onAction1Click,
                onAction2Click = onAction2Click,
                onOutsideClick = onOutsideClick,
            )
        )
    }

}