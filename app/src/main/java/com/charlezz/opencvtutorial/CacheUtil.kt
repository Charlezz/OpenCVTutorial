package com.charlezz.opencvtutorial

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.*
import java.util.*
import javax.inject.Inject

class CacheUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun copyFrom(src: Uri): File {
        // Preparing Temp file name
        val fileExtension = getFileExtension(context, src)
        val fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""

        // Creating Temp file
        val tempFile = File(context.filesDir, fileName)
        tempFile.createNewFile()

        try {
            val fileOutputStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(src)

            inputStream?.let {
                copy(inputStream, fileOutputStream)
            }

            fileOutputStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return tempFile
    }

    fun clear() = getCacheDir(context).deleteRecursively()

    private fun getCacheDir(context: Context): File {
        val file = File(context.filesDir, UUID.randomUUID().toString())
        if(!file.exists()){
            file.mkdir()
        }
        return file
    }

    private fun getFileExtension(context: Context, uri: Uri): String? {
        val fileType: String? = context.contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
    }

    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }
}