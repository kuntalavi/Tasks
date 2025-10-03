package com.ravi.tasks.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.ravi.tasks.DatabaseProvider
import com.ravi.tasks.data.TaskRepository
import com.ravi.tasks.ui.viewmodel.TaskViewModel
import com.ravi.tasks.TaskViewModelFactory
import com.ravi.tasks.ui.component.AddTask
import com.ravi.tasks.ui.theme.Purple10
import com.ravi.tasks.ui.theme.TasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = DatabaseProvider.getDatabase(applicationContext).taskDao()
        val repo = TaskRepository(dao)
        val factory = TaskViewModelFactory(repo)
        val viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            TasksTheme {
                App(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    val showDialog = viewModel.showDialog

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "TASKS")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.toggleDialog(true) },
                shape = CircleShape,
                containerColor = Purple10,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, "Add")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            LazyColumn(
                reverseLayout = true
            ) {
                items(tasks) { task ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = task.title,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .weight(1f)
                            )
                            IconButton(
                                onClick = { viewModel.deleteTask(task) }
                            ) {
                                Icon(imageVector = Icons.Default.Delete, "Delete")
                            }
                        }
                    }
                }
            }
        }
        if (showDialog) {
            AddTask(viewModel)
        }
    }
}