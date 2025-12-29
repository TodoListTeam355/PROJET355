package com.example.monapplication.data.repository


import com.example.monapplication.data.dao.CategoryDao
import com.example.monapplication.data.dao.TaskDao
import com.example.monapplication.data.entity.Category
import com.example.monapplication.data.entity.Task
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
) {
    // Categories
    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()

    suspend fun insertCategory(category: Category): Long {
        return categoryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    suspend fun getCategoryById(categoryId: Long): Category? {
        return categoryDao.getCategoryById(categoryId)
    }

    // Tasks
    fun getTasksByCategory(categoryId: Long): Flow<List<Task>> {
        return taskDao.getTasksByCategory(categoryId)
    }

    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Task): Long {
        val id = taskDao.insertTask(task)
        categoryDao.updateAllTaskCounts()
        return id
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
        categoryDao.updateAllTaskCounts()
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
        categoryDao.updateAllTaskCounts()
    }

    suspend fun toggleTaskCompletion(taskId: Long, isCompleted: Boolean) {
        taskDao.updateTaskCompletion(taskId, isCompleted)
        categoryDao.updateAllTaskCounts()
    }

    suspend fun initializeDefaultCategories() {
        val defaultCategories = listOf(
            Category(
                id = 1,
                name = "All",
                icon = "list",
                iconColor = 0xFF6C63FF,
                backgroundColor = 0xFFE8E6FF,
                taskCount = 0
            ),
            Category(
                id = 2,
                name = "Work",
                icon = "work",
                iconColor = 0xFF7C8089,
                backgroundColor = 0xFFE8E9EB,
                taskCount = 0
            ),
            Category(
                id = 3,
                name = "Music",
                icon = "headphones",
                iconColor = 0xFF8B4855,
                backgroundColor = 0xFFFFE4E8,
                taskCount = 0
            ),
            Category(
                id = 4,
                name = "Travel",
                icon = "flight",
                iconColor = 0xFF4ECB71,
                backgroundColor = 0xFFE6F9ED,
                taskCount = 0
            ),
            Category(
                id = 5,
                name = "Study",
                icon = "school",
                iconColor = 0xFF6C63FF,
                backgroundColor = 0xFFE8E6FF,
                taskCount = 0
            ),
            Category(
                id = 6,
                name = "Home",
                icon = "home",
                iconColor = 0xFFB74444,
                backgroundColor = 0xFFFFE4E4,
                taskCount = 0
            ),
            Category(
                id = 7,
                name = "Art",
                icon = "palette",
                iconColor = 0xFFAB7DF7,
                backgroundColor = 0xFFF3E8FF,
                taskCount = 0
            ),
            Category(
                id = 8,
                name = "Shopping",
                icon = "shoppingcart",
                iconColor = 0xFF4ECBDC,
                backgroundColor = 0xFFE6F9FC,
                taskCount = 0
            )
        )

        defaultCategories.forEach { category ->
            insertCategory(category)
        }
    }
}
