package com.example.petfinder


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petfinder.ui.screens.AnimalSearchScreen
import com.example.petfinder.ui.screens.FavoritesScreen

@Composable
fun App() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == "search",
                    onClick = { navController.navigate("search") },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Explorar") }
                )
                NavigationBarItem(
                    selected = currentRoute == "favorites",
                    onClick = { navController.navigate("favorites") },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favs") },
                    label = { Text("Favoritos") }
                )
            }
        }
    ) { padding ->
        NavHost(navController, startDestination = "search", Modifier.padding(padding)) {
            // Cambiamos AnimalSearchScreen por AnimalListScreen para que se use
            composable("search") { AnimalSearchScreen() }
            composable("favorites") { FavoritesScreen() }
        }
    }
}
