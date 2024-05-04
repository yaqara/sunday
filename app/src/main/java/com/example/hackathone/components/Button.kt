package com.example.hackathone.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp


@Composable
fun CircleButton(
    func:  () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = func,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = CircleShape,
        content = content
    )
}

@Composable
fun MainButton(
    func:  () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = func,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RectangleShape,
        content = content
    )
}