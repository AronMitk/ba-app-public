package com.harrontech.vilniusobjectrecognizer.android.models

data class ObjectInfo(
    val id: String,
    val title: String,
    val description: String,
    val beforeYouGo: String,
    val imageUrl: String? = null,
    val area: String? = "",
    val type: String? = "",
    val coordinates: Coordinates? = null
)