package com.ravi.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddTask(viewModel: TaskViewModel){

    val newTask = viewModel.newTask

    Dialog(
        onDismissRequest = {
            viewModel.toggleDialog(false)
            viewModel.onTaskChange("")
        }
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add Task"
                )
                OutlinedTextField(
                    value = newTask,
                    onValueChange = { viewModel.onTaskChange(it) },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Button(
                    onClick = { viewModel.addTask() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = "Add"
                    )
                }
            }
        }
    }

}
