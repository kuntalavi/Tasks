package com.ravi.tasks.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravi.tasks.data.TaskRepository
import com.ravi.tasks.data.local.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repo: TaskRepository): ViewModel() {

    val tasks: StateFlow<List<Task>> = repo.tasks.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(),
        emptyList()
    )

    var newTask by mutableStateOf("")
        private set

    var showDialog by mutableStateOf(false)
        private set

    fun addTask() {
        if (newTask.isNotBlank()) {
            viewModelScope.launch {
                repo.addTask(Task(title = newTask))
                newTask = ""
                showDialog = false
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
           repo.removeTask(task)
        }
    }


    fun onTaskChange(value:String){
        newTask = value
    }

    fun toggleDialog(show: Boolean){
        showDialog = show
    }

}