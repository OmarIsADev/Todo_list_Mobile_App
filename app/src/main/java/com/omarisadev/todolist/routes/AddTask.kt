package com.omarisadev.todolist.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.omarisadev.todolist.Input
import com.omarisadev.todolist.Task
import com.omarisadev.todolist.TopBar

@Composable
fun AddTaskRoute(navController: NavController, changeTasks: (Task) -> Unit) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        topBar = {
            TopBar(
                title = "Add task",
                backButton = true,
                navController = navController
            )
        }
    ) { innerPadding ->
        AddTask(
            changeTasks = changeTasks,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AddTask(changeTasks: (Task) -> Unit, modifier: Modifier = Modifier) {
    var todo by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        Form(
            changeTasks = changeTasks,
            taskInput = todo,
            changeInput = { todo = it },
        )
    }
}

@Composable
fun Form(
    taskInput: String,
    changeInput: (String) -> Unit,
    changeTasks: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Input(label = "Task", value = taskInput, changeInput, changeTasks)

            ElevatedButton(
                onClick = {
                    if (taskInput.isNotEmpty()) {
                        changeTasks(Task(taskInput))
                    }
                    changeInput("")
                },
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier
                    .height(56.dp)
                    .align(Alignment.Bottom)
                    .padding(bottom = 2.dp)
            ) {
                Text("Add Task", textAlign = TextAlign.Center)
            }
        }
    }
}
