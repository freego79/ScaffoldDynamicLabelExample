package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.BottomNavigationBar
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.ScreenTitleText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites.FavoritesScreen
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.home.HomeScreen
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = viewModel()
) {

    // podržíme si Context (jen pro exit app)
    val context = LocalContext.current

    // získáme aktuální route, abychom rozlišili "home" obsah
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
                            IconButton(onClick = { (context as? android.app.Activity)?.finish() }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "ExitApp"
                                )
                            }
                        } else {
                            // jinak klasický back, v tomto případě vždy návrat na "home"
                            IconButton(
                                onClick = {
                                    // Pop back stack do "home" a vymazání historie
                                    // Pokud chceme zachovat celou historii, stačí volat bez parametrů
                                    navController.popBackStack("home", false)
                                    // Standardní back skrze celou historii
                                    // navController.popBackStack()
                                }
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
                if (mainViewModel.scaffoldState.showBottomNavigation) {
                    BottomNavigationBar(navController)
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(paddingValues)
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
        }
    }
}
