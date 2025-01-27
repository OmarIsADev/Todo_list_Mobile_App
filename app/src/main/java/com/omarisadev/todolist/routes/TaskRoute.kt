package com.omarisadev.todolist.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.omarisadev.todolist.Task
import com.omarisadev.todolist.TopBar

@Composable
fun TaskRoute(task: Task, navController: NavController) {
    Scaffold(
        topBar = {
            TopBar(
                task.category,
                navController = navController,
                backButton = true
            )
        }
    ) { innerPadding ->
        Task(modifier = Modifier.padding(innerPadding), title = task.title, description = task.description)
    }
}

@Composable
fun Task(title: String, description: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {

        Text(
            text = title
        )

        Text(
            text = description
        )
    }
}
