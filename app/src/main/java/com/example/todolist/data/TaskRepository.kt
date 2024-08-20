package com.example.todolist.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun addATask(task: Task){
        taskDao.addTask(task)
    }

    fun getTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    fun getATaskById(id: Long): Flow<Task> = taskDao.getATaskById(id)

    suspend fun updateATask(task: Task){
        taskDao.updateATask(task)
    }

    suspend fun deleteATask(task: Task){
        taskDao.deleteATask(task)
    }

}