package com.example.todoapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDoItem (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val task : String,
    val dayOfWeek : String,
    val isCompleted : Boolean = false
)