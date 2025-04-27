package com.example.doowat.presentation.home.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.apply


@Composable
fun CloudShape(modifier: Modifier = Modifier, curveSize: Dp, color: Color) {

    val curveSize = with(LocalDensity.current){curveSize.toPx()}

    Box(
        modifier = modifier
            .size(300.dp)
            .drawBehind {

                val path = Path().apply {
                    addRoundRect(
                        RoundRect(
                            top = 0f,
                            right = size.width,
                            bottom = size.height - curveSize,
                            left = 0f
                        )
                    )

                    moveTo(0f, size.height - curveSize)
                    quadraticTo(
                        size.width/6f, size.height, size.width * 0.33f, size.height - curveSize
                    )
                    close()

                    moveTo(size.width * 0.33f, size.height - curveSize)
                    quadraticTo(
                        size.width/2f, size.height, size.width*0.67f, size.height - curveSize
                    )
                    close()

                    moveTo(size.width * 0.67f, size.height - curveSize)
                    quadraticTo(
                        size.width/1.2f, size.height, size.width, size.height - curveSize
                    )
                    close()
                }

                drawPath(path = path, color = color)
        }
    )

}