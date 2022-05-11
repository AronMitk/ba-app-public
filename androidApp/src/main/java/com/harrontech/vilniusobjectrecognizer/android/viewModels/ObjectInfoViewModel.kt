package com.harrontech.vilniusobjectrecognizer.android.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harrontech.vilniusobjectrecognizer.android.helpers.DataFetchingHelper
import com.harrontech.vilniusobjectrecognizer.android.helpers.RequestsHelper
import com.harrontech.vilniusobjectrecognizer.android.models.ObjectInfo

class ObjectInfoViewModel : ViewModel() {
    private var _objectInfo = MutableLiveData<ObjectInfo>(null)
    val objectInfo : LiveData<ObjectInfo> = _objectInfo

    var storage  = RequestsHelper()
    
    fun setValue(id: String){
        _objectInfo.value = storage.getObjectByID(id)
    }
}