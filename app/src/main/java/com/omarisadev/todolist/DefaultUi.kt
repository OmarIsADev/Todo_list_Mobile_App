package com.omarisadev.todolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TopBar(
    title: String,
    navController: NavController? = null,
    backButton: Boolean = false
) {
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
