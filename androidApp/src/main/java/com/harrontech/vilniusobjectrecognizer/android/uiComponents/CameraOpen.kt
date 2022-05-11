package com.harrontech.vilniusobjectrecognizer.android.uiComponents

import android.annotation.SuppressLint
import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
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

    var images = ArrayList<ByteArray>()

    var locationHelper = LocationHelper(context) {
        location = it
    }

    var imageService = ImageService(context, 4, 250) {
        it ?: return@ImageService
        images.add(it)
        Logger.e("IMAGES", images.size.toString())
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
                onDrag = { Logger.e("POSITION", it.position.toString()) },
                onDragStart = {
                    locationHelper.getLastLocation()

                    objectInfo = recognize(storage, location, images.map {
                        Image(image = it)
                    })

                    images.clear()

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

fun recognize(storage: RequestsHelper, location: Location?, images: List<Image>?): ObjectListCardData? {
    if (location != null) {
        var id = storage.recognizeObject(
            UserDataRequest(
                coordinates = Coordinates(
                    location.latitude,
                    location.longitude
                ),
                images = images
            )
        )!!
        var result = storage.getObjectByID(id)

        return ObjectListCardData(
            id = result!!.id,
            title = result.title,
            type = result.type!!,
            url = result.imageUrl!!
        )
    }

    return null
}