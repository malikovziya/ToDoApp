package com.example.todoapp.domain

import com.example.todoapp.data.local.ToDoItem

interface ToDoRepository {
    suspend fun getTodos(): List<ToDoItem>

    suspend fun getTodoByWeekday(weekDay : String) : List<ToDoItem>

    suspend fun addTodo(todo: ToDoItem)

    suspend fun deleteTodo(todo: ToDoItem)

    suspend fun deleteTodoById(id : Int)

    suspend fun updateCompletionStatus(item: ToDoItem, status : Boolean)
}