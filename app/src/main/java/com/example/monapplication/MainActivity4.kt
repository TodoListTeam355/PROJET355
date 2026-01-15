package com.example.monapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monapplication.data.entity.Category
import com.example.monapplication.data.entity.Task
import com.example.monapplication.ui.theme.MonApplicationTheme
import com.example.monapplication.utils.IconMapper
import com.example.monapplication.viewModel.TodoViewModel
import com.example.monapplication.viewModel.TodoViewModelFactory
import java.time.*
import java.time.format.DateTimeFormatter

class MainActivity4 : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val taskId = intent.getLongExtra("TASK_ID", -1L)
        val taskTitle = intent.getStringExtra("TASK_TITLE") ?: ""
        val taskTime = intent.getStringExtra("TASK_TIME") ?: ""
        val taskDate = intent.getStringExtra("TASK_DATE") ?: ""
        val taskDueDate = intent.getLongExtra("TASK_DUE_DATE", System.currentTimeMillis())
        val taskCategoryId = intent.getLongExtra("TASK_CATEGORY_ID", 1L)
        val taskIsCompleted = intent.getBooleanExtra("TASK_IS_COMPLETED", false)

        setContent {
            val application = applicationContext as TodoApplication
            val viewModel: TodoViewModel = viewModel(
                factory = TodoViewModelFactory(application.repository)
            )

            val categories by viewModel.allCategories.collectAsState()

            MonApplicationTheme {
                EditTaskScreen(
                    taskId = taskId,
                    initialTitle = taskTitle,
                    initialTime = taskTime,
                    initialDate = taskDate,
                    initialDueDate = taskDueDate,
                    initialCategoryId = taskCategoryId,
                    initialIsCompleted = taskIsCompleted,
                    categories = categories,
                    onSaveTask = { task ->
                        viewModel.updateTask(task)
                        finish()
                    },
                    onCancel = { finish() }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    taskId: Long,
    initialTitle: String,
    initialTime: String,
    initialDate: String,
    initialDueDate: Long,
    initialCategoryId: Long,
    initialIsCompleted: Boolean,
    categories: List<Category>,
    onSaveTask: (Task) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(initialTitle) }

    val initialLocalDate = remember {
        Instant.ofEpochMilli(initialDueDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    val initialLocalTime = remember {
        if (initialTime.isNotEmpty()) {
            try {
                LocalTime.parse(initialTime, DateTimeFormatter.ofPattern("HH:mm"))
            } catch (e: Exception) {
                LocalTime.now()
            }
        } else {
            LocalTime.now()
        }
    }

    var selectedDate by remember { mutableStateOf(initialLocalDate) }
    var selectedTime by remember { mutableStateOf(initialLocalTime) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var isCategoryMenuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(categories) {
        if (selectedCategory == null && categories.isNotEmpty()) {
            selectedCategory = categories.find { it.id == initialCategoryId } ?: categories.first()
        }
    }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(
        initialHour = selectedTime.hour,
        initialMinute = selectedTime.minute
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Edit Task",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.weight(1f))
            IconButton(onClick = onCancel) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            if (title.isNotEmpty()) {
                Text(
                    text = "Task title",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    if (title.isEmpty()) {
                        Text(text = "Enter task title")
                    }
                },
                singleLine = false,
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { showDatePicker = true },
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.surface,
                border = ButtonDefaults.outlinedButtonBorder(enabled = true)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yy")),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { showTimePicker = true },
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.surface,
                border = ButtonDefaults.outlinedButtonBorder(enabled = true)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Time",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (categories.isNotEmpty()) {
            ExposedDropdownMenuBox(
                expanded = isCategoryMenuExpanded,
                onExpandedChange = { isCategoryMenuExpanded = !isCategoryMenuExpanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory?.name ?: "Select category",
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    leadingIcon = {
                        selectedCategory?.let { category ->
                            Icon(
                                imageVector = IconMapper.getIconByName(category.icon),
                                contentDescription = category.name,
                                tint = Color(category.iconColor)
                            )
                        }
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryMenuExpanded)
                    }
                )
                ExposedDropdownMenu(
                    expanded = isCategoryMenuExpanded,
                    onDismissRequest = { isCategoryMenuExpanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = IconMapper.getIconByName(category.icon),
                                        contentDescription = category.name,
                                        tint = Color(category.iconColor),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(category.name)
                                }
                            },
                            onClick = {
                                selectedCategory = category
                                isCategoryMenuExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && selectedCategory != null) {
                    val dateTime = LocalDateTime.of(selectedDate, selectedTime)
                    val timestamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

                    val task = Task(
                        id = taskId,
                        title = title,
                        time = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        date = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yy")),
                        dueDate = timestamp,
                        isCompleted = initialIsCompleted,
                        categoryId = selectedCategory!!.id
                    )
                    onSaveTask(task)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = title.isNotBlank() && selectedCategory != null
        ) {
            Text("Save Changes", fontSize = 18.sp)
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            onConfirm = {
                selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                showTimePicker = false
            },
            content = { TimePicker(state = timePickerState) }
        )
    }
}