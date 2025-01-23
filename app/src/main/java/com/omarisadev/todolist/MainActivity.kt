package com.omarisadev.todolist

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omarisadev.todolist.ui.theme.TodoListTheme
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
                        Scaffold(
                            modifier = Modifier
                                .fillMaxSize(),
                            topBar = {
                                TopBar(
                                    title = "Tasks"
                                )
                            },
                            bottomBar = {
                                FloatingActionButton(
                                    onClick = { navController.navigate(CreateTask) },
                                    modifier = Modifier.padding(32.dp)
                                ) {
                                    Icon(
                                        Icons.Rounded.Add,
                                        contentDescription = null
                                    )
                                }
                            }
                        ) { innerPadding ->
                            HomeRoute (
                                modifier = Modifier.padding(innerPadding),
                                tasks = tasks,
                            )
                        }
                    }

                    composable<CreateTask> {
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
                            CreateTaskRoute(
                                changeTasks = { tasks = tasks + it },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeRoute(modifier: Modifier = Modifier, tasks: List<Task>) {
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

@Composable
fun CreateTaskRoute(changeTasks: (Task) -> Unit, modifier: Modifier = Modifier) {
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
fun Title() {
    Text(
        text = "Todo List",
        fontSize = 24.sp
    )
}

@Composable
fun Form(
    taskInput: String,
    changeInput: (String) -> Unit,
    changeTasks: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Title()

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

class Task(val title: String, val description: String = "") {
    @RequiresApi(Build.VERSION_CODES.O)
    val createdAt = LocalDate.now()
    var isDone: Boolean = false

    fun toggleIsDone() {
        isDone = !isDone
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskCompose(task: Task) {
    var isDone by remember { mutableStateOf(task.isDone) }
    var isOpen by remember { mutableStateOf(false) }

    Card {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        onCheckedChange = {
                            isDone = it
                            task.toggleIsDone()
                        },
                        checked = isDone,
                    )

                    Text(
                        task.title,
                        fontSize = 20.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(task.createdAt.format(DateTimeFormatter.ofPattern("dd/MM")))
                    IconButton(
                        onClick = {
                            isOpen = !isOpen
                        }
                    ) {
                        if (task.description != "") {
                            Icon(
                                Icons.Rounded.KeyboardArrowDown,
                                contentDescription = null,
                                modifier = if (isOpen) {
                                    Modifier.rotate(180F)
                                } else {
                                    Modifier
                                }
                            )
                        }
                    }
                }
            }

            if (isOpen) {
                Text(
                    task.description,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksList(tasks: List<Task>) {
    LazyColumn(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks) { task ->
            TaskCompose(task)
        }
    }
}

@Composable
fun Input(
    label: String,
    value: String,
    changeInput: (String) -> Unit,
    changeTasks: (Task) -> Unit
) {
    OutlinedTextField(
        label = { Text(label) },
        value = value,
        onValueChange = changeInput,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            if (value.isNotEmpty()) {
                changeTasks(Task(value))
            }
            changeInput("")
        }),
        shape = MaterialTheme.shapes.extraSmall,
    )
}

@Composable
fun TopBar(title: String, navController: NavController? = null, backButton: Boolean = false) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Row(
            horizontalArrangement =
            if (backButton)
                Arrangement.SpaceBetween
            else
                Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (backButton)
                IconButton(
                    onClick = { navController?.popBackStack() },
                ) {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }

            Text(
                text = title,
                fontSize = 24.sp,
            )

            if (backButton)
                Text(
                    text = "",
                    modifier = Modifier.size(48.dp)
                )
        }
        HorizontalDivider()
    }
}


//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true, device = Devices.PIXEL, showSystemUi = true)
//@Composable
//fun AppPreview() {
//    TodoListTheme {
//        TodoListApp()
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TaskComposePreview() {
    TaskCompose(Task("test"))
}

@Serializable
object Home

@Serializable
object CreateTask
