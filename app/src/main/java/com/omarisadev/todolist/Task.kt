package com.omarisadev.todolist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Task(val title: String, val description: String = "") {
    @RequiresApi(Build.VERSION_CODES.O)
    val createdAt: LocalDate = LocalDate.now()
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
