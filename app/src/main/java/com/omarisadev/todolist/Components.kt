package com.omarisadev.todolist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@Composable
fun Input(
    label: String,
    value: String,
    changeInput: (String) -> Unit,
    isLast: Boolean = false
) {
    OutlinedTextField(
        label = { Text(label) },
        value = value,
        onValueChange = changeInput,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction =
            if (isLast) {
                ImeAction.Done
            } else {
                ImeAction.Next
            }
        ),
        shape = MaterialTheme.shapes.extraSmall,
        modifier = Modifier.fillMaxWidth()
    )
}
