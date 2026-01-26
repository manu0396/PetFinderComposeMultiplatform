package com.example.petfinder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petfinder.ui.screens.AnimalSearchScreen
import com.example.petfinder.ui.screens.FavoritesScreen
import com.example.petfinder.ui.screens.SettingsScreen

@Composable
fun PetFinderNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.Search.route,
        modifier = modifier
    ) {
        composable(NavScreen.Search.route) { AnimalSearchScreen() }
        composable(NavScreen.Favorites.route) { FavoritesScreen() }
        composable(NavScreen.Settings.route) { SettingsScreen() }
    }
}
