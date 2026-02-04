package com.example.vedaapplication.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(Screen.Home.route, "Главная", Icons.AutoMirrored.Filled.LibraryBooks)
    data object Collections : BottomNavItem(Screen.Collection.route, "Коллекции", Icons.Filled.Description)
    data object Settings : BottomNavItem(Screen.Settings.route, "Настройки", Icons.Filled.Settings)
}
