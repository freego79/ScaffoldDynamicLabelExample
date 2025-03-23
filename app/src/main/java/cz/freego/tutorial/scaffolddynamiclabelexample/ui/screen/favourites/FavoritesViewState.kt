package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import cz.freego.tutorial.scaffolddynamiclabelexample.BaseViewState

class FavoritesViewState : BaseViewState() {

    val navigationVisible = mutableStateOf(true)

    val messageCount = mutableIntStateOf(0)
    val messageInfo = derivedStateOf {
        when(messageCount.intValue) {
            1 -> "${messageCount.intValue} zpráva"
            in 2..4 -> "${messageCount.intValue} zprávy"
            else -> "${messageCount.intValue} zpráv"
        }
    }

    val onesCount = mutableIntStateOf(0)
    val tensCount = mutableIntStateOf(0)
    val totalCount = derivedStateOf { onesCount.intValue + tensCount.intValue }
}