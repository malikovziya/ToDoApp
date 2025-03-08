package com.example.todoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.ToDoItem
import com.example.todoapp.domain.ToDoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val todoRepository: ToDoRepository): ViewModel() {
    val allTodos = MutableStateFlow<List<ToDoItem>>(emptyList())

    val latestTodo = MutableStateFlow<ToDoItem?>(null)

    fun insert(todo: ToDoItem){
        viewModelScope.launch {
            todoRepository.addTodo(todo)
            getAllTodos()
        }
    }

    fun delete(todo: ToDoItem){
        viewModelScope.launch {
            todoRepository.deleteTodo(todo)
            getAllTodos()
        }
    }

    fun deleteById(id: Int){
        viewModelScope.launch {
            todoRepository.deleteTodoById(id)
            getAllTodos()
        }
    }

    fun getAllTodos(){
        viewModelScope.launch {
            val result = todoRepository.getTodos()
            allTodos.value = result
            latestTodo.value = result.firstOrNull()
        }
    }

    fun getFilteredTodos(day: String?) {
        if (day != null) {
            viewModelScope.launch {
                val result = todoRepository.getTodoByWeekday(day)
                allTodos.value = result
            }
        } else {
            getAllTodos()
        }
    }

    fun updateCompletedStatus(item: ToDoItem, isCompleted: Boolean) {
        viewModelScope.launch {
            val updatedItem = item.copy(isCompleted = isCompleted)
            todoRepository.updateCompletionStatus(updatedItem, isCompleted)
            getAllTodos()
        }
    }
}