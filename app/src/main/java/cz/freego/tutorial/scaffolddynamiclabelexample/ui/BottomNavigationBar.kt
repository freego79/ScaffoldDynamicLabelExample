package cz.freego.tutorial.scaffolddynamiclabelexample.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.PreviewShowcase
import cz.freego.tutorial.scaffolddynamiclabelexample.utils.baseRoute

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("home", Icons.Default.Home, "Domů"),
        BottomNavItem("profile/1", Icons.Default.Person, "Profil"),
        BottomNavItem("favorites", Icons.Default.Favorite, "Oblíbené"),
    )

    NavigationBar {  // Používáme Material3 variantu
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            val isSelected = item.route.baseRoute() == currentRoute?.baseRoute()
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = isSelected,
                onClick = {
                    // tato podmínka zabrání zbytečnému znovuvytváření již vybrané a zobrazené sekce
                    if (isSelected.not()) {
                        navController.popBackStack(navController.graph.startDestinationId, false)
                        navController.navigate(item.route) {
                            popUpTo(item.route) { saveState = true } // zachová stav při návratu
                            launchSingleTop = true // zabrání zbytečnému znovuvytváření obrazovek
                            restoreState = true // zachová stav obrazovky při návratu
                        }
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)

@PreviewLightDark
@Composable
private fun BottomNavigationBarPreview() {
    PreviewShowcase {
        BottomNavigationBar(navController = rememberNavController())
    }
}