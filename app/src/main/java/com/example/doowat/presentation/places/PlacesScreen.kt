package com.example.doowat.presentation.places


import android.net.Uri
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.doowat.data.places.PlaceDTO
import com.example.doowat.domain.model.Condition
import com.example.doowat.presentation.common.LoadingState
import com.example.doowat.presentation.home.components.shimmerEffect
import com.example.doowat.ui.theme.DooWatTheme
import com.example.doowat.ui.theme.getColorScheme

@Composable
fun PlacesScreen(
    modifier: Modifier = Modifier,
    state: PlacesState,
) {

    val loadingState = state.loadingState

    Scaffold(topBar = {PlacesTopBar()}, modifier = modifier) { innerPadding ->

        when(loadingState){

            is LoadingState.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(text = loadingState.message) }
            is LoadingState.Error -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(text = loadingState.message) }

            LoadingState.Success ->
                PlacesColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(top = 10.dp, start = 5.dp, end = 5.dp),
                    placesList = state.placesList
                )
        }
    }

}

@Preview
@Composable
fun PlacesPreview() {
    DooWatTheme(colorScheme = getColorScheme(Condition.ClearDay)) {

        val placeList = listOf<PlaceDTO>(
            PlaceDTO("ABC", id = ""),
            PlaceDTO("XYZ Zone", id = ""),
            PlaceDTO("Naruto", id = ""),
            PlaceDTO("SHT Park", id = ""),
            PlaceDTO("PQR", id = ""),
        )
        PlacesScreen(
            modifier = Modifier,
            state = PlacesState(placesList = placeList, loadingState = LoadingState.Success),
        )
    }
}

@Composable
fun PlacesColumn(modifier: Modifier = Modifier, placesList: List<PlaceDTO>) {

    LazyColumn(modifier = modifier.fillMaxSize()) {

        items(placesList){
            val imgUrl by it.imgUrl.collectAsState()
            PlaceCard(
                imgUrl = imgUrl,
                displayName = it.displayName,
                modifier = Modifier
                    .animateItem(fadeInSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                    .padding(vertical = 7.dp)

            )
        }
    }
}

@Composable
fun PlaceCard(modifier: Modifier = Modifier, imgUrl: Uri?, displayName: String) {

    var loading by remember { mutableStateOf(true) }

    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 20.dp,
            hoveredElevation = 50.dp,
            draggedElevation = 100.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {

            AsyncImage(
                model = imgUrl,
                contentDescription = null,
                onSuccess = { loading = false },
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.82f)
                    .run {
                        if (loading)
                            shimmerEffect()
                        else
                            this
                    },
                contentScale = ContentScale.Crop
            )
            Text(
                text = displayName,
                color = Color.DarkGray,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .blur(3.dp)
                    .padding(10.dp)
                    .offset(x = 2.dp, y=2.dp)
                    .alpha(0.8f)
            )

            Text(
                text = displayName,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(10.dp)

            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesTopBar(modifier: Modifier = Modifier) {

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        title = {Text("Explore", style = MaterialTheme.typography.titleMedium)}
    )

}