package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main

data class TopBannerUIState(
    val visible: Boolean = false,
    val title: String? = null,
    val message: String? = null,
    val action1Label: String? = null,
    val action2Label: String? = null,
    val onAction1Click: (() -> Unit)? = null,
    val onAction2Click: (() -> Unit)? = null,
    val onOutsideClick: (() -> Unit)? = null,
)