package com.example.monapplication.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

object IconMapper {
    fun getIconByName(iconName: String): ImageVector {
        return when (iconName.lowercase()) {
            "list" -> Icons.Default.List
            "work" -> Icons.Default.Work
            "headphones", "music" -> Icons.Default.Headphones
            "flight", "travel" -> Icons.Default.Flight
            "school", "study" -> Icons.Default.School
            "home" -> Icons.Default.Home
            "palette", "art" -> Icons.Default.Palette
            "shoppingcart", "shopping" -> Icons.Default.ShoppingCart
            else -> Icons.Default.Category
        }
    }
}
