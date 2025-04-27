package com.example.doowat.presentation.home.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.doowat.R
import com.example.doowat.presentation.home.components.CloudShape
import com.example.doowat.presentation.home.components.MoonShape

@Composable
fun ClearAnimation(modifier: Modifier = Modifier, startAnimation: Boolean, isDay: Boolean) {

    var visible by remember { mutableStateOf(false) }


    LaunchedEffect(startAnimation) {
        visible = startAnimation == true
    }

    Box(modifier = modifier.fillMaxSize()) {

        if (!isDay){
            Image(
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                painter = painterResource(R.drawable.night_background),
                contentScale = ContentScale.Crop
            )
        }


        if (isDay) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(900, easing = LinearOutSlowInEasing)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(500, easing = LinearOutSlowInEasing)
                    )
                ) {
                    CloudShape(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.25f),
                        curveSize = 72.dp,
                        color = Color.White
                    )

                }
            }
        }



        if (isDay) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(900, easing = LinearOutSlowInEasing)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = {it},
                        animationSpec = tween(500, easing = LinearOutSlowInEasing)
                    )

                ) {
                    SunComposable()

                }
            }
        }else{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {

                AnimatedVisibility(
                    label = "MoonAnimation",
                    visible = visible,
                    enter = slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(900, easing = LinearOutSlowInEasing)
                    )
                ) {
                    MoonShape(modifier = Modifier.size(150.dp).padding(20.dp))
                }
            }
        }
    }

}

@Composable
fun SunComposable(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
            .background(Color.Yellow)

    )
}
