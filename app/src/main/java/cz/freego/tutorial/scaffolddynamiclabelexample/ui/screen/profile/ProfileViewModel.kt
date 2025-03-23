package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.profile

import androidx.compose.runtime.derivedStateOf
import cz.freego.tutorial.scaffolddynamiclabelexample.BaseViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState

class ProfileViewModel(
    initialState: ProfileViewState = ProfileViewState()
) : BaseViewModel<ProfileViewState,ProfileEvent>(initialState), Profile.Actions {

    val scaffoldUIState = derivedStateOf {
        ScaffoldUIState(
            title = "Profile (level: ${viewState.value.navigationLevel.intValue})",
            showBottomNavigation = viewState.value.navigationLevel.intValue < 4,
        )
    }

    // -- Actions metody izolované pouze pro Content UI --
    override fun navigateBack() {
        sendEvent(ProfileEvent.NavigateBack)
    }

    override fun navigateToNextProfile() {
        sendEvent(
            ProfileEvent.NavigateToProfile(viewState.value.navigationLevel.intValue + 1)
        )
    }

    // -- Ostatní metody --
    fun setNavigationLevel(navigationLevel: Int) {
        viewState.value.navigationLevel.intValue = navigationLevel
    }

}
