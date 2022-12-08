package com.charlezz.opencvtutorial.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@Throws(IOException::class)
fun copy(inputStream: InputStream, outputStream: OutputStream) {
    val buffer = ByteArray(1024)
    var read: Int
    while (inputStream.read(buffer).also { read = it } != -1) {
        outputStream.write(buffer, 0, read)
    }
}

fun copyToCacheDir(context: Context, assetName: String, overwrite: Boolean = false): File {
    val inputStream = context.assets.open(assetName)
    val file = File(context.cacheDir, assetName)
    val write = {
        val outputStream = FileOutputStream(file)
        copy(inputStream = inputStream, outputStream = outputStream)
    }
    if (file.exists()) {
        if (overwrite) {
            file.delete()
            write.invoke()
        }
    } else {
        write.invoke()
    }
    return file
}
