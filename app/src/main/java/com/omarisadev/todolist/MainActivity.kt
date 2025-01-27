package com.omarisadev.todolist

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.omarisadev.todolist.routes.AddTaskRoute
import com.omarisadev.todolist.routes.HomeRoute
import com.omarisadev.todolist.routes.TaskRoute
import com.omarisadev.todolist.ui.theme.TodoListTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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
                    startDestination = Home,
                    popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }
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

                    composable<TaskPage>(
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                            )
                        },

                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                            )
                        }
                    ) {
                        val args = it.toRoute<TaskPage>()
                        val task = Json.decodeFromString<Task>(args.task)

                        TaskRoute(
                            task = task,
                            navController = navController,
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

@Serializable
data class TaskPage(
    val task: String
)
