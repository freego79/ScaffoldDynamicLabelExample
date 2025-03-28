package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.PreviewShowcase
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.RichText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.TitleLargeText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.LocalMainViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.MainViewModel
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState
import cz.freego.tutorial.scaffolddynamiclabelexample.utils.emojiCodeToEscapeSequence

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
    }

}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    PreviewShowcase {
        HomeScreenContent()
    }
}