package com.example.monapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val time: String = "",
    val date: String = "",
    val dueDate: Long? = null,
    val isCompleted: Boolean = false,
    val categoryId: Long,
    val createdAt: Long = System.currentTimeMillis()
)
