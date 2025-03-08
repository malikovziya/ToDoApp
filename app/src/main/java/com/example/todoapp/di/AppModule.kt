package com.example.todoapp.di

import android.app.Application
import androidx.room.Room
import com.example.todoapp.data.local.ToDoDao
import com.example.todoapp.data.local.ToDoDatabase
import com.example.todoapp.data.remote.repository.ToDoRepositoryImpl
import com.example.todoapp.domain.ToDoRepository
import com.example.todoapp.presentation.TodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Provide Database instance
    single {
        Room.databaseBuilder(get<Application>(), ToDoDatabase::class.java, "todo_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    // Provide DAO instance
    single<ToDoDao> { get<ToDoDatabase>().toDoDao() }

    // Provide Repository instance
    single<ToDoRepository> { ToDoRepositoryImpl(get()) }

    // Provide ViewModel instance
    viewModel { TodoViewModel(get()) }
}
