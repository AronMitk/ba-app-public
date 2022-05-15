package com.harrontech.vilniusobjectrecognizer.android

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harrontech.vilniusobjectrecognizer.android.helpers.RequestsHelper
import com.harrontech.vilniusobjectrecognizer.android.models.ObjectInfo
import com.harrontech.vilniusobjectrecognizer.android.uiComponents.ObjectListCard
import com.harrontech.vilniusobjectrecognizer.android.uiComponents.ObjectListCardData
import kotlinx.coroutines.runBlocking

class BugReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var storage = RequestsHelper()

        var objectId = intent.getStringExtra("ID").toString()
        var obj = runBlocking {
            storage.getObjectByID(objectId)
        }

        Log.i("OBJECT_ID", objectId)

        setContent {
            BugReportView(obj!!) { finish() }
        }
    }
}

@Composable
fun BugReportView(objectInfo: ObjectInfo, close : () -> Unit){
    val (description, setDescription) = remember {
        mutableStateOf("")
    }

    val (title, setTitle) = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(18.dp)
    ) {
        ObjectListCard(item = ObjectListCardData(
            objectInfo.id,
            objectInfo.title,
            objectInfo.type!!,
            objectInfo.imageUrl!!
        ),
        onObjectInfoOpen = null,
        onBugReportOpen = null)

        Text(text = "Title:")
        TextField(
            value = title,
            onValueChange = setTitle,
            Modifier.fillMaxWidth()
        )

        Text(text = "Description:")
        TextField(
            value = description,
            onValueChange = setDescription,
            Modifier.fillMaxWidth().height(100.dp)
        )

        Button(
            onClick = { close() },
            Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit")
        }

    }
}