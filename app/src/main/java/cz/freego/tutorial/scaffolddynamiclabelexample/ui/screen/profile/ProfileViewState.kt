package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.profile

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import cz.freego.tutorial.scaffolddynamiclabelexample.BaseViewState

// ViewState s možností přednastavení hodnot - při vytváření ViewModelu ihned nastavíme výchozí hodnotu
// initialNavigationLevel ze vstupního parametru navigationLevel. Tuto hodnotu je nutné přiřadit
// ještě před první kompozicí, jinak by defaultní hodnota měla dopad na derivovanou boolean hodnotu
// navigationVisible,  která je podmíněna < 4. Následkem by bylo, že by se stihla kompozice ještě
// s default hodnotou, než bychom ji přenastavili LaunchEffectem na 4+ a např. při rotaci obrazovky by
// skrývání bottom navigation bar problikávalo. OBecně pak platí, že přednastavení iniciačních hodnot
// zajistí, že jsou platné už při první kompozici a nestihne se vykreslit na základě default stavů.
class ProfileViewState(initialNavigationLevel: Int = 1) : BaseViewState() {

    val navigationLevel = mutableIntStateOf(initialNavigationLevel)
    val navigationVisible = derivedStateOf { navigationLevel.intValue < 4 }
}