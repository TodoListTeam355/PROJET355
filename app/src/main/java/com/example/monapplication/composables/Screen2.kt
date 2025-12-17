package com.example.monapplication.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monapplication.ui.theme.MonApplicationTheme
import kotlinx.coroutines.launch

data class TaskCategory(
    val icon: ImageVector,
    val name: String,
    val taskCount: Int,
    val iconColor: Color,
    val backgroundColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onAddTaskClick: () -> Unit,
    onCategoryClick: (String) -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val primaryContainerColor = MaterialTheme.colorScheme.primaryContainer
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val secondaryContainerColor = MaterialTheme.colorScheme.secondaryContainer
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val tertiaryContainerColor = MaterialTheme.colorScheme.tertiaryContainer
    val errorColor = MaterialTheme.colorScheme.error
    val errorContainerColor = MaterialTheme.colorScheme.errorContainer

    val categories = remember {
        listOf(
            TaskCategory(
                icon = Icons.Default.List,
                name = "All",
                taskCount = 23,
                iconColor = primaryColor,
                backgroundColor = primaryContainerColor
            ),
            TaskCategory(
                icon = Icons.Default.Work,
                name = "Work",
                taskCount = 14,
                iconColor = secondaryColor,
                backgroundColor = secondaryContainerColor
            ),
            TaskCategory(
                icon = Icons.Default.Headphones,
                name = "Music",
                taskCount = 6,
                iconColor = tertiaryColor,
                backgroundColor = tertiaryContainerColor
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
                iconColor = primaryColor,
                backgroundColor = primaryContainerColor
            ),
            TaskCategory(
                icon = Icons.Default.Home,
                name = "Home",
                taskCount = 14,
                iconColor = errorColor,
                backgroundColor = errorContainerColor
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

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange,
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                 MediumTopAppBar(

                    title = { Text("Lists") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),

                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAddTaskClick,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(99.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(paddingValues)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(123.dp),
                    contentPadding = PaddingValues(36.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(categories) { category ->
                        CategoryCard(category = category) {
                            onCategoryClick(category.name)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: TaskCategory, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon container
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = category.backgroundColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = category.name,
                    tint = category.iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Category info
            Column {
                Text(
                    text = category.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${category.taskCount} Tasks",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListsScreenPreview() {
    MonApplicationTheme {
        ListsScreen(isDarkTheme = false, onThemeChange = {}, onAddTaskClick = {}, onCategoryClick = {})
    }
}
