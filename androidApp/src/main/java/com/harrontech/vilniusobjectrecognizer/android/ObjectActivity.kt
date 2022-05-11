package com.harrontech.vilniusobjectrecognizer.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.harrontech.vilniusobjectrecognizer.android.models.ObjectInfo
import com.harrontech.vilniusobjectrecognizer.android.viewModels.ObjectInfoViewModel

class ObjectActivity : AppCompatActivity(){

    private val objectInfoViewModel by viewModels<ObjectInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            objectInfoWindow(
                objectInfoViewModel,
                onBackClick = {
                    finish()
                }
            )
        }

        objectInfoViewModel.setValue(intent.getStringExtra("ID").toString())
    }
}

@Composable
fun objectInfoWindow(objectInfoViewModel: ObjectInfoViewModel, onBackClick : () -> Unit){
    val objectInfo : ObjectInfo? by objectInfoViewModel.objectInfo.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        if(objectInfo != null){
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                val painter =
                    if(objectInfo!!.imageUrl == null)
                        painterResource(id = R.drawable.no_image)
                    else
                        rememberImagePainter(objectInfo!!.imageUrl)

                Image(
                    painter = painter,
                    contentDescription = "Main image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.previous),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(32.dp)
                        .clickable { onBackClick() }
                )
            }
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                ) {
                    Text(
                        text = objectInfo!!.title,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = objectInfo!!.type!!,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp, 0.dp, 0.dp)
                    )
                    Text(
                        text = objectInfo!!.description,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp, 0.dp, 0.dp)
                    )
                }
            }

            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 16.dp)
                ) {
                    Text(
                        text = "Before you go",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        text = objectInfo!!.beforeYouGo,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp, 0.dp, 0.dp)
                    )
                }

            }
        }
    }
}