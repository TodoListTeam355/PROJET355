package com.example.monapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monapplication.data.entity.Category
import com.example.monapplication.ui.theme.MonApplicationTheme
import com.example.monapplication.utils.IconMapper
import com.example.monapplication.viewModel.TodoViewModel
import com.example.monapplication.viewModel.TodoViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesManager = PreferencesManager(this)

        setContent {
            val application = applicationContext as TodoApplication
            val viewModel: TodoViewModel = viewModel(
                factory = TodoViewModelFactory(application.repository)
            )

            val categories by viewModel.allCategories.collectAsState()
            val isDarkMode by preferencesManager.darkModeFlow.collectAsState(initial = false)

            LaunchedEffect(Unit) {
                if (categories.isEmpty()) {
                    viewModel.initializeDefaultCategories()
                }
            }

            MonApplicationTheme(darkTheme = isDarkMode) {
                MainScreen(
                    categories = categories,
                    isDarkMode = isDarkMode,
                    preferencesManager = preferencesManager,
                    onCategoryClick = { category ->
                        val intent = Intent(this@MainActivity, MainActivity3::class.java).apply {
                            putExtra("CATEGORY_ID", category.id)
                            putExtra("CATEGORY_NAME", category.name)
                            putExtra("CATEGORY_ICON", category.icon)
                            putExtra("CATEGORY_ICON_COLOR", category.iconColor)
                            putExtra("IS_DARK_THEME", isDarkMode)
                        }
                        startActivity(intent)
                    },
                    onAddClick = {
                        val intent = Intent(this@MainActivity, MainActivity2::class.java).apply {
                            putExtra("IS_DARK_THEME", isDarkMode)
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    categories: List<Category>,
    isDarkMode: Boolean,
    preferencesManager: PreferencesManager?,
    onCategoryClick: (Category) -> Unit,
    onAddClick: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (preferencesManager != null) {
                NavigationDrawerContent(
                    isDarkMode = isDarkMode,
                    preferencesManager = preferencesManager,
                    onCloseDrawer = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            } else {
                ModalDrawerSheet { Text("Drawer Preview") }
            }
        }
    ) {
        CategoriesScreen(
            categories = categories,
            onCategoryClick = onCategoryClick,
            onAddClick = onAddClick,
            onMenuClick = {
                scope.launch {
                    drawerState.open()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    onAddClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Lists",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = onMenuClick) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        if (categories.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    CategoryCard(
                        category = category,
                        onClick = { onCategoryClick(category) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    
    // On adapte la couleur de fond en mode sombre pour que le texte blanc soit lisible
    val containerColor = if (isDark) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        Color(category.backgroundColor)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(category.iconColor).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = IconMapper.getIconByName(category.icon),
                    contentDescription = category.name,
                    tint = Color(category.iconColor),
                    modifier = Modifier.size(24.dp)
                )
            }

            Column {
                Text(
                    text = category.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${category.taskCount} Tasks",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerContent(
    isDarkMode: Boolean,
    preferencesManager: PreferencesManager,
    onCloseDrawer: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val notificationsEnabled by preferencesManager.notificationsFlow.collectAsState(initial = true)
    val autoDeleteEnabled by preferencesManager.autoDeleteFlow.collectAsState(initial = false)

    ModalDrawerSheet(
        modifier = Modifier.width(300.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "TodoList",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Manage your tasks",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Settings Section
            Text(
                text = "SETTINGS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Dark Mode Toggle
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            preferencesManager.setDarkMode(!isDarkMode)
                        }
                    },
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Dark Mode",
                                fontSize = 16.sp
                            )
                            Text(
                                text = if (isDarkMode) "On" else "Off",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { enabled ->
                            scope.launch {
                                preferencesManager.setDarkMode(enabled)
                            }
                        }
                    )
                }
            }

            // Notifications Toggle
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            preferencesManager.setNotifications(!notificationsEnabled)
                        }
                    },
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Notifications",
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Task reminders",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { enabled ->
                            scope.launch {
                                preferencesManager.setNotifications(enabled)
                            }
                        }
                    )
                }
            }

            // Auto Delete Completed
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            preferencesManager.setAutoDelete(!autoDeleteEnabled)
                        }
                    },
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Auto Delete",
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Remove completed tasks",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Switch(
                        checked = autoDeleteEnabled,
                        onCheckedChange = { enabled ->
                            scope.launch {
                                preferencesManager.setAutoDelete(enabled)
                            }
                        }
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Other Options
            Text(
                text = "OTHER",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Statistics
            NavigationDrawerItem(
                icon = { Icon(Icons.Default.BarChart, contentDescription = null) },
                label = { Text("Statistics") },
                selected = false,
                onClick = {
                    onCloseDrawer()
                }
            )

            // Backup & Restore
            NavigationDrawerItem(
                icon = { Icon(Icons.Default.Backup, contentDescription = null) },
                label = { Text("Backup & Restore") },
                selected = false,
                onClick = {
                    onCloseDrawer()
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // About
            NavigationDrawerItem(
                icon = { Icon(Icons.Default.Info, contentDescription = null) },
                label = {
                    Column {
                        Text("About")
                        Text(
                            "Version 1.0.0",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                selected = false,
                onClick = {
                    onCloseDrawer()
                }
            )
        }
    }
}

// PREVIEWS
@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    MonApplicationTheme {
        CategoryCard(
            category = Category(
                id = 1,
                name = "All",
                icon = "list",
                iconColor = 0xFF6C63FF,
                backgroundColor = 0xFFFFFFFF,
                taskCount = 5
            ),
            onClick = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    val sampleCategories = listOf(
        Category(1, "All", "list", 0xFF6C63FF, 0xFFFFFFFF, 5),
        Category(2, "Work", "work", 0xFFFFA500, 0xFFFFF9F0, 3),
        Category(3, "Personal", "person", 0xFF4CAF50, 0xFFF1F8F1, 8),
        Category(4, "Shopping", "shopping_cart", 0xFFE91E63, 0xFFFDF2F5, 2)
    )
    MonApplicationTheme {
        MainScreen(
            categories = sampleCategories,
            isDarkMode = false,
            preferencesManager = null,
            onCategoryClick = {},
            onAddClick = {}
        )
    }
}
