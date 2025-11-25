package com.example.monapplication.composables

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monapplication.MainActivity2
import com.example.monapplication.MainActivity3

data class TaskCategory(
    val icon: ImageVector,
    val name: String,
    val taskCount: Int,
    val iconColor: Color,
    val backgroundColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen() {
    val context = LocalContext.current
    val categories = listOf(
        TaskCategory(
            icon = Icons.Default.List,
            name = "All",
            taskCount = 23,
            iconColor = Color(0xFF5F7DF7),
            backgroundColor = Color(0xFFE8ECFF)
        ),
        TaskCategory(
            icon = Icons.Default.Work,
            name = "Work",
            taskCount = 14,
            iconColor = Color(0xFFFF9F43),
            backgroundColor = Color(0xFFFFF3E6)
        ),
        TaskCategory(
            icon = Icons.Default.Headphones,
            name = "Music",
            taskCount = 6,
            iconColor = Color(0xFFFF6B6B),
            backgroundColor = Color(0xFFFFE8E8)
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
            iconColor = Color(0xFF5F7DF7),
            backgroundColor = Color(0xFFE8ECFF)
        ),
        TaskCategory(
            icon = Icons.Default.Home,
            name = "Home",
            taskCount = 14,
            iconColor = Color(0xFFFF6B6B),
            backgroundColor = Color(0xFFFFE8E8)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lists",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C3E50)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color(0xFF2C3E50)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        floatingActionButton = {

            FloatingActionButton(

                onClick = {


                    val intent = Intent(context, MainActivity2::class.java)
                    context.startActivity(intent)



                     },
                containerColor = Color(0xFF5F7DF7),
                contentColor = Color.White,
                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(99.dp))
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
                .background(Color.White)
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
                        val intent = Intent(context, MainActivity3::class.java)
                        intent.putExtra("CATEGORY_NAME", category.name)
                        context.startActivity(intent)
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
            .shadow(30.dp,RoundedCornerShape(99.dp),false)
            .fillMaxWidth()
            .height(130.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
                    color = Color(0xFF2C3E50)
                )
                Text(
                    text = "${category.taskCount} Tasks",
                    fontSize = 13.sp,
                    color = Color(0xFF95A5A6)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListsScreenPreview() {
    MaterialTheme {
        ListsScreen()
    }
}
