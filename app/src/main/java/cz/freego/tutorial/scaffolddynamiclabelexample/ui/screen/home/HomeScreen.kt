package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.PreviewShowcase
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.ButtonText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.RichText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.TitleLargeText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.LocalMainViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.MainViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState
import cz.freego.tutorial.scaffolddynamiclabelexample.utils.emojiCodeToEscapeSequence

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = LocalMainViewModel.current,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current

    // V případě této obrazovky chceme jen nastavit jednorázově title ve Scaffoldu, proto
    // nemusíme držet stav ScaffoldUIState po celou dobu životnosti Screen a nastavíme přímo z UI
    LaunchedEffect(Unit) {
        mainViewModel.updateScaffoldState(ScaffoldUIState(title = "Domů"))
    }

    LaunchedEffect(viewModel.eventFlow) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is HomeEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is HomeEvent.HideTopBanner -> {
                    mainViewModel.hideTopBanner()
                }
                is HomeEvent.ShowTopBanner -> {
                    mainViewModel.showTopBanner(
                        title = event.title,
                        message = event.message,
                        action1Label = event.action1Label,
                        action2Label = event.action2Label,
                        onAction1Click = event.onAction1Click,
                        onAction2Click = event.onAction2Click,
                        onOutsideClick = event.onOutsideClick,
                    )
                }
            }
        }
    }

    Home.Content(actions = viewModel)
}

object Home {

    interface Actions {
        fun showMessage(message: String) = Unit
        fun hideTopBanner() = Unit
        fun showTopBanner(
            title: String? = null,
            message: String? = null,
            action1Label: String? = null,
            action2Label: String? = null,
            onAction1Click: (() -> Unit)? = null,
            onAction2Click: (() -> Unit)? = null,
            onOutsideClick: (() -> Unit)? = null,
        ) = Unit
    }

    @Composable
    fun Content(
        actions: Actions,
    ) {
        Column (
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(Modifier.height(16.dp))

            TitleLargeText("Toto je Domovská obrazovka")
            Spacer(Modifier.height(24.dp))

            RichText(
                style = MaterialTheme.typography.bodyLarge,
                text ="Domovská obrazovka nabízí **nestandardní navigační prvek** pro ukončení " +
                        "aplikace (křížek místo šipky zpět). Jen pro **ukázku flexibility.** ${"1f60e".emojiCodeToEscapeSequence()}"
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 1.dp,
            )

            val bullet = String(Character.toChars(0x2022))
            val formBullet = "   $bullet "
            RichText(
                style = MaterialTheme.typography.bodyMedium,
                text = "Vedle **tučného textu** plně **podporuje Emoji**. Ukázka emotikonů:" +
                        "\n**$formBullet bear** ${"1f43b".emojiCodeToEscapeSequence()}" +
                        "\n**$formBullet sunflower** ${"1f33b".emojiCodeToEscapeSequence()}" +
                        "\n**$formBullet tropical_drink** ${"1f379".emojiCodeToEscapeSequence()}" +
                        "\n**$formBullet cactus** ${"1f335".emojiCodeToEscapeSequence()}" +
                        "\n**$formBullet anchor** ${"2693".emojiCodeToEscapeSequence()}"
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 1.dp,
            )

            RichText(
                style = MaterialTheme.typography.bodyMedium,
                text = " Tato rozšířená textová komponenta **umožňuje přidat i leading ikonu** univerzálně " +
                        "jako **Composable obsah** pomocí parametru, včetně optional leadingIconSize " +
                        "(defaultně si bere velikost ze stylu textu, takže velikost leading ikony " +
                        "systémovému nastavení zvětšní písma - reflektuje accessibility, stejně jako " +
                        "emoji). ${"1f44d".emojiCodeToEscapeSequence()}",
                boldTextColor = MaterialTheme.colorScheme.secondary,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Info Icon",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                },
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    actions.showTopBanner(
                        title = "Ups, něco se pokazilo!",
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                        action1Label = "Say Hello!",
                        action2Label = "Rozumím",
                        onAction1Click = { actions.showMessage("Hello there!") },
                        onAction2Click = { actions.hideTopBanner() },
                        onOutsideClick = { actions.showMessage("Clicked outside") },
                    )
                }
            ) {
                ButtonText("Show Top Banner")
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    PreviewShowcase {
        Home.Content(
            actions = object: Home.Actions{}
        )
    }
}