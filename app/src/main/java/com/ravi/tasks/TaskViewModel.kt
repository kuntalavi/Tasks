package com.ravi.tasks

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(context: Context): ViewModel() {

    private val dao = DatabaseProvider.getDatabase(context).taskDao()

    val tasks: StateFlow<List<Task>> = dao.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    var newTask by mutableStateOf("")
        private set

    var showDialog by mutableStateOf(false)
        private set

    fun addTask() {
        if (newTask.isNotBlank()) {
            viewModelScope.launch {
                dao.upsertTask(Task(title = newTask))
                newTask = ""
                showDialog = false
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
           dao.deleteTask(task)
        }
    }


    fun onTaskChange(value:String){
        newTask = value
    }

    fun toggleDialog(show: Boolean){
        showDialog = show
    }

}