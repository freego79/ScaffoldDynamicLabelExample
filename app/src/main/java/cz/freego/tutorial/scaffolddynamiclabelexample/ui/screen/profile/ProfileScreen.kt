package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState

@Composable
fun ProfileScreen(
    navigationLevel: Int,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(),
    onTitleChange: (ScaffoldUIState) -> Unit,
) {
    val viewState: ProfileViewState = viewModel.viewState.value

    val scaffoldUIState = viewModel.scaffoldUIState

    LaunchedEffect(scaffoldUIState.value, navigationLevel) {
        viewModel.setNavigationLevel(navigationLevel)
        onTitleChange(scaffoldUIState.value)
    }

    // Odběr událostí z ViewModelu
    LaunchedEffect(viewModel.eventFlow) {
        // pokud se collectuje, tak donekonečna, v tomto případě neagregovat s jinými hodnotami,
        // jinak vznikne "unreachable" code, proto držíme odděleně ve dvou LaunchEffects
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ProfileEvent.NavigateBack -> {
                    navController.popBackStack()
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
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Toto je Profilová obrazovka",
            )

            Spacer(Modifier.height(4.dp))

            Text(
                style = MaterialTheme.typography.titleMedium,
                text = "Úroveň zanoření: $navigationLevel",
            )

            Spacer(Modifier.height(4.dp))

            Row {
                Button(onClick = { actions.navigateBack() }) {
                    Text(
                        text = if (navigationLevel > 1) "Zpět na level ${navigationLevel - 1}" else "Zpět na Domů"
                    )
                }

                Spacer(Modifier.width(8.dp))

                Button(
                    enabled = navigationLevel < 6,
                    onClick = { actions.navigateToNextProfile() }
                ) {
                    Text("Jít na level ${navigationLevel + 1}")
                }
            }

            Spacer(Modifier.height(4.dp))

            Text(
                style = MaterialTheme.typography.bodySmall,
                text ="Vnoření do 3. úrovně zobrazuje spodní navigační lištu, od úrovně 4 až do úrovně 6 se spodní navigační lišta skryje."
            )
        }
    }

}