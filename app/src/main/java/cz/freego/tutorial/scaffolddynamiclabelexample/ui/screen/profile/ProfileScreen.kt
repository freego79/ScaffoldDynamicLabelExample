package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.ScaffoldUIState

@Composable
fun ProfileScreen(onTitleChange: (ScaffoldUIState) -> Unit) {
    LaunchedEffect(Unit) { onTitleChange(ScaffoldUIState(title = "Profil")) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Toto je Profilová obrazovka")
    }
}