package com.harrontech.vilniusobjectrecognizer.android.uiComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.harrontech.vilniusobjectrecognizer.android.models.ObjectInfo

@Composable
fun ObjectsList(objectsList : List<ObjectInfo>,
                onCardClick : (id: String)-> Unit){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(
            items = objectsList,
            itemContent = { it ->
                ObjectListCard(
                    ObjectListCardData(
                        id = it.id,
                        title = it.title,
                        type = it.type ?: "#none",
                        url = it.imageUrl ?: "https://media.istockphoto.com/vectors/photo-coming-soon-image-icon-vector-illustration-isolated-on-white-vector-id1193046540?k=20&m=1193046540&s=612x612&w=0&h=HQfBJLo1S0CJEsD4uk7m3EkR99gkICDdf0I52uAlk-8="
                    ),
                    onCardClick,
                    null
                )
            }
        )
    }
}

class SampleUserProvider : PreviewParameterProvider<List<String>>{
    override var values: Sequence<List<String>> = sequenceOf(listOf("AAA", "BBBB", "CCCC", "DDDD", "EEEE", "FFFF", "1111", "22222", "3333", "4444", "555"))
}

@Composable
fun NoResultObjectListView(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "No result")
    }
}