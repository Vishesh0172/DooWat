package com.example.doowat.presentation.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.doowat.domain.model.Condition
import com.example.doowat.presentation.common.LoadingState
import com.example.doowat.presentation.home.animations.ClearAnimation
import com.example.doowat.presentation.home.animations.FoggyAnimation
import com.example.doowat.presentation.home.animations.OvercastAnimation
import com.example.doowat.presentation.home.animations.PartlyCloudyAnimation
import com.example.doowat.presentation.home.animations.RainyAnimation
import com.example.doowat.presentation.home.animations.SnowAnimation
import com.example.doowat.ui.theme.DooWatTheme
import com.example.doowat.ui.theme.getColorScheme
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    state: HomeState,
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit,
    onExploreClicked:() -> Unit,
    changeTheme:() -> Unit
) {


    val context = LocalContext.current
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->

        when(permissionGranted) {
            true -> {
                getLocation(context){
                    Log.d("LocationFetched", it.toString())
                    onEvent(HomeEvent.LocationFetched( it ))
                }

            }
            false -> {
                onEvent(HomeEvent.LocationFetched(null) )
            }
        }
    }

    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(state.locationPermission, state.loadingState) {
        if (state.locationPermission == false){
            checkLocationPermission(context){ granted ->
                if (granted)
                    getLocation(context){ onEvent(HomeEvent.LocationFetched( it )) }
                else
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

            }
        }

        if (state.loadingState is LoadingState.Success) {
            changeTheme()
            startAnimation = true
        }
    }


    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){

        val loadingState = state.loadingState
        when(loadingState){
            is LoadingState.Loading ->
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            is LoadingState.Error -> Text(state.loadingState.message)

            LoadingState.Success -> {

                BackgroundAnimation(startAnimation = startAnimation, condition = state.conditionType)

                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    AnimatedWeatherDetailsBox(
                        modifier = Modifier.padding(top = 36.dp),
                        tempC = state.tempC.toString(),
                        tempF = state.tempF.toString(),
                        country = state.country,
                        city = state.city,
                        condition = state.condition,
                        startAnimation = startAnimation
                    )

                    AnimatedTextBox(state.activityText, startAnimation = startAnimation, modifier = Modifier)

                    AnimatedButton(
                        modifier = Modifier,
                        startAnimation = startAnimation,
                        onExploreClicked = {
                            startAnimation = false
                            onExploreClicked()
                        }
                    )

                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomePreview() {

    val homeState = HomeState(
        loadingState = LoadingState.Success,
        tempC = 30.2f,
        region = "Chandigarh",
        isDay = true,
        conditionType = Condition.LightSnowyDark
        
    )

    val colorScheme = getColorScheme(homeState.conditionType)
    DooWatTheme(colorScheme = colorScheme) {
        HomeScreen(
            state = homeState,
            onEvent = {},
            onExploreClicked = {},
            changeTheme = {}
        )
    }
}


@Composable
fun AnimatedWeatherDetailsBox(modifier: Modifier = Modifier, tempC: String, tempF: String, country: String, city: String, condition: String, startAnimation: Boolean) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(startAnimation) {
        delay(500)
        visible = true
    }


    AnimatedVisibility(visible = visible, enter = fadeIn(tween(800))) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
            Text("$tempC C", style = MaterialTheme.typography.displayLarge)
            Text("$tempF F", style = MaterialTheme.typography.titleSmall)
            Text(condition, style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                Text("$city, ", style = MaterialTheme.typography.titleSmall)
                Text(country, style = MaterialTheme.typography.titleSmall)
            }
        }
    }

}


@Composable
fun AnimatedButton(modifier: Modifier = Modifier, startAnimation: Boolean, onExploreClicked: () -> Unit) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(startAnimation) {
        delay(500)
        visible = true
    }

    AnimatedVisibility(modifier = modifier, visible = visible, enter = fadeIn(tween(1000)) + slideInVertically(initialOffsetY = {it}, animationSpec = tween(800))) {
        ElevatedButton(onClick = { onExploreClicked() } ) {
            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
                Text("Explore", style = MaterialTheme.typography.titleSmall)
            }
        }

    }

}

@Composable
fun AnimatedTextBox(text: String, modifier: Modifier = Modifier, startAnimation: Boolean) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(startAnimation) {
        delay(500)
        visible = true
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(tween(1000))
                + slideInVertically(initialOffsetY = {-it}, animationSpec = tween(800))
    ) {
        Box(contentAlignment = Alignment.Center){
            Text(text, style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Center)
        }

    }

}


@Composable
fun BackgroundAnimation(condition: Condition, startAnimation: Boolean) {

    when(condition){
        Condition.ClearDark -> ClearAnimation(startAnimation = startAnimation, isDay = false)
        Condition.ClearDay ->  ClearAnimation(startAnimation = startAnimation, isDay = true)

        Condition.OvercastDark -> OvercastAnimation(startAnimation = startAnimation, isDay = false)
        Condition.OvercastDay ->  OvercastAnimation(startAnimation = startAnimation, isDay = true)

        Condition.PartlyCloudyDark -> PartlyCloudyAnimation(startAnimation = startAnimation, isDay = false)
        Condition.PartlyCloudyDay ->  PartlyCloudyAnimation(startAnimation = startAnimation, isDay = true)

        Condition.LightSnowyDay -> SnowAnimation(startAnimation = startAnimation, isDay = true)
        Condition.LightSnowyDark -> SnowAnimation(startAnimation = startAnimation, isDay = false)

        Condition.HeavySnowyDay -> SnowAnimation(startAnimation = startAnimation, isDay = true)
        Condition.HeavySnowyDark -> SnowAnimation(startAnimation = startAnimation, isDay = false)

        Condition.FoggyDark -> FoggyAnimation()
        Condition.FoggyDay -> FoggyAnimation()

        Condition.HeavyRainyDark -> RainyAnimation(startAnimation = startAnimation)
        Condition.HeavyRainyDay -> RainyAnimation(startAnimation = startAnimation)

        Condition.LightRainyDark -> RainyAnimation(startAnimation = startAnimation)
        Condition.LightRainyDay -> RainyAnimation(startAnimation = startAnimation)

        Condition.SleetOrIcyDark -> SnowAnimation(startAnimation = startAnimation, isDay = false)
        Condition.SleetOrIcyDay -> SnowAnimation(startAnimation = startAnimation, isDay = true)

        Condition.ThunderstormDark -> RainyAnimation(startAnimation = startAnimation)
        Condition.ThunderstormDay -> RainyAnimation(startAnimation = startAnimation)

        else -> ClearAnimation(startAnimation = startAnimation, isDay = true)
    }
}

private fun checkLocationPermission(context: Context, permissionGranted:(Boolean) -> Unit) {

    val permissionIsGranted = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if (permissionIsGranted){
        permissionGranted(true)
    }else{
       permissionGranted(false)
    }
}

@SuppressLint("MissingPermission")
private fun getLocation(context: Context, locationFetched:(Location?) -> Unit){

    val locationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationTask = locationClient.getCurrentLocation(CurrentLocationRequest.Builder().build(), null)
    locationTask.addOnSuccessListener{
        locationFetched(it)
        Log.d("LocationSuccess", it.toString())
    }.addOnFailureListener{
        Log.d("LocationFailure", it.toString())
    }


}




