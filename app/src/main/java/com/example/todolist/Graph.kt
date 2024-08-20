package com.example.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.data.TaskDatabase
import com.example.todolist.data.TaskRepository

object Graph {
    lateinit var database: TaskDatabase

    val taskRepository by lazy {
        TaskRepository(taskDao = database.taskDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, TaskDatabase::class.java, "tasklist.db").build()
    }
}