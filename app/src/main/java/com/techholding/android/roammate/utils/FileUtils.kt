package com.techholding.android.roammate.utils

import android.content.res.AssetManager
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel


object FileUtils {
    /** Load TF Lite model from asset file.  */
    @Throws(IOException::class)
    fun loadModelFile(assetManager: AssetManager, modelPath: String?): MappedByteBuffer {
        assetManager.openFd(modelPath!!).use { fileDescriptor ->
            FileInputStream(fileDescriptor.fileDescriptor).use { inputStream ->
                val fileChannel: FileChannel = inputStream.getChannel()
                val startOffset = fileDescriptor.startOffset
                val declaredLength = fileDescriptor.declaredLength
                return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            }
        }
    }
}