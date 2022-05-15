package com.harrontech.vilniusobjectrecognizer.android.helpers

import com.harrontech.vilniusobjectrecognizer.android.models.ObjectInfo
import com.harrontech.vilniusobjectrecognizer.android.models.RecognitionRequest

interface DataFetchingHelper {
    fun getObjectsList() : List<ObjectInfo>?
    fun getFilteredObjectsList(text: String) : List<ObjectInfo>?
    fun getObjectByID(id: String) : ObjectInfo?
    fun recognizeObject(recognitionRequest: RecognitionRequest? = null) : String?
    fun fillBugReport(bugReportRequest: String) : String?
}