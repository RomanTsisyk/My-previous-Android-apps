package tsisyk.app.diary.activities

import tsisyk.app.diary.R
import tsisyk.app.diary.model.Player.PlaybackInfoListener
import tsisyk.app.diary.model.Player.PlayerAdapter
import tsisyk.app.diary.utilities.TimeManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import tsisyk.app.diary.model.Player.MediaPlayerHolder
import java.io.File

class PlayerActivity : AppCompatActivity(), PlaybackInfoListener {
    private var mPlayerAdapter: PlayerAdapter? = null
    private var mSeekBar: SeekBar? = null
    private var mPlayStopButton: ImageButton? = null
    private var mNameText: TextView? = null
    private var mDurationText: TextView? = null
    private var mCurrentTime: TextView? = null
    private var mUserIsSeeking: Boolean = false

    private fun initializeUI() {
        mSeekBar = findViewById<View>(R.id.activity_dialog_player_seek_bar) as SeekBar
        mCurrentTime = findViewById<View>(R.id.activity_dialog_player_current_time) as TextView
        mDurationText = findViewById<View>(R.id.activity_dialog_player_duration) as TextView
        mNameText = findViewById<View>(R.id.activity_dialog_player_name_file) as TextView
        mPlayStopButton = findViewById<View>(R.id.activity_dialog_player_play_stop_btn) as ImageButton
        mPlayStopButton!!.setOnClickListener {
            if (mPlayerAdapter!!.isPlaying) {
                mPlayerAdapter!!.pause()
                mPlayStopButton!!.setImageResource(R.drawable.ic_play)
            } else {
                mPlayerAdapter!!.play()
                mPlayStopButton!!.setImageResource(R.drawable.ic_pause)
            }
        }
    }

    private fun initializeSeekBar() {
        mSeekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var userSelectionPosition = 0

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    userSelectionPosition = i
                    mCurrentTime!!.text = TimeManager.getTime((i / 1000).toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                mUserIsSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                mUserIsSeeking = false
                mPlayerAdapter!!.seekTo(userSelectionPosition)
            }
        })
    }

    private fun initializeCallbackController() {
        val mediaPlayerHolder = MediaPlayerHolder(this).also {
            it.setPlaybackInfoListener(this)
        }
        mPlayerAdapter = mediaPlayerHolder
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_player)
        initializeUI()
        initializeSeekBar()
        initializeCallbackController()
        val file = intent.getSerializableExtra(ARG_FILE_SOUND) as File
        mNameText!!.text = file.name
        mPlayerAdapter!!.loadMedia(file.absolutePath)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayerAdapter!!.stop()
        mPlayerAdapter!!.release()
    }

    override fun onDurationChanged(duration: Int) {
        mSeekBar!!.max = duration
        mDurationText!!.text = TimeManager.getTime((duration / 1000).toLong())
    }

    override fun onPositionChanged(position: Int) {
        if (!mUserIsSeeking) {
            mSeekBar!!.progress = position
            mCurrentTime!!.post { mCurrentTime!!.text = TimeManager.getTime((position / 1000).toLong()) }
        }
    }

    override fun onPlaybackCompleted() {
        mPlayStopButton!!.setImageResource(R.drawable.ic_play)
    }

    override fun onPauseMedia() {
        mPlayStopButton!!.setImageResource(R.drawable.ic_play)
    }

    companion object {

        private const val ARG_FILE_SOUND = "FileSound"

        fun newIntent(packageContext: Context, file: File): Intent {
            val intent = Intent(packageContext, PlayerActivity::class.java)
            intent.putExtra(ARG_FILE_SOUND, file)
            return intent
        }
    }
}
