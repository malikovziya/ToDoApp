package com.example.todoapp.data.remote.repository

import com.example.todoapp.data.local.ToDoDao
import com.example.todoapp.data.local.ToDoItem
import com.example.todoapp.domain.ToDoRepository

class ToDoRepositoryImpl(private val todoDao: ToDoDao) : ToDoRepository {
    override suspend fun getTodos(): List<ToDoItem> {
        return todoDao.getAllTodos()
    }

    override suspend fun getTodoByWeekday(weekDay : String) : List<ToDoItem> {
        return todoDao.getTodosByDay(weekDay)
    }

    override suspend fun addTodo(todo: ToDoItem) {
        todoDao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: ToDoItem) {
        todoDao.removeTodo(todo)
    }

    override suspend fun deleteTodoById(id : Int){
        todoDao.removeTodoById(id)
    }

    override suspend fun updateCompletionStatus(item: ToDoItem, status: Boolean) {
        todoDao.updateCompletionStatus(item.id, status)
    }
}