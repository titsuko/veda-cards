package com.example.vedaapplication.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vedaapplication.ui.navigation.BottomNavItem

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Collections,
        BottomNavItem.Settings
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppButtonBarPreview() {
    AppBottomBar(currentRoute = null, onNavigate = {})
}