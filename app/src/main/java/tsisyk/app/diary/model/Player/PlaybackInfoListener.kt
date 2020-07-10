package tsisyk.app.diary.model.Player

interface PlaybackInfoListener {

    fun onDurationChanged(duration: Int)

    fun onPositionChanged(position: Int)

    fun onPlaybackCompleted()

    fun onPauseMedia()
}