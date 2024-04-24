package com.example.themlexplorer.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpace(
    space: Int
) {
    Spacer(modifier = Modifier.height(space.dp))
}

@Composable
fun HorizontalSpace(
    space: Int
) {
    Spacer(modifier = Modifier.width(space.dp))
}