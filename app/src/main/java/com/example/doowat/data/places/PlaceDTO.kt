package com.example.doowat.data.places

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow

data class PlaceDTO(
    val displayName: String,
    val imgUrl: MutableStateFlow<Uri?> = MutableStateFlow(null),
    val id: String
)
