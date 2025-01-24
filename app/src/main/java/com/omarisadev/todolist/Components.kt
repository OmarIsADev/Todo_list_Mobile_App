package com.omarisadev.todolist

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction

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
