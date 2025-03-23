package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main

import androidx.compose.ui.graphics.vector.ImageVector

data class ScaffoldUIState(
    val title: String = "",
    val showBottomNavigation: Boolean = true,
    val actions: List<Action> = emptyList()
)

data class Action(
    val icon: ImageVector,
    val onClick: () -> Unit,
    val label: String
)