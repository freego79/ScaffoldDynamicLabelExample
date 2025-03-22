package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = viewModel(),
    onTitleChange: (ScaffoldUIState) -> Unit,
) {
    val favoritesViewState: FavoritesViewState = viewModel.viewState.value

    val context = LocalContext.current

    // scaffoldUIState držíme na ViewModel, aby nám přežil rotaci screeny
    // Jedná se o čistší řešení, než držet na Compose přes rememberSaveable
    // Navíc pro ScaffoldUIState bychom museli definovat jako Parcelable nebo
    // vytvořit vlastní Saver
    val scaffoldUIState = viewModel.scaffoldUIState

    // Odběr událostí z ViewModelu
    LaunchedEffect(viewModel.eventFlow) {
        // pokud se collectuje, tak donekonečna, v tomto případě neagregovat s jinými hodnotami,
        // jinak vznikne "unreachable" code, proto držíme odděleně ve dvou LaunchEffects
        viewModel.eventFlow.collect { event ->
            when (event) {
                is FavoritesEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is FavoritesEvent.ExitEvent -> {
                    (context as? android.app.Activity)?.finish()
                }
                is FavoritesEvent.SetNavigationVisibility -> {
                    scaffoldUIState.value = scaffoldUIState.value.copy(showBottomNavigation = event.isVisible)
                    onTitleChange(scaffoldUIState.value)
                }
            }
        }
    }

    // Aktualizace ScaffoldUIState
    LaunchedEffect(favoritesViewState.totalCount.value) {
        scaffoldUIState.value = scaffoldUIState.value.copy(title = "Oblíbené: ${favoritesViewState.totalCount.value}")
        onTitleChange(scaffoldUIState.value)
    }

    with(viewModel) {
        Favorites.Content(
            count = favoritesViewState.totalCount.value,
            isBottomNavigationVisible = scaffoldUIState.value.showBottomNavigation,
            actions = this,
        )
    }
}

object Favorites {

    interface Actions {
        fun onIncOnesCounterClicked() = Unit
        fun onIncTensCounterClicked() = Unit
        fun setNavigationVisibility(isVisible: Boolean) = Unit
    }

    @Composable
    fun Content(
        count: Int,
        isBottomNavigationVisible: Boolean,
        actions: Actions,
    ) {
        Column (
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {
            Text("Toto je obrazovka $count oblíbených")

            Button(onClick = { actions.onIncOnesCounterClicked() }) {
                Text("Counter +1")
            }

            Button(onClick = { actions.onIncTensCounterClicked() }) {
                Text("Counter +10")
            }

            val showHideText = if (isBottomNavigationVisible) "Skrýt" else "Zobrazit"
            Button(onClick = { actions.setNavigationVisibility(!isBottomNavigationVisible) }) {
                Text(
                    text = "$showHideText navigaci"
                )
            }
        }
    }

}
