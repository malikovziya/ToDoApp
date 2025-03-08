package com.example.todoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(toDoData: ToDoItem)

    @Query("SELECT * FROM todo_table ORDER BY id DESC")
    suspend fun getAllTodos(): List<ToDoItem>

    @Query("SELECT * FROM todo_table WHERE dayOfWeek = :day ORDER BY id DESC")
    suspend fun getTodosByDay(day: String): List<ToDoItem>

    @Delete
    suspend fun removeTodo(todoData: ToDoItem)

    @Query("DELETE FROM todo_table WHERE id = :todoId")
    suspend fun removeTodoById(todoId: Int)

    @Query("UPDATE todo_table SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateCompletionStatus(id: Int, isCompleted: Boolean)
}