package com.harrontech.vilniusobjectrecognizer.android.models

import kotlinx.serialization.Serializable

//class ObjectInfo {
//    lateinit var id: String
//    lateinit var externalId: String
//    lateinit var title: String
//    lateinit var description: String
//    lateinit var beforeYouGo: String
//    lateinit var imageUrl: String
//    lateinit var type: String
//    lateinit var coordinates: Coordinates
//}
//
@Serializable
data class ObjectInfo(
    var id: String,
    var externalId: String,
    var title: String,
    var description: String,
    var beforeYouGo: String,
    var imageUrl: String,
    var type: String,
    var coordinates: Coordinates
)