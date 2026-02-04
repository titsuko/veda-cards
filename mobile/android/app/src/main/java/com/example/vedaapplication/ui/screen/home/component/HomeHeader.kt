package com.example.vedaapplication.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeHeader(
    title: String? = null,
    icon: ImageVector? = null,
    onSearchClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Icon(
                imageVector = icon ?: Icons.AutoMirrored.Outlined.LibraryBooks,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = Color.Black
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = title ?: "Welcome",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircleActionButton(
                icon = Icons.Outlined.Search,
                contentDescription = "Search",
                onClick = onSearchClick
            )
        }
    }
}

@Composable
fun CircleActionButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 2.dp,
        modifier = Modifier.size(44.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeHeaderPreview() {
    HomeHeader()
}