package tsisyk.app.diary.utilities

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException

object FileManager {

    private val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

    private val isExternalStorageReadable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
        }

    fun getFilesWithExternalPublicStorage(directory: String): Array<File>? {
        if (isExternalStorageReadable) {
            createDirExternalStoragePublic(directory)
            val sd = Environment.getExternalStoragePublicDirectory(directory)
            return sd.listFiles()
        }
        return null
    }

    fun getFileWithExternalPublicStorage(directory: String, name: String): File? {
        if (isExternalStorageReadable) {
            val sd = Environment.getExternalStoragePublicDirectory(directory)
            val file = File(sd, name)
            if (file.exists()) {
                return file
            }
        }
        return null
    }

    fun fileExistsInExternalPublicStorage(directory: String, name: String): Boolean {
        val sd = Environment.getExternalStoragePublicDirectory(directory)
        return File(sd, name).exists()
    }

    fun renameFileInExternalPublicStorage(directory: String, oldName: String,
                                          newName: String) {
        if (isExternalStorageWritable) {
            val sd = Environment.getExternalStoragePublicDirectory(directory)
            val oldFile = File(sd, oldName)
            val newFile = File(sd, newName)
            oldFile.renameTo(newFile)
        }
    }

    fun createFileExternalStoragePublic(dirName: String, nameFile: String): File? {
        if (isExternalStorageWritable) {
            val sd = Environment.getExternalStoragePublicDirectory(dirName)
            val newFile = File(sd, nameFile)
            try {
                newFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }

            return newFile
        }
        return null
    }

    fun clearFileInExternalStorage(dirName: String, nameFile: String) {
        if (isExternalStorageWritable) {
            val sd = Environment.getExternalStoragePublicDirectory(dirName)
            val file = File(sd, nameFile)
            if (file.exists())
                file.delete()
        }
    }

    fun createDirExternalStoragePublic(dirName: String): Boolean {
        if (isExternalStorageWritable) {
            val file = Environment.getExternalStoragePublicDirectory(dirName)
            return file.mkdirs()
        }
        return false
    }


    fun createFileInternalStorage(context: Context, nameFile: String): File? {
        val newFile = File(context.filesDir, nameFile)
        try {
            newFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return newFile
    }

    fun clearInternalStorage(context: Context) {
        for (file in context.filesDir.listFiles()) {
            file.delete()
        }
    }
}
