package com.example.monapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.monapplication.composables.ListsScreen
import com.example.monapplication.composables.TaskManagerApp
import com.example.monapplication.ui.theme.MonApplicationTheme

class MainActivity : ComponentActivity() {

    private var isDarkTheme by mutableStateOf(false)

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val systemInDarkTheme = isSystemInDarkTheme()
            isDarkTheme = systemInDarkTheme // Par défaut, on utilise le thème du système

            MonApplicationTheme(darkTheme = isDarkTheme) {
                AppNavigation(isDarkTheme = isDarkTheme, onThemeChange = { isDarkTheme = it })
            }
        }
    }
}

@Composable
fun AppNavigation (isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "home") {
        composable("home"){
            ListsScreen(
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange,
                onAddTaskClick = {
                    val intent = Intent(context, MainActivity2::class.java).apply {
                        putExtra("IS_DARK_THEME", isDarkTheme)
                    }
                    context.startActivity(intent)
                },
                onCategoryClick = { categoryName ->
                    val intent = Intent(context, MainActivity3::class.java).apply {
                        putExtra("CATEGORY_NAME", categoryName)
                        putExtra("IS_DARK_THEME", isDarkTheme)
                    }
                    context.startActivity(intent)
                }
            )
        }
        composable("tasks") {
            TaskManagerApp(isDarkTheme = isDarkTheme, onThemeChange = onThemeChange)
        }
    }
}
