package com.example.monapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monapplication.data.entity.Task
import com.example.monapplication.ui.theme.MonApplicationTheme
import com.example.monapplication.viewModel.TodoViewModel
import com.example.monapplication.viewModel.TodoViewModelFactory

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.graphics.Color
import com.example.monapplication.utils.IconMapper

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Tasks"
        val categoryId = intent.getLongExtra("CATEGORY_ID", 1L)
        val categoryIcon = intent.getStringExtra("CATEGORY_ICON") ?: "list"
        val categoryIconColor = intent.getLongExtra("CATEGORY_ICON_COLOR", 0xFF6C63FF)

        setContent {
            val application = applicationContext as TodoApplication
            val viewModel: TodoViewModel = viewModel(
                factory = TodoViewModelFactory(application.repository)
            )

            LaunchedEffect(categoryId) {
                viewModel.selectCategory(categoryId)
            }

            val useDarkTheme = if (intent.hasExtra("IS_DARK_THEME")) {
                intent.getBooleanExtra("IS_DARK_THEME", false)
            } else {
                isSystemInDarkTheme()
            }

            MonApplicationTheme(darkTheme = useDarkTheme) {
                TaskDetailsScreen(
                    categoryName = categoryName,
                    categoryIcon = categoryIcon,
                    categoryIconColor = categoryIconColor,
                    viewModel = viewModel,
                    onBackClick = { finish() },
                    onAddTaskClick = {
                        val intent = Intent(this@MainActivity3, MainActivity2::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    categoryName: String,
    categoryIcon: String,
    categoryIconColor: Long,
    viewModel: TodoViewModel,
    onBackClick: () -> Unit,
    onAddTaskClick: () -> Unit
) {
    val tasks by viewModel.tasksForSelectedCategory.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val lateTasks = remember(tasks) {
        tasks.filter { task ->
            !task.isCompleted && task.dueDate != null &&
                    task.dueDate < System.currentTimeMillis() - 86400000
        }
    }

    val todayTasks = remember(tasks) {
        val today = System.currentTimeMillis()
        val startOfDay = today - (today % 86400000)
        val endOfDay = startOfDay + 86400000

        tasks.filter { task ->
            !task.isCompleted && task.dueDate != null &&
                    task.dueDate in startOfDay..endOfDay
        }
    }

    val completedTasks = remember(tasks) {
        tasks.filter { it.isCompleted }
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
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Delete all completed") },
                            onClick = {
                                completedTasks.forEach { task ->
                                    viewModel.deleteTask(task)
                                }
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Mark all as completed") },
                            onClick = {
                                tasks.filter { !it.isCompleted }.forEach { task ->
                                    viewModel.toggleTaskCompletion(task.id, true)
                                }
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete all tasks") },
                            onClick = {
                                showDeleteDialog = true
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.DeleteForever,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
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
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            // Header avec catégorie et ICÔNE
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(categoryIconColor).copy(alpha = 0.3f),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = IconMapper.getIconByName(categoryIcon), // ← Icône dynamique
                            contentDescription = categoryName,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = categoryName,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = if(tasks.size>0)"${tasks.size} Tasks " else "",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                }
            }

            if (tasks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No tasks yet",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap + to add a new task",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    if (lateTasks.isNotEmpty()) {
                        item {
                            Text(
                                text = "Late",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        items(items = lateTasks, key = { it.id }) { task ->
                            TaskItem(
                                task = task,
                                onCheckedChange = { isChecked ->
                                    viewModel.toggleTaskCompletion(task.id, isChecked)
                                },
                                onTaskLongClick = { selectedTask = task }
                            )
                        }
                    }

                    if (todayTasks.isNotEmpty()) {
                        item {
                            Text(
                                text = "Today",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        items(items = todayTasks, key = { it.id }) { task ->
                            TaskItem(
                                task = task,
                                onCheckedChange = { isChecked ->
                                    viewModel.toggleTaskCompletion(task.id, isChecked)
                                },
                                onTaskLongClick = { selectedTask = task }
                            )
                        }
                    }

                    if (completedTasks.isNotEmpty()) {
                        item {
                            Text(
                                text = "Done",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        items(items = completedTasks, key = { it.id }) { task ->
                            TaskItem(
                                task = task,
                                onCheckedChange = { isChecked ->
                                    viewModel.toggleTaskCompletion(task.id, isChecked)
                                },
                                onTaskLongClick = { selectedTask = task }
                            )
                        }
                    }
                }
            }
        }
    }

    // Dialog de confirmation pour supprimer toutes les tâches
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            icon = {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = { Text("Delete all tasks?") },
            text = { Text("This action cannot be undone. All ${tasks.size} tasks will be permanently deleted.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        tasks.forEach { task ->
                            viewModel.deleteTask(task)
                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Menu contextuel pour une tâche spécifique
    selectedTask?.let { task ->
        TaskContextMenu(
            task = task,
            onDismiss = { selectedTask = null },
            onDelete = {
                viewModel.deleteTask(task)
                selectedTask = null
            },
            onToggleComplete = {
                viewModel.toggleTaskCompletion(task.id, !task.isCompleted)
                selectedTask = null
            }
        )
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    onTaskLongClick: () -> Unit
) {
    var isChecked by remember(task.isCompleted) { mutableStateOf(task.isCompleted) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .combinedClickable(
                onClick = { },
                onLongClick = { onTaskLongClick() }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isChecked) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    textDecoration = if (isChecked) TextDecoration.LineThrough else null
                )
                if (task.time.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = task.time,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                if (task.date.isNotEmpty()) {
                    Text(
                        text = task.date,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Checkbox(
                checked = isChecked,
                onCheckedChange = { checked ->
                    isChecked = checked
                    onCheckedChange(checked)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}


@Composable
fun TaskContextMenu(
    task: Task,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onToggleComplete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(task.title) },
        text = {
            Column {
                Text("What would you like to do with this task?")
            }
        },
        confirmButton = {
            Column {
                TextButton(
                    onClick = onToggleComplete,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        if (task.isCompleted) Icons.Default.RadioButtonUnchecked
                        else Icons.Default.CheckCircle,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (task.isCompleted) "Mark as incomplete" else "Mark as complete")
                }
                TextButton(
                    onClick = onDelete,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete task")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}