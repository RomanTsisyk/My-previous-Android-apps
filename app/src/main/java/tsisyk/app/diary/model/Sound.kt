package tsisyk.app.diary.model

import android.annotation.SuppressLint
import tsisyk.app.diary.utilities.FileManager
import android.media.MediaMetadataRetriever
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class Sound(file: File) {

    var file: File? = null
        private set
    var name: String? = null
        private set
    var date: String? = null
        private set
    var time: String? = null
        private set

    init {
        this.file = file
    }


    fun sutupSpecifications() {
        try {
            installDate()
            installName()
        } catch (runtime: RuntimeException) {
            clear()
        }

        Thread(Runnable {
            try {
                installTime()
            } catch (runtime: RuntimeException) {
                clear()
            }
        }).start()
    }

    private fun clear() {
        val component = file!!.path.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val dir = component[component.size - 2]
        FileManager.clearFileInExternalStorage(dir, file!!.name)
    }

    private fun installName() {
        val component = file!!.name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        name = component[0]
    }

    @SuppressLint("SimpleDateFormat")
    private fun installDate() {
        date = SimpleDateFormat("dd.MM.yyyy").format(Date(file!!.lastModified()))
    }

    private fun installTime() {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(file!!.path)
        val time = Integer.valueOf(mediaMetadataRetriever
                .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))
        val h = (time.toLong() / 3600000).toInt()
        val m = (time.toLong() - h * 3600000).toInt() / 60000
        val s = (time.toLong() - (h * 3600000).toLong() - (m * 60000).toLong()).toInt() / 1000
        this.time = (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10)
            "0$s"
        else
            s
    }

    fun delete() {
        file!!.delete()
    }

    fun rename(newName: String) {
        val component = file!!.absolutePath.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val directory = component[component.size - 2]
        FileManager.renameFileInExternalPublicStorage(directory, file!!.name, newName)
        file = FileManager.getFileWithExternalPublicStorage(directory, newName)
        installDate()
        installName()
    }
}
