package com.example.doowat

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.doowat.ui.theme.DooWatTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.doowat.presentation.navigation.AppNavigation
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            //var condition: Condition by remember {  mutableStateOf(Condition.ClearDay) }
            val viewModel = getViewModel<AppViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            DooWatTheme(colorScheme = state.colorScheme) {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        changeTheme = {
                            Log.d("MainActivity", "changeTheme in mainActivity called with condition: $it")
                            viewModel.changeTheme(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DooWatTheme {
        Greeting("Android")
    }
}

