package com.example.doowat.presentation.home.components


import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun MoonShape(modifier: Modifier = Modifier) {

    val space = with(LocalDensity.current){30.dp.toPx()}

    Box(
        modifier = modifier
            .drawBehind {

                val width = size.width
                val height = size.height

                // Main circle
                val outerPath = Path().apply {
                    addOval(Rect(Offset(0f, 0f), Size(width, height)))
                }

                // Subtracting circle to create crescent shape
                val innerPath = Path().apply {
                    addOval(
                        Rect(
                            Offset(width * -0.2f, 0f), // Shifted to the right
                            Size(width, height - space)
                        )
                    )
                }

                // Combine paths to create crescent moon
                val moonPath = Path.combine(
                    PathOperation.Difference, // Subtract inner from outer
                    outerPath,
                    innerPath
                )


                drawPath(moonPath, Color.Yellow)
            }
    )

}