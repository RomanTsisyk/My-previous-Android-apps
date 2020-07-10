package tsisyk.app.diary.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import tsisyk.app.diary.R
import tsisyk.app.diary.model.Dictaphone.DictaphoneAdapter
import tsisyk.app.diary.model.Dictaphone.DictaphoneHolder
import tsisyk.app.diary.model.Dictaphone.PlaybackInfoListener
import tsisyk.app.diary.service.DictaphoneService.DictaphoneService
import tsisyk.app.diary.utilities.TimeManager
import tsisyk.app.diary.views.CustomImageButton
import java.util.*

class DiaryActivity : AppCompatActivity(), PlaybackInfoListener {


    private var mDictaphone: DictaphoneAdapter? = null
    private var mStopwatch: TextView? = null
    private var mStateDictaphone: TextView? = null
    private var mRecordingStopButton: CustomImageButton? = null
    private var mListDeleteButton: CustomImageButton? = null
    private var mSaveButton: CustomImageButton? = null
    private var mDefaultNameFile: String? = null
    private var mProgressDialog: ProgressDialog? = null
    private var mBound: Boolean = false



    internal var handlerHideProgress: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            mProgressDialog!!.hide()
        }
    }

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            val dictaphoneHolder = iBinder as DictaphoneHolder
            dictaphoneHolder.addPlaybackInfoListener(this@DiaryActivity)
            mDictaphone = dictaphoneHolder
            updateUI()
            mBound = true
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBound = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.me -> {
                val intent = Intent(this, AboutMeActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeUI() {

        mRecordingStopButton!!.setOnClickListener {
            if (mBound) {
                if (!mDictaphone!!.isRecording && !mDictaphone!!.isPause) {
                    if (!mDictaphone!!.start()) {
                        Toast.makeText(this@DiaryActivity, R.string.error_start,
                                Toast.LENGTH_SHORT).show()
                    }
                } else if (mDictaphone!!.isPause) {
                    mDictaphone!!.resume()
                } else if (!mDictaphone!!.isPause) {
                    mDictaphone!!.pause()
                }
            }
        }

        mSaveButton!!.setOnClickListener {
            mDictaphone!!.pause()
            mDefaultNameFile = getString(R.string.default_name_record) + UUID
                    .randomUUID().toString().split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            val intent = EditTextActivity
                    .newIntent(applicationContext, getString(R.string.name_record),
                            NAME_DIR,
                            mDefaultNameFile!!,
                            FORMAT)
            startActivityForResult(intent, REQUEST_SAVE)
        }

        mListDeleteButton!!.setOnClickListener {
            if (mDictaphone!!.isRecording && mBound) {
                mDictaphone!!.pause()
                val intent = CancelDialogActivity
                        .newIntent(applicationContext, getString(R.string.no_save_file),
                                getString(R.string.no_save_button))
                startActivityForResult(intent, REQUEST_NO_SAVE)
            } else {
                val intent = ListRecordsActivity
                        .newIntent(applicationContext, NAME_DIR, FORMAT)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictaphone)
        mStopwatch = findViewById<View>(R.id.activity_dictaphone_stop_watch) as TextView
        mStateDictaphone = findViewById<View>(R.id.activity_dictaphone_state_dictaphone) as TextView
        mRecordingStopButton = findViewById<View>(
                R.id.activity_dictaphone_recording_stop__btn) as CustomImageButton
        mSaveButton = findViewById<View>(R.id.activity_dictaphone_save_btn) as CustomImageButton
        mListDeleteButton = findViewById<View>(
                R.id.activity_dictaphone_list_delete__btn) as CustomImageButton
        mProgressDialog = ProgressDialog(this)
        mProgressDialog!!.setMessage(getString(R.string.saving))
        mProgressDialog!!.setCancelable(false)
        initializeUI()
        onNewIntent(intent)


        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions,0)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action != null) {
            if (intent.action == ACTION_DELETE) {
                mListDeleteButton!!.post {
                    while (!mBound);
                    mListDeleteButton!!.performClick()
                }
            } else if (intent.action == ACTION_SAVE) {
                mSaveButton!!.post {
                    while (!mBound);
                    mSaveButton!!.performClick()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = DictaphoneService.newIntent(applicationContext, null)
        startService(intent)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mBound) {
            unbindService(mServiceConnection)
            mBound = false
        }
        if (!mDictaphone!!.isRecording) {
            (mDictaphone as DictaphoneHolder).removePlaybackInfoListener(this)
            val intent = DictaphoneService.newIntent(applicationContext, null)
            stopService(intent)
        }
        mProgressDialog!!.dismiss()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SAVE) {
            if (resultCode == Activity.RESULT_OK) {
                mDictaphone!!.stop()
                mProgressDialog!!.show()
                Thread(Runnable { mDictaphone!!.save(EditTextActivity.getResult(data!!)) }).start()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                mDictaphone!!.resume()
            }
        } else if (requestCode == REQUEST_NO_SAVE) {
            if (resultCode == Activity.RESULT_OK) {
                mDictaphone!!.stop()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                mDictaphone!!.resume()
            }
        }
    }

    private fun updateUI() {
        if (mDictaphone!!.isRecording) {
            mSaveButton!!.isEnabled = true
            mListDeleteButton!!.setImageResource(R.drawable.ic_clear)
        } else {
            mSaveButton!!.isEnabled = false
            mListDeleteButton!!.setImageResource(R.drawable.ic_list)
            mStateDictaphone!!.setText(R.string.start_recording)
        }

        if (mDictaphone!!.isRecording && !mDictaphone!!.isPause) {
            mRecordingStopButton!!.setImageResource(R.drawable.ic_pause)
            mStateDictaphone!!.setText(R.string.state_recording)
        } else if (mDictaphone!!.isRecording && mDictaphone!!.isPause) {
            mRecordingStopButton!!.setImageResource(R.drawable.ic_micro)
            mStateDictaphone!!.setText(R.string.state_pause)
        } else if (!mDictaphone!!.isRecording) {
            mRecordingStopButton!!.setImageResource(R.drawable.ic_micro)
        }
        mStopwatch!!.text = TimeManager.getTime(mDictaphone!!.second)
    }

    override fun startRecording() {
        updateUI()
    }

    override fun stopRecording() {
        updateUI()
    }

    override fun pauseRecording() {
        updateUI()
    }

    override fun resumeRecording() {
        updateUI()
    }

    override fun currentTimeRecording(time: Long) {
        mStopwatch!!.post {
            if (mDictaphone!!.isRecording) {
                mStopwatch!!.text = TimeManager.getTime(time)
            }
        }
    }

    override fun saveFinish() {
        handlerHideProgress.sendEmptyMessage(0)
    }

    companion object {

        val ACTION_SAVE = "ActionSave"
        val ACTION_DELETE = "ActionDelete"
        private val NAME_DIR = "Dictaphone"
        private val FORMAT = ".m4a"
        private val REQUEST_SAVE = 1
        private val REQUEST_NO_SAVE = 2


        fun newIntent(packageContext: Context, action: String?): Intent {
            val intent = Intent(packageContext, DiaryActivity::class.java)
            if (action != null) {
                intent.action = action
            }
            return intent
        }
    }


}

