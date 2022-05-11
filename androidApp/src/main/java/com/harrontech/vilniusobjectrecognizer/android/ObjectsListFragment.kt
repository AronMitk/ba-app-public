package com.harrontech.vilniusobjectrecognizer.android

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.harrontech.vilniusobjectrecognizer.android.helpers.DataFetchingHelper
import com.harrontech.vilniusobjectrecognizer.android.helpers.RequestsHelper
import com.harrontech.vilniusobjectrecognizer.android.uiComponents.NoResultObjectListView
import com.harrontech.vilniusobjectrecognizer.android.uiComponents.ObjectsList
import com.harrontech.vilniusobjectrecognizer.android.models.ObjectInfo

class ObjectsListFragmentViewModel : ViewModel(){
    val objectsList : MutableLiveData<MutableList<ObjectInfo>> = MutableLiveData(mutableListOf())
    val searchText : MutableLiveData<String> = MutableLiveData("")

    var storage  = RequestsHelper()

    fun onSearchTextChanged(value: String){
        searchText.value = value

        objectsList.value?.clear()

        if(value.isEmpty()){
            try {
                var result = storage.getObjectsList()
                if (result != null) objectsList.value!!.addAll(result)
            } catch (e : Exception){
                objectsList.value = mutableListOf()
            }
        } else {
            objectsList.value!!.addAll(storage.getFilteredObjectsList(value)!!)
        }
    }
}

@Composable
fun objectListFragmentView(objectsListFragmentViewModel: ObjectsListFragmentViewModel = viewModel(),
                           onCardClick: (id: String) -> Unit){

    val text by objectsListFragmentViewModel.searchText.observeAsState("")
    objectsListFragmentViewModel.onSearchTextChanged(text)

    Column {
        TextField(value = text,
            onValueChange = {
                objectsListFragmentViewModel.onSearchTextChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = { Text("Search...") }
        )
        if(!objectsListFragmentViewModel.objectsList.value.isNullOrEmpty()){
            ObjectsList(
                objectsList = objectsListFragmentViewModel.objectsList.value!!,
            ){
                onCardClick(it)
            }
        } else {
            NoResultObjectListView()
        }
    }
}