package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import cz.freego.tutorial.scaffolddynamiclabelexample.BaseViewState

class FavoritesViewState : BaseViewState() {

    val onesCount = mutableIntStateOf(0)
    val tensCount = mutableIntStateOf(0)

    val totalCount = derivedStateOf { onesCount.intValue + tensCount.intValue }
}