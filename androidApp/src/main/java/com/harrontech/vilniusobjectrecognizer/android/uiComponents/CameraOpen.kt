package com.harrontech.vilniusobjectrecognizer.android.uiComponents

import android.annotation.SuppressLint
import android.location.Location
import androidx.camera.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.harrontech.vilniusobjectrecognizer.android.helpers.*
import com.harrontech.vilniusobjectrecognizer.android.models.*
import kotlinx.coroutines.*


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("RestrictedApi", "CoroutineCreationDuringComposition")
@Composable
fun CameraOpen(onBugReportOpen: (id: String) -> Unit, onObjectInfoOpen: (id: String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var storage = RequestsHelper()


    var location: Location? by remember { mutableStateOf(null) }

    var locationHelper = LocationHelper(context) {
        location = it
    }

    var image: ByteArray? by remember { mutableStateOf(null) }

    var imageService = ImageService(context) {
        image = it
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    val coroutineScope = rememberCoroutineScope()
    var objectInfo: ObjectListCardData? by remember { mutableStateOf(null) }

    coroutineScope.launch {
        bottomSheetScaffoldState.bottomSheetState.collapse()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                Box(
                    Modifier
                        .fillMaxWidth()
                ) {
                    if (objectInfo != null) {
                        ObjectListCard(
                            objectInfo!!,
                            onObjectInfoOpen,
                            onBugReportOpen
                        )
                    } else {
                        ErrorCard(text = "Object not found")
                    }
                }
            },
            sheetPeekHeight = 0.dp,
            sheetBackgroundColor = Color.Transparent,
            sheetElevation = 0.dp
        ) {

            CameraPreview(
                context = context,
                lifecycleOwner = lifecycleOwner,
                onDragStart = {
                    locationHelper.getLastLocation()
                    objectInfo = recognize(storage, location, image)

                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                },
                onDragEnd = {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.drawerState.isOpen)
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                },
                setImageCapture = { x, y -> imageService.takePictures(x, y)}
            )
        }
    }

}

fun recognize(storage: RequestsHelper, location: Location?, image: ByteArray?): ObjectListCardData? {
    if (location != null) {
        var id = storage.recognizeObject(
            RecognitionRequest(
                images = image,
                deviceData = UserDataRequest(
                    coordinates = Coordinates(
                        location.latitude,
                        location.longitude
                    )
                )
            )
        )!!
        var result = runBlocking {
            storage.getObjectByID(id)
        }

        return ObjectListCardData(
            id = result!!.id,
            title = result.title,
            type = result.type!!,
            url = result.imageUrl!!
        )
    }

    return null
}