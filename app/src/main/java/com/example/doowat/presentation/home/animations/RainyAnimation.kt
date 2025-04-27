package com.example.doowat.presentation.home.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.doowat.R
import com.example.doowat.presentation.home.components.CloudShape

@Composable
fun RainyAnimation(modifier: Modifier = Modifier, startAnimation: Boolean) {

    var visible by remember { mutableStateOf(false) }


    LaunchedEffect(startAnimation) {
        if (startAnimation == true)
            visible = true
    }

    Box(modifier = modifier.fillMaxSize()) {


        Image(
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
            painter = painterResource(R.drawable.rainy_background),
            contentScale = ContentScale.Crop
        )


        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(900, easing = LinearOutSlowInEasing)
                )
            ) {
                CloudShape(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.25f),
                    curveSize = 72.dp,
                    color = Color.DarkGray
                )

            }
        }
    }

}