package fr.yofardev.templatecompose.utils

import android.content.Context
import android.net.Uri
import java.io.FileNotFoundException
import java.io.InputStream

fun getBytesFromUri(uri: Uri, context: Context): ByteArray? {
    var inputStream: InputStream? = null
    try {
        inputStream = context.contentResolver.openInputStream(uri)
        return inputStream?.readBytes()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }
    return null
}