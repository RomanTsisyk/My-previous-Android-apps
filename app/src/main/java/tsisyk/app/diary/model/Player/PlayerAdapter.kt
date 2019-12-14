package tsisyk.app.diary.model.Player

interface PlayerAdapter {

    val isPlaying: Boolean

    fun loadMedia(path: String)

    fun release()

    fun play()

    fun reset()

    fun stop()

    fun pause()

    fun initializeProgressCallback()

    fun seekTo(position: Int)
}
