package com.ravi.tasks.data

import com.ravi.tasks.data.local.Task
import com.ravi.tasks.data.local.TaskDao
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    val tasks: Flow<List<Task>> = dao.getAllTasks()

    suspend fun addTask(task: Task) = dao.upsertTask(task)
    suspend fun removeTask(task: Task) = dao.deleteTask(task)
}