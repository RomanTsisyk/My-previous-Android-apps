package tsisyk.app.diary.model.Dictaphone

interface DictaphoneAdapter {

    val isRecording: Boolean

    val isPause: Boolean

    val second: Long

    fun initial()

    fun prepareDataSourceConfigured()

    fun start(): Boolean

    fun stop()

    fun resume()

    fun pause()

    fun reset()

    fun release()

    fun save(nameFile: String)
}
