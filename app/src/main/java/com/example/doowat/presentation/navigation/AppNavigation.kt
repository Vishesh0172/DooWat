package com.example.doowat.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.doowat.domain.model.Condition
import com.example.doowat.presentation.home.HomeScreen
import com.example.doowat.presentation.home.HomeViewModel
import com.example.doowat.presentation.places.PlacesScreen
import com.example.doowat.presentation.places.PlacesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier, changeTheme:(Condition) -> Unit) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "HomeRoute"){

        composable(
            route = "HomeRoute",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ){
            val viewModel = koinViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            HomeScreen(
                state = state,
                onExploreClicked = {
                    navController.navigate(NavRoute.PlacesArgs(
                        isDay = state.isDay,
                        conditionCode = state.conditionCode,
                        latitude = state.latitude,
                        longitude = state.longitude
                    ))
                },
                onEvent = viewModel::onEvent,
                changeTheme = { changeTheme(state.conditionType) }
            )
        }

        composable< NavRoute.PlacesArgs>(
            enterTransition = {
                val previousRoute = initialState.destination.route ?: ""
                when(previousRoute){
                    "HomeRoute" -> slideInHorizontally(initialOffsetX = {it}, animationSpec = tween(800))
                    else -> null
                }
            },
            exitTransition = {
                val nextRoute = targetState.destination.route ?: ""
                when(nextRoute){
                    "HomeRoute" -> slideOutHorizontally(targetOffsetX = {it}, animationSpec = tween(800))
                    else -> null
                }
            }
        ){
            val viewModel = koinViewModel<PlacesViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            PlacesScreen(
                state = state
            )
        }

    }

}

