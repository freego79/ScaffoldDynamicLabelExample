package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState

@Composable
fun HomeScreen(onUIChange: (ScaffoldUIState) -> Unit) {
    LaunchedEffect(Unit) { onUIChange(ScaffoldUIState(title = "Domů")) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Toto je Domovská obrazovka")
    }
}