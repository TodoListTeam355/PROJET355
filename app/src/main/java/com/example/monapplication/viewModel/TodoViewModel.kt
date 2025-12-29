package com.example.monapplication.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monapplication.data.entity.Category
import com.example.monapplication.data.entity.Task
import com.example.monapplication.data.repository.TodoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    val allCategories: StateFlow<List<Category>> =
        repository.allCategories.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val allTasks: StateFlow<List<Task>> =
        repository.allTasks.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _selectedCategoryId = MutableStateFlow<Long?>(null)
    val selectedCategoryId: StateFlow<Long?> = _selectedCategoryId

    @OptIn(ExperimentalCoroutinesApi::class)
    val tasksForSelectedCategory: StateFlow<List<Task>> =
        _selectedCategoryId.flatMapLatest { categoryId ->
            if (categoryId == null || categoryId == 1L) {
                repository.allTasks
            } else {
                repository.getTasksByCategory(categoryId)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun selectCategory(categoryId: Long) {
        _selectedCategoryId.value = categoryId
    }

    // Task operations
    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun toggleTaskCompletion(taskId: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.toggleTaskCompletion(taskId, isCompleted)
        }
    }

    // Category operations
    fun insertCategory(category: Category) {
        viewModelScope.launch {
            repository.insertCategory(category)
        }
    }

    fun initializeDefaultCategories() {
        viewModelScope.launch {
            repository.initializeDefaultCategories()
        }
    }
}