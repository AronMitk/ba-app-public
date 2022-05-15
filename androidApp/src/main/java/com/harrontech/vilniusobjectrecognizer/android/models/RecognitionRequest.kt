package com.harrontech.vilniusobjectrecognizer.android.models

data class RecognitionRequest(
    var images: ByteArray? = null,
    var deviceData: UserDataRequest? = null
)

data class UserDataRequest(
    var coordinates: Coordinates,
    var orientation: Orientation? = null,
    var clickCoordinates: ScreenClickCoordinates? = null,
    var cameraParameters: CameraParameters? = null
)

data class Coordinates(
    var latitude : Double,
    var longitude : Double
)

data class Orientation(
    var azimuth : Double,
    var pitch : Double,
    var roll : Double
)

data class CameraParameters(
    var verticalFOV : Double = 90.0,
    var horizontalFOV : Double = 90.0
)

data class ScreenClickCoordinates(
    var x : Double,
    var y : Double
)