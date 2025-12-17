package com.example.monapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TaskCategory(
    val icon: ImageVector,
    val name: String,
    val taskCount: Int,
    val iconColor: Color,
    val backgroundColor: Color
)

@Composable
fun getTaskCategories(): List<TaskCategory> {
    return listOf(
        TaskCategory(
            icon = Icons.Default.List,
            name = "All",
            taskCount = 23,
            iconColor = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        ),
        TaskCategory(
            icon = Icons.Default.Work,
            name = "Work",
            taskCount = 14,
            iconColor = MaterialTheme.colorScheme.secondary,
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        TaskCategory(
            icon = Icons.Default.Headphones,
            name = "Music",
            taskCount = 6,
            iconColor = MaterialTheme.colorScheme.tertiary,
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        TaskCategory(
            icon = Icons.Default.Flight,
            name = "Travel",
            taskCount = 1,
            iconColor = Color(0xFF4ECB71),
            backgroundColor = Color(0xFFE6F9ED)
        ),
        TaskCategory(
            icon = Icons.Default.School,
            name = "Study",
            taskCount = 2,
            iconColor = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        ),
        TaskCategory(
            icon = Icons.Default.Home,
            name = "Home",
            taskCount = 14,
            iconColor = MaterialTheme.colorScheme.error,
            backgroundColor = MaterialTheme.colorScheme.errorContainer
        ),
        TaskCategory(
            icon = Icons.Default.Palette,
            name = "Art",
            taskCount = 0,
            iconColor = Color(0xFFAB7DF7),
            backgroundColor = Color(0xFFF3E8FF)
        ),
        TaskCategory(
            icon = Icons.Default.ShoppingCart,
            name = "Shopping",
            taskCount = 0,
            iconColor = Color(0xFF4ECBDC),
            backgroundColor = Color(0xFFE6F9FC)
        )
    )
}
