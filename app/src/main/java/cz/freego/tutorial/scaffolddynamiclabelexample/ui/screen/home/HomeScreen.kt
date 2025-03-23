package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.BodySmallText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.TitleLargeText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.LocalMainViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.MainViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = LocalMainViewModel.current
) {

    // V případě této obrazovky chceme jen nastavit jednorázově title ve Scaffoldu, proto
    // nemusíme držet stav ScaffoldUIState po celou dobu životnosti Screen a nastavíme přímo z UI
    LaunchedEffect(Unit) {
        mainViewModel.updateScaffoldState(ScaffoldUIState(title = "Domů"))
    }

    Column (
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleLargeText("Toto je Domovská obrazovka")
        Spacer(Modifier.height(4.dp))
        BodySmallText("Domovská obrazovka nabízí nestandardní navigační prvek pro ukončení " +
                "aplikace (křížek místo šipky zpět). Jen pro ukázku flexibility.")
    }
}