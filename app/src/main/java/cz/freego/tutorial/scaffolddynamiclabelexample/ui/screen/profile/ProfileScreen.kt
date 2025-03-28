package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.PreviewShowcase
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.ButtonText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.RichText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.TitleLargeText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.TitleMediumText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.LocalMainViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.MainViewModel

@Composable
fun ProfileScreen(
    navigationLevel: Int,
    navController: NavController,
    mainViewModel: MainViewModel = LocalMainViewModel.current, // získáváme globální MainViewModel
    viewModel: ProfileViewModel = viewModel {
        ProfileViewModel(initialState = ProfileViewState(initialNavigationLevel = navigationLevel))
    } // vytváříme lokální ProfileViewModel s přednastavenou hodnotou stavu navigationLevel
) {
    val viewState: ProfileViewState = viewModel.viewState.value

    val scaffoldUIState = viewModel.scaffoldUIState.value
    LaunchedEffect(scaffoldUIState) {
        mainViewModel.updateScaffoldState(scaffoldUIState)
    }

    LaunchedEffect(navigationLevel) {
        viewModel.setNavigationLevel(navigationLevel)
    }

    // Odběr událostí z ViewModelu
    LaunchedEffect(viewModel.eventFlow) {
        // pokud se collectuje, tak donekonečna, v tomto případě neagregovat s jinými hodnotami,
        // jinak vznikne "unreachable" code, proto držíme odděleně ve dvou LaunchEffects
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ProfileEvent.NavigateBack -> {
                    if (viewState.navigationLevel.intValue > 1) {
                        navController.popBackStack()
                    } else {
                        navController.popBackStack("home", false)
                    }
                }
                is ProfileEvent.NavigateToProfile -> {
                    navController.navigate("profile/${event.navigationLevel}")
                }
            }
        }
    }

    Profile.Content(
        navigationLevel = viewState.navigationLevel.intValue,
        actions = viewModel,
    )
}

object Profile {

    interface Actions {
        fun navigateBack() = Unit
        fun navigateToNextProfile() = Unit
    }

    @Composable
    fun Content(
        navigationLevel: Int,
        actions: Actions,
    ) {
        Column (
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(16.dp))

            TitleLargeText("Toto je Profilová obrazovka")
            Spacer(Modifier.height(24.dp))

            TitleMediumText("Úroveň zanoření: $navigationLevel")
            Spacer(Modifier.height(8.dp))

            Row {
                Button(onClick = { actions.navigateBack() }) {
                    ButtonText(
                        if (navigationLevel > 1) "Zpět na level ${navigationLevel - 1}"
                        else "Zpět na Domů"
                    )
                }
                Spacer(Modifier.width(8.dp))
                Button(enabled = navigationLevel < 6, onClick = { actions.navigateToNextProfile() }) {
                    ButtonText("Jít na level ${navigationLevel + 1}")
                }
            }

            Spacer(Modifier.height(16.dp))
            RichText("Vnoření do 3. úrovně **zobrazuje** spodní navigační **lištu,** od " +
                    "úrovně 4 až do úrovně 6 se spodní navigační **lišta skryje.**")

            Spacer(Modifier.height(16.dp))
        }
    }

}

@PreviewLightDark
@Composable
private fun ProfileScreenPreview() {
    PreviewShowcase {
        Profile.Content(
            navigationLevel = 3,
            actions = object: Profile.Actions{}
        )
    }
}