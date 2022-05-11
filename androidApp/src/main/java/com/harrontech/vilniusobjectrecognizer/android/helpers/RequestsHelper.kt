package com.harrontech.vilniusobjectrecognizer.android.helpers

import com.harrontech.vilniusobjectrecognizer.android.models.ObjectInfo
import com.harrontech.vilniusobjectrecognizer.android.models.UserDataRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking

class RequestsHelper : DataFetchingHelper {

    val client = HttpClient()

    val url = "https://ktor.io"
    val cityObjectUrl = "$url/city-object"
    val recognitionUrl = "$url/landmarks"
    val bugReportUrl = "$url/bug-report"

    override fun getObjectsList(): List<ObjectInfo>? {
        return runBlocking {
            client.get(cityObjectUrl).body()
        }
    }

    override fun getFilteredObjectsList(text: String): List<ObjectInfo>? {
        return runBlocking {
            client.get(cityObjectUrl) {
                parameter("search", text)
            }.body()
        }
    }

    override fun getObjectByID(id: String): ObjectInfo? {
        return runBlocking {
            client
                .get("$cityObjectUrl/$id")
                .body()
        }
    }

    override fun recognizeObject(userDataRequest: UserDataRequest?): String? {
        return runBlocking {
            client
                .post(recognitionUrl) {
                    contentType(ContentType.Application.Json)
                    setBody(userDataRequest)
                }
                .body()
        }
    }

    override fun fillBugReport(bugReportRequest: String): String? {
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