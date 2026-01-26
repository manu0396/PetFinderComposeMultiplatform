package com.example.petfinder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import com.example.feature_login.ui.screens.LoginScreen
import com.example.petfinder.navigation.PetFinderNavGraph
import com.example.session.SessionManager
import com.example.session.domain.SessionState
import org.koin.compose.koinInject

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory()) }
            .crossfade(true)
            .build()
    }
    val sessionManager: SessionManager = koinInject()
    val sessionState by sessionManager.currentState.collectAsState(initial = SessionState.Loading)

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            when (sessionState) {
                is SessionState.Loading -> FullScreenLoading()
                is SessionState.NotAuthenticated -> LoginScreen()
                is SessionState.Authenticated -> MainScaffold()
            }
        }
    }
}

@Composable
fun MainScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == "search",
                    onClick = {
                        navController.navigate("search") {
                            val startDestination = navController.graph.findStartDestination()
                            popUpTo(startDestination.route ?: "search") {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Explorar") }
                )
                NavigationBarItem(
                    selected = currentRoute == "favorites",
                    onClick = {
                        navController.navigate("favorites") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favs") },
                    label = { Text("Favoritos") }
                )
            }
        }
    ) { paddingValues ->
        PetFinderNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(strokeWidth = 3.dp)
    }
}
