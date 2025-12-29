package com.example.monapplication

import android.app.Application
import com.example.monapplication.data.dataBase.AppDatabase
import com.example.monapplication.data.repository.TodoRepository


class TodoApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy {
        TodoRepository(database.taskDao(), database.categoryDao())
    }
}