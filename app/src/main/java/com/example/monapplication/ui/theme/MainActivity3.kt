
package com.example.monapplication.ui.theme

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Tasks"
        setContent {
            MonApplicationTheme {
                TaskDetailsScreen(
                    categoryName = categoryName,
                    onBackClick = { finish() },
                    onAddTaskClick = { /* TODO */ }
                )
            }
        }
    }
}

// Définition de la classe de données pour une tâche
data class Task(
    val id: String,
    val title: String,
    val time: String,
    val date: String,
    val isCompleted: Boolean,
    val category: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onAddTaskClick: () -> Unit
) {
    val tasks = remember {
        listOf(
            Task(
                id = "1",
                title = "Call Max",
                time = "20:15",
                date = "April 29",
                isCompleted = false,
                category = "All"
            ),
            Task(
                id = "2",
                title = "Practice piano",
                time = "16:00",
                date = "Today",
                isCompleted = false,
                category = "All"
            ),
            Task(
                id = "3",
                title = "Learn Spanish",
                time = "17:00",
                date = "Today",
                isCompleted = false,
                category = "All"
            ),
            Task(
                id = "4",
                title = "Finalize presentation",
                time = "",
                date = "Done",
                isCompleted = true,
                category = "All"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5F7DF7)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick,
                containerColor = Color(0xFF5F7DF7),
                contentColor = Color.White,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(paddingValues)

        ) {
            // Header avec catégorie
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF5F7DF7))
                    .padding(horizontal = 24.dp, vertical = 32.dp)

            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = categoryName,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = categoryName,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${tasks.size} Tasks",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            // Liste des tâches
            LazyColumn(
                modifier = Modifier.fillMaxSize() .clip(RoundedCornerShape(199.dp)),
                contentPadding = PaddingValues(16.dp)
            ) {
                // Tâches en retard
                item {
                    Text(
                        text = "Late",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF95A5A6),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(tasks.filter { it.date == "April 29" }) { task ->
                    TaskItem(task = task)
                }

                // Tâches du jour
                item {
                    Text(
                        text = "Today",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF95A5A6),
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)
                    )
                }

                items(tasks.filter { it.date == "Today" }) { task ->
                    TaskItem(task = task)
                }

                // Tâches terminées
                item {
                    Text(
                        text = "Done",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF95A5A6),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(tasks.filter { it.isCompleted }) { task ->
                    TaskItem(task = task)
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    var isChecked by remember { mutableStateOf(task.isCompleted) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isChecked) Color(0xFF95A5A6) else Color(0xFF2C3E50)
                )
                if (task.time.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.time,
                        fontSize = 13.sp,
                        color = Color(0xFFFF6B6B)
                    )
                }
                if (task.date.isNotEmpty() && task.date != "Done") {
                    Text(
                        text = task.date,
                        fontSize = 12.sp,
                        color = Color(0xFF95A5A6)
                    )
                }
            }

            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF5F7DF7),
                    uncheckedColor = Color(0xFFDDE2E5)
                )
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MonApplicationTheme {
        TaskDetailsScreen(
            categoryName = "Toutes les tâches",
            onBackClick = {},
            onAddTaskClick = {}
        )
    }
}
