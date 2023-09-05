package org.algotn.api.utils

import java.io.File

class FileUtils

fun File.getLatestUpdateTime(): Long {
    if (!this.isDirectory) {
        throw IllegalArgumentException("The provided path is not a directory.")
    }
    if (this.listFiles()?.isEmpty() == true) throw IllegalArgumentException("The provided directory is empty.")

    var latestUpdateTime: Long? = null

    this.listFiles()?.forEach { file ->
        if (file.isFile) {
            val lastModifiedTime = file.lastModified()
            if (latestUpdateTime == null || lastModifiedTime > latestUpdateTime!!) {
                latestUpdateTime = lastModifiedTime
            }
        } else if (file.isDirectory) {
            val lastModifiedTime = file.getLatestUpdateTime()
            if (latestUpdateTime == null || lastModifiedTime > latestUpdateTime!!) {
                latestUpdateTime = lastModifiedTime
            }
        }
    }

    return latestUpdateTime!!
}