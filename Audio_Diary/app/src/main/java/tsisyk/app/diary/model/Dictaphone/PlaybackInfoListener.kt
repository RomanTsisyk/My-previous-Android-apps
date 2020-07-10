package tsisyk.app.diary.model.Dictaphone

interface PlaybackInfoListener {

    fun startRecording()

    fun stopRecording()

    fun pauseRecording()

    fun resumeRecording()

    fun currentTimeRecording(time: Long)

    fun saveFinish()
}
