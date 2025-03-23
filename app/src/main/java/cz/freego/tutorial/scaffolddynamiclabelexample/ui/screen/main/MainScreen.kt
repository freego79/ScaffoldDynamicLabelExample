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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.BottomNavigationBar
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.favourites.FavoritesScreen
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.home.HomeScreen
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {

    // podržíme si Context (jen pro exit app)
    val context = LocalContext.current

    // Dynamický stav scaffoldu
    val currentScaffoldState = remember {
        mutableStateOf(
            ScaffoldUIState(
                title = "",
            )
        )
    }

    // získáme aktuální route, abychom rozlišili "home" obsah
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = currentScaffoldState.value.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    if (currentRoute == "home") {
                        // nestandardní zavírací křížek, když jsme na "home" - jen pro ukázku flexibility
                        IconButton(
                            onClick = {
                                // Exit App
                                (context as? android.app.Activity)?.finish()
                            }
                        ) {
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
                    currentScaffoldState.value.actions.forEach { action ->
                        IconButton(onClick = action.onClick) {
                            Icon(action.icon, contentDescription = action.label)
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (currentScaffoldState.value.showBottomNavigation) {
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
                HomeScreen { newUIState -> currentScaffoldState.value = newUIState }
            }
            composable(
                route = "profile/{navigationLevel}",
                arguments = listOf(navArgument("navigationLevel") { type = NavType.IntType }),
            ) { backStackEntry ->
                val navigationLevel = backStackEntry.arguments?.getInt("navigationLevel") ?: 1
                ProfileScreen(
                    navigationLevel = navigationLevel,
                    navController = navController
                ) { newUIState -> currentScaffoldState.value = newUIState }
            }
            composable("favorites") {
                FavoritesScreen { newUIState -> currentScaffoldState.value = newUIState }
            }
        }
    }
}
