package com.example.hackathone.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun CText(
    text : String
) {
    Text(
        text = text,
        fontSize = 30.sp
    )
}