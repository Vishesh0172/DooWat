package com.example.doowat.presentation.home.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

fun Modifier.shimmerEffect() = composed{

    var size by remember { mutableStateOf(IntSize.Zero) }

    val transition = rememberInfiniteTransition(label = "InfiniteTransition")

    val startOffset by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat() ,
        animationSpec = infiniteRepeatable(tween(1000)),
        label = "OffsetAnimation"
    )

    background(
        brush = Brush.linearGradient(
            listOf<Color>(
                Color(0xFF565454),
                Color(0xFF3A3939),
                Color(0xFF626161)
            ),
            start = Offset(x = startOffset, y = 0f),
            end = Offset(x = startOffset + size.width.toFloat(), y = size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }

}