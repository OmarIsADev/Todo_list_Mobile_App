package com.omarisadev.todolist.routes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.omarisadev.todolist.CreateTask
import com.omarisadev.todolist.Task
import com.omarisadev.todolist.TasksList
import com.omarisadev.todolist.TopBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeRoute(tasks: List<Task>, navController: NavController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = "Tasks"
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(CreateTask) }
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        Home (
            modifier = Modifier.padding(innerPadding),
            tasks = tasks,
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(modifier: Modifier = Modifier, tasks: List<Task>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),

        modifier = modifier
            .safeDrawingPadding()
            .navigationBarsPadding()
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (tasks.isNotEmpty()) {
            TasksList(tasks)
        } else {
            Text(
                text = "No tasks Yet!",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
