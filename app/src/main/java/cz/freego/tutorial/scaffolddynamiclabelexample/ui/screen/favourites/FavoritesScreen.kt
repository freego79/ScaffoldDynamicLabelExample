package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.ButtonText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.TitleLargeText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.TitleMediumText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.LocalMainViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.MainViewModel

@Composable
fun FavoritesScreen(
    mainViewModel: MainViewModel = LocalMainViewModel.current, // získáváme globální MainViewModel
    viewModel: FavoritesViewModel = viewModel(), // vytváříme lokální FavoritesViewModel
) {
    val viewState: FavoritesViewState = viewModel.viewState.value
    val context = LocalContext.current

    // sledujeme změny stavu scaffoldUI a při změně delegujeme do globálního mainViewModel
    val scaffoldUIState = viewModel.scaffoldUIState.value
    LaunchedEffect(scaffoldUIState) {
        mainViewModel.updateScaffoldState(scaffoldUIState)
    }

    // Odběr událostí z ViewModelu
    LaunchedEffect(viewModel.eventFlow) {
        // pokud se collectuje, tak donekonečna, v tomto případě neagregovat s jinými hodnotami,
        // jinak vznikne "unreachable" code
        viewModel.eventFlow.collect { event ->
            when (event) {
                is FavoritesEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is FavoritesEvent.ExitEvent -> {
                    (context as? android.app.Activity)?.finish()
                }
            }
        }
    }

    Favorites.Content(
        count = viewState.totalCount.value,
        isBottomNavigationVisible = viewState.navigationVisible.value,
        actions = viewModel,
    )
}

object Favorites {

    interface Actions {
        fun onIncOnesCounterClicked() = Unit
        fun onIncTensCounterClicked() = Unit
        fun setNavigationVisibility(isVisible: Boolean) = Unit
        fun resetCounterClicked() = Unit
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

            TitleLargeText("Toto je obrazovka oblíbených")
            Spacer(Modifier.height(4.dp))
            TitleMediumText("Počet oblíbených: $count")
            Spacer(Modifier.height(4.dp))

            Row {
                Button(onClick = { actions.onIncOnesCounterClicked() }) {
                    ButtonText("Counter +1")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { actions.onIncTensCounterClicked() }) {
                    ButtonText("Counter +10")
                }
            }

            Row {
                Button(onClick = { actions.resetCounterClicked() }) {
                    ButtonText("Reset")
                }
                Spacer(Modifier.width(8.dp))

                val showHideText = if (isBottomNavigationVisible) "Skrýt" else "Zobrazit"
                Button(onClick = { actions.setNavigationVisibility(!isBottomNavigationVisible) }) {
                    ButtonText("$showHideText navigaci")
                }
            }
        }
    }

}
