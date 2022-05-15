package com.harrontech.vilniusobjectrecognizer.android.helpers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.harrontech.vilniusobjectrecognizer.android.models.ObjectInfo
import com.harrontech.vilniusobjectrecognizer.android.models.RecognitionRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Type


class RequestsHelper {

    val client = HttpClient()

    val url = "http://5.20.206.255:8085/api"
    val cityObjectUrl = "$url/city-objects"
    val recognitionUrl = "$url/landmarks"
    val bugReportUrl = "$url/bug-report"

    val gson = Gson()
    val objectInfoListType: Type = object : TypeToken<List<ObjectInfo>>() {}.type

    suspend fun getObjectsList(): List<ObjectInfo>? {
        return try {
            client.get(cityObjectUrl).body()
        }catch (e: NoTransformationFoundException){
            val objects = client.get(cityObjectUrl).bodyAsText()
            gson.fromJson<List<ObjectInfo>>(objects, objectInfoListType)
        }
    }

    suspend fun getFilteredObjectsList(text: String): List<ObjectInfo>? {
        val objects = client.get("$cityObjectUrl:search") {
            parameter("text", text)
        }.bodyAsText()
        return gson.fromJson<List<ObjectInfo>>(objects, objectInfoListType)
    }

    suspend fun getObjectByID(id: String): ObjectInfo? {
        return gson.fromJson(client.get("$cityObjectUrl/$id").bodyAsText(), ObjectInfo::class.java)
    }

    fun recognizeObject(recognitionRequest: RecognitionRequest?): String? {
        return runBlocking {
            client
                .post(recognitionUrl) {
                    contentType(ContentType.Application.Json)
                    setBody(recognitionRequest)
                }
                .body()
        }
    }

    fun fillBugReport(bugReportRequest: String): String? {
        return runBlocking {
            client
                .post(bugReportUrl) {
                    contentType(ContentType.Application.Json)
                    setBody(bugReportRequest)
                }
                .body()
        }
    }

}