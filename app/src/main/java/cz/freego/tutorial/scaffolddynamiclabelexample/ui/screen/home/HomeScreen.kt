package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.PreviewShowcase
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.DynamicBoldText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.TitleLargeText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.LocalMainViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.MainViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = LocalMainViewModel.current
) {

    // V případě této obrazovky chceme jen nastavit jednorázově title ve Scaffoldu, proto
    // nemusíme držet stav ScaffoldUIState po celou dobu životnosti Screen a nastavíme přímo z UI
    LaunchedEffect(Unit) {
        mainViewModel.updateScaffoldState(ScaffoldUIState(title = "Domů"))
    }

    HomeScreenContent(modifier = modifier)
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
) {
    Column (
        modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(16.dp))

        TitleLargeText("Toto je Domovská obrazovka")
        Spacer(Modifier.height(16.dp))
        DynamicBoldText("Domovská obrazovka nabízí **nestandardní navigační prvek** pro ukončení " +
                "aplikace (křížek místo šipky zpět). Jen pro **ukázku flexibility.**")

        Spacer(Modifier.height(16.dp))
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    PreviewShowcase {
        HomeScreenContent()
    }
}