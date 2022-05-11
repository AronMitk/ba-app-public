package com.harrontech.vilniusobjectrecognizer.android.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Base64
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executor

class ImageService(var context: Context, var number: Int, var delay: Long, onMediaCaptured: (ByteArray?) -> Unit) {
    fun takePictures(imageCapture: ImageCapture, executor: Executor){
        GlobalScope.launch {
            repeat(number){
                imageCapture.takePicture(executor, imageCaptureCallback)
                delay(delay)
            }
        }
    }

    var imageCaptureCallback = object : ImageCapture.OnImageCapturedCallback() {

        @SuppressLint("UnsafeOptInUsageError")
        override fun onCaptureSuccess(image: ImageProxy) {
            var bitmap = image.image?.toBitmap()

            image.close()

            val outputStream = ByteArrayOutputStream()
            bitmap?.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                outputStream
            )

            var str = Base64.encode(
                outputStream.toByteArray(),
                Base64.DEFAULT)

            onMediaCaptured(str)
        }

        override fun onError(exception: ImageCaptureException) {
            Toast.makeText(
                context,
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

fun Image.toBitmap(): Bitmap {
    val buffer = planes[0].buffer
    buffer.rewind()
    val bytes = ByteArray(buffer.capacity())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}