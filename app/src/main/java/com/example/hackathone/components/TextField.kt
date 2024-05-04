package com.example.hackathone.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val rememberedValue = remember { mutableStateOf(value) }
    val rememberedOnValueChange = remember { onValueChange }

    Box(modifier = modifier.background(Color.White)) {
        BasicTextField(
            value = rememberedValue.value,
            onValueChange = { newValue ->
                rememberedValue.value = newValue
                rememberedOnValueChange(newValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                },
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black,
                fontSize = 18.sp
            ),
            cursorBrush = SolidColor(Color.Black)
        )
    }
}

@Composable
fun NameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val rememberedValue = remember { mutableStateOf(value) }
    val rememberedOnValueChange = remember { onValueChange }
    val filtered = remember { mutableStateOf("") }
    val transformed = remember { mutableStateOf(TransformedText(AnnotatedString(""), OffsetMapping.Identity)) }
    val numberFilter = remember {
        VisualTransformation { text ->
            filtered.value = text.text.filter { it.isDigit() }
            transformed.value = TransformedText(AnnotatedString(filtered.value), OffsetMapping.Identity)

            transformed.value
        }
    }
    Box(modifier = modifier.background(Color.White)) {
        BasicTextField(
            value = rememberedValue.value,
            onValueChange = { newValue ->
                rememberedValue.value = newValue
                rememberedOnValueChange(newValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                },
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black,
                fontSize = 18.sp
            ),
            cursorBrush = SolidColor(Color.Black),
            visualTransformation = numberFilter
        )
    }
}