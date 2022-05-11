package com.harrontech.vilniusobjectrecognizer.android.helpers

import com.harrontech.vilniusobjectrecognizer.android.models.ObjectInfo
import com.harrontech.vilniusobjectrecognizer.android.models.UserDataRequest

interface DataFetchingHelper {
    fun getObjectsList() : List<ObjectInfo>?
    fun getFilteredObjectsList(text: String) : List<ObjectInfo>?
    fun getObjectByID(id: String) : ObjectInfo?
    fun recognizeObject(userDataRequest: UserDataRequest? = null) : String?
    fun fillBugReport(bugReportRequest: String) : String?
}