package com.omarisadev.todolist

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omarisadev.todolist.routes.AddTaskRoute
import com.omarisadev.todolist.routes.HomeRoute
import com.omarisadev.todolist.ui.theme.TodoListTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoListTheme {
                val navController = rememberNavController()
                var tasks by remember { mutableStateOf(listOf<Task>()) }

                NavHost(
                    navController = navController,
                    startDestination = Home
                ) {

                    composable<Home> {
                        HomeRoute(
                            tasks = tasks,
                            navController = navController
                        )
                    }

                    composable<CreateTask> {
                        AddTaskRoute(
                            changeTasks = { tasks = tasks + it },
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Serializable
object Home

@Serializable
object CreateTask
