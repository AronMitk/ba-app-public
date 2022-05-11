package com.harrontech.vilniusobjectrecognizer.android

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.*
import com.harrontech.vilniusobjectrecognizer.android.uiComponents.CameraOpen


@RequiresApi(Build.VERSION_CODES.CUPCAKE)
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalPagerApi::class, androidx.compose.material.ExperimentalMaterialApi::class)
    @SuppressLint("PermissionLaunchedDuringComposition", "RestrictedApi")
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityView(context = applicationContext,
                onBugReportOpen = { id -> onBugReportOpen(id)},
                onObjectInfoOpen = { id -> onObjectInfoOpen(id) })
        }
    }

    fun onObjectInfoOpen(id: String) {
        Log.e("ACTIVITY-STARTER", "OBJECT")
        startActivity(
            Intent(applicationContext, ObjectActivity::class.java).apply {
                this.putExtra("ID", id)
            }
        )
    }

    fun onBugReportOpen(id: String) {
        Log.e("ACTIVITY-STARTER", "BUG REPORT")
        startActivity(
            Intent(applicationContext, BugReportActivity::class.java).apply {
                this.putExtra("ID", id)
            }
        )
    }
}

@OptIn(ExperimentalPagerApi::class,ExperimentalPermissionsApi::class,ExperimentalMaterialApi::class)
@Composable
fun MainActivityView(context: Context,
                     onBugReportOpen: (id: String) -> Unit,
                     onObjectInfoOpen: (id: String) -> Unit){
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )

    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION)
    )

    val pagerState = rememberPagerState(initialPage = 0)


    HorizontalPager(
        state = pagerState,
        count = 2
    ) { index ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (index == 0) {
                objectListFragmentView(onCardClick = {
                    onObjectInfoOpen(it)
                })
            } else {
                when (cameraPermissionState.status) {
                    // If the camera permission is granted, then show screen with the feature enabled
                    PermissionStatus.Granted -> {
                        Surface(color= MaterialTheme.colors.background) {
                            CameraOpen(onBugReportOpen, onObjectInfoOpen)
                        }
                    }
                    is PermissionStatus.Denied -> {
                        Column {
                            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                                Text("Request permission")
                            }
                        }
                    }
                }
            }
        }
    }
}