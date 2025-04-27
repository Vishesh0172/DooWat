package com.example.doowat.data.places


import android.net.Uri
import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchByTextRequest
import com.google.android.libraries.places.api.net.kotlin.awaitFetchResolvedPhotoUri
import kotlinx.coroutines.tasks.await

interface PlacesRepository{
    suspend fun getPlacePhoto(id: String):  Uri?
    suspend fun getPlaces(query: String,latitude: Double, longitude: Double,updateImgUrl: (PlaceDTO) -> Unit): Either<PlacesApiError, List<PlaceDTO>>
}


class PlacesRepositoryImpl(
    private val placesApi: PlacesClient
) : PlacesRepository{

    private val fieldList = listOf<Place.Field>(Place.Field.ID, Place.Field.DISPLAY_NAME)


    override suspend fun getPlacePhoto(id: String): Uri? {

        val fields = listOf<Place.Field>(Place.Field.PHOTO_METADATAS)
        val request = FetchPlaceRequest.newInstance(id, fields)
        val place = placesApi.fetchPlace(request).await().place
        val metadata = place.photoMetadatas

        metadata?.let {
            val response = placesApi.awaitFetchResolvedPhotoUri(metadata[0])
            val uri = response.uri
            return uri
        }?: return null

    }

    override suspend fun getPlaces(query: String, latitude: Double, longitude: Double, updateImgUrl:(PlaceDTO) -> Unit): Either<PlacesApiError, List<PlaceDTO>> {

        val searchCenter = LatLng(latitude, longitude)

        val searchRequest = SearchByTextRequest.builder(query, fieldList)
            .setLocationBias(CircularBounds.newInstance(searchCenter, 1000.0))
            .build()

        val response = placesApi.searchByText(searchRequest)
        val places = response.await().places



        return either {
            ensure(response.isSuccessful){ PlacesApiError.NetworkError("Network Error Occurred") }
            ensure(places.isNotEmpty()){ PlacesApiError.EmptyResult("Couldn't Fetch Places") }

            val placeList = places.map {
                PlaceDTO(id = it.id ?: "", displayName = it.displayName?: "")
            }

            placeList.forEach {
                updateImgUrl(it)
            }

            placeList
        }
    }





}

