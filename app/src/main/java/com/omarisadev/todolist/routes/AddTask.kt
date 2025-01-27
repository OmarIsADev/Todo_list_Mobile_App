package com.omarisadev.todolist.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
    Column(modifier = modifier.padding(16.dp)) {
        Form(
            changeTasks = changeTasks,
        )
    }
}

@Composable
fun Form(
    changeTasks: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Input(label = "Title", value = title, changeInput = { title = it })
            Input(
                label = "Description",
                value = description,
                changeInput = { description = it },
                isLast = true
            )

            ElevatedButton(
                onClick = {
                    if (title.isNotEmpty()) {
                        changeTasks(
                            Task(
                                title = title,
                                description = description,
                            )
                        )
                    }
                    title = ""
                    description = ""
                },
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier
                    .height(56.dp)
                    .padding(bottom = 2.dp)
                    .fillMaxWidth()
            ) {
                Text("Add Task", textAlign = TextAlign.Center)
            }
        }
    }
}
