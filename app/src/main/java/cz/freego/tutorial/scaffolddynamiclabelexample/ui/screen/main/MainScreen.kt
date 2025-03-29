package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.BottomNavigationBar
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.PreviewShowcase
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.ScreenTitleText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites.FavoritesScreen
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.home.HomeScreen
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.component.TopBanner
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = viewModel()
) {

    // podržíme si Context (jen pro exit app)
    val context = LocalContext.current

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Poskytnutí MainViewModel pomocí CompositionLocalProvider do child Screenů přes Content lambda
    CompositionLocalProvider(LocalMainViewModel provides mainViewModel) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { ScreenTitleText(text = mainViewModel.scaffoldState.title) },
                    navigationIcon = {
                        if (currentRoute == "home") {
                            // nestandardní zavírací křížek, když jsme na "home" - jen pro ukázku flexibility
                            IconButton(onClick = {
                                mainViewModel.showTopBanner(
                                    title = "Ukončení aplikace",
                                    message = "Skutečně chcete ukončit aplikaci? Tento banner je modální a nelze ho zavřít kliknutím mimo něj.",
                                    action1Label = "Ukončit",
                                    action2Label = "Zpět",
                                    onAction1Click = { (context as? android.app.Activity)?.finish() },
                                    onAction2Click = { mainViewModel.hideTopBanner() },
                                    isModal = true,
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "ExitApp"
                                )
                            }
                        } else {
                            // jinak klasický back, v tomto případě vždy návrat na "home"
                            IconButton(
                                onClick = { navController.popBackStack() }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    },
                    actions = {
                        mainViewModel.scaffoldState.actions.forEach { action ->
                            IconButton(onClick = action.onClick) {
                                Icon(action.icon, contentDescription = action.label)
                            }
                        }
                    }
                )
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = mainViewModel.scaffoldState.showBottomNavigation,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(animationSpec = tween(500)),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(animationSpec = tween(500)),
                ) {
                    BottomNavigationBar(navController)
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "home",
                ) {
                    composable("home") {
                        HomeScreen()
                    }
                    composable(
                        route = "profile/{navigationLevel}",
                        arguments = listOf(navArgument("navigationLevel") { type = NavType.IntType }),
                    ) { backStackEntry ->
                        val navigationLevel = backStackEntry.arguments?.getInt("navigationLevel") ?: 1
                        ProfileScreen(
                            navigationLevel = navigationLevel,
                            navController = navController
                        )
                    }
                    composable("favorites") {
                        FavoritesScreen()
                    }
                }

                val interactionSource = remember { MutableInteractionSource() }
                AnimatedVisibility(
                    visible = mainViewModel.topBannerState.visible,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = .75f))
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null // Zruší vizuální efekt kliknutí
                            ) {
                                if (mainViewModel.topBannerState.isModal.not()) {
                                    mainViewModel.hideTopBanner()
                                }
                                mainViewModel.topBannerState.onOutsideClick?.invoke()
                            }
                    )
                }

                AnimatedVisibility(
                    visible = mainViewModel.topBannerState.visible,
                    enter = slideInVertically(initialOffsetY = { -it }),
                    exit = slideOutVertically(targetOffsetY = { -it }),
                ) {
                    Card(
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                    ) {
                        TopBanner(topBannerUIState = mainViewModel.topBannerState)
                    }
                }

            }
        }
    }
}

@PreviewLightDark
@Composable
private fun MainScreenPreview() {
    PreviewShowcase {
        MainScreen()
    }
}