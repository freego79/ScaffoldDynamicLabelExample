package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.profile

import androidx.compose.runtime.mutableIntStateOf
import cz.freego.tutorial.scaffolddynamiclabelexample.BaseViewState

class ProfileViewState : BaseViewState() {

    val navigationLevel = mutableIntStateOf(1)
}