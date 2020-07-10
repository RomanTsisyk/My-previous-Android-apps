package tsisyk.app.diary.model.Dictaphone

import tsisyk.app.diary.utilities.FileManager
import android.content.Context
import android.media.MediaRecorder
import android.os.Binder
import com.googlecode.mp4parser.authoring.Movie
import com.googlecode.mp4parser.authoring.Track
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator
import com.googlecode.mp4parser.authoring.tracks.AppendTrack
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.util.ArrayList
import java.util.LinkedList
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class DictaphoneHolder(context: Context, private val mDirName: String) : Binder(), DictaphoneAdapter {
    private var mMediaRecorder: MediaRecorder? = null
    private val mPlaybackInfoListener: MutableList<PlaybackInfoListener>
    private var mUpdateGUI: ScheduledExecutorService? = null
    private val mContext: Context
    private val mFilesBuffer: MutableList<File>
    override var isRecording: Boolean = false
        private set
    override var isPause: Boolean = false
        private set
    override var second: Long = 0
        private set


    init {
        mContext = context.applicationContext
        mFilesBuffer = ArrayList()
        mPlaybackInfoListener = ArrayList()
    }

    fun addPlaybackInfoListener(
            playbackInfoListener: PlaybackInfoListener) {
        mPlaybackInfoListener.add(playbackInfoListener)
    }

    fun removePlaybackInfoListener(playbackInfoListener: PlaybackInfoListener) {
        mPlaybackInfoListener.remove(playbackInfoListener)
    }

    private fun stopUpdatingCallbackWithPosition() {
        if (mUpdateGUI != null) {
            mUpdateGUI!!.shutdownNow()
            mUpdateGUI = null
            second = 0
        }
    }

    private fun startUpdatingCallbackPosition() {
        if (mUpdateGUI == null) {
            mUpdateGUI = Executors.newSingleThreadScheduledExecutor()
        }
        mUpdateGUI!!.scheduleAtFixedRate({ updateProgressCallbackTask() }, PLAYBACK_POSITION_REFRESH_INTERVAL_MS, PLAYBACK_POSITION_REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS)
    }

    private fun updateProgressCallbackTask() {
        if (mMediaRecorder != null && isRecording && !isPause) {
            second++
            for (listener in mPlaybackInfoListener) {
                listener.currentTimeRecording(second)
            }
        }
    }

    override fun initial() {
        if (mMediaRecorder == null) {
            mMediaRecorder = MediaRecorder()
            mFilesBuffer.clear()
            FileManager.clearInternalStorage(mContext)
        }
    }

    override fun prepareDataSourceConfigured() {
        if (mMediaRecorder != null) {
            mMediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            FileManager.createFileInternalStorage(mContext,
                    FILE_NAME_FOR_BUFF + mFilesBuffer.size + FORMAT)?.let { mFilesBuffer.add(it) }
            mMediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mMediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mMediaRecorder!!.setAudioChannels(1)
            mMediaRecorder!!.setAudioEncodingBitRate(96000)
            mMediaRecorder!!.setAudioSamplingRate(44100)
            mMediaRecorder!!.setOutputFile(mFilesBuffer[mFilesBuffer.size - 1].path)
            try {
                mMediaRecorder!!.prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    override fun start(): Boolean {
        if (!isRecording) {
            initial()
            prepareDataSourceConfigured()
            try {
                mMediaRecorder!!.start()
            } catch (error: RuntimeException) {
                reset()
                release()
                return false
            }

            isPause = false
            isRecording = true
            for (listener in mPlaybackInfoListener) {
                listener.startRecording()
            }
            startUpdatingCallbackPosition()
            return true
        }
        return false
    }

    override fun stop() {
        if (mMediaRecorder != null && isRecording) {
            try {
                if (!isPause) {
                    mMediaRecorder!!.stop()
                }
            } catch (re: RuntimeException) {
                mFilesBuffer[mFilesBuffer.size - 1].delete()
                mFilesBuffer.removeAt(mFilesBuffer.size - 1)
            } finally {
                isRecording = false
                isPause = false
                release()
                stopUpdatingCallbackWithPosition()
                for (listener in mPlaybackInfoListener) {
                    listener.stopRecording()
                }
            }
        }
    }

    override fun resume() {
        if (mMediaRecorder != null && isPause) {
            isPause = false
            prepareDataSourceConfigured()
            mMediaRecorder!!.start()
            for (listener in mPlaybackInfoListener) {
                listener.resumeRecording()
            }
        }
    }

    override fun pause() {
        if (mMediaRecorder != null && !isPause) {
            isPause = true
            for (listener in mPlaybackInfoListener) {
                listener.pauseRecording()
            }
            try {
                mMediaRecorder!!.stop()
            } catch (re: RuntimeException) {
                mFilesBuffer[mFilesBuffer.size - 1].delete()
                mFilesBuffer.removeAt(mFilesBuffer.size - 1)
            } finally {
                reset()
            }
        }
    }

    override fun reset() {
        if (mMediaRecorder != null) {
            mMediaRecorder!!.reset()
        }
    }

    override fun release() {
        if (mMediaRecorder != null) {
            mMediaRecorder!!.release()
            mMediaRecorder = null
        }
    }

    override fun save(nameFile: String) {
        try {
            FileManager.createDirExternalStoragePublic(mDirName)
            val fileMusic = FileManager
                    .createFileExternalStoragePublic(mDirName, nameFile + FORMAT)
            val list = ArrayList<File>()
            for (file in mFilesBuffer) {
                list.add(file)
            }
            if (list.size != 0) {
                val resultMovie = Movie()
                val inMovies = ArrayList<Movie>()
                for (file in list)
                    inMovies.add(MovieCreator.build(file.path))
                val audioTracks = LinkedList<Track>()
                for (m in inMovies) {
                    for (track in m.tracks) {
                        if (track.handler == "soun") {
                            audioTracks.add(track)
                        }
                    }
                }
                if (!audioTracks.isEmpty())
                    resultMovie.addTrack(
                            AppendTrack(*audioTracks.toTypedArray()))

                val container = DefaultMp4Builder().build(resultMovie)
                val fc = RandomAccessFile(fileMusic, "rw").channel
                container.writeContainer(fc)
                fc.close()
            }
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }

        for (listener in mPlaybackInfoListener) {
            listener.saveFinish()
        }
    }

    companion object {

        private val PLAYBACK_POSITION_REFRESH_INTERVAL_MS = TimeUnit.SECONDS.toMillis(1)
        private val FILE_NAME_FOR_BUFF = "FILE_NAME_FOR_BUFF"
        private val FORMAT = ".m4a"
    }
}
