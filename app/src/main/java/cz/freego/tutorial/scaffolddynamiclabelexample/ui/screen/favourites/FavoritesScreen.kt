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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = viewModel(),
    onTitleChange: (ScaffoldUIState) -> Unit,
) {
    val viewState: FavoritesViewState = viewModel.viewState.value

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
            }
        }
    }

    LaunchedEffect(scaffoldUIState.value) {
        onTitleChange(scaffoldUIState.value)
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

            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Toto je obrazovka oblíbených"
            )

            Spacer(Modifier.height(4.dp))

            Text(
                style = MaterialTheme.typography.titleMedium,
                text = "Počet oblíbených: $count"
            )

            Spacer(Modifier.height(4.dp))

            Row {
                Button(onClick = { actions.onIncOnesCounterClicked() }) {
                    Text("Counter +1")
                }

                Spacer(Modifier.width(8.dp))

                Button(onClick = { actions.onIncTensCounterClicked() }) {
                    Text("Counter +10")
                }
            }

            Row {
                Button(onClick = { actions.resetCounterClicked() }) {
                    Text("Reset")
                }

                Spacer(Modifier.width(8.dp))

                val showHideText = if (isBottomNavigationVisible) "Skrýt" else "Zobrazit"
                Button(onClick = { actions.setNavigationVisibility(!isBottomNavigationVisible) }) {
                    Text(
                        text = "$showHideText navigaci"
                    )
                }
            }

        }
    }

}
