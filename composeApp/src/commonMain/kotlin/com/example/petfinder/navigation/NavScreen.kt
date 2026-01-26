package com.example.petfinder.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.components.resources.nav_favorites
import com.example.components.resources.nav_search
import com.example.components.resources.nav_settings
import com.example.components.resources.Res
import org.jetbrains.compose.resources.StringResource

enum class NavScreen(
    val route: String,
    val label: StringResource,
    val icon: ImageVector
) {
    Search(
        route = "search",
        label = Res.string.nav_search,
        icon = Icons.Default.Search
    ),
    Favorites(
        route = "favorites",
        label = Res.string.nav_favorites,
        icon = Icons.Default.Favorite
    ),
    Settings(
        route = "settings",
        label = Res.string.nav_settings,
        icon = Icons.Default.Settings
    );

    companion object {
        fun fromRoute(route: String?): NavScreen =
            entries.find { it.route == route } ?: Search
    }
}
